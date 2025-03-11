package com.dieyteixeira.fluxsync.ui.home.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.di.repository.FirestoreRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _contas = mutableStateOf<List<Conta>>(emptyList())
    val contas = _contas
    private val _categorias = mutableStateOf<List<Categoria>>(emptyList())
    val categorias = _categorias
    private val _transacoes = mutableStateOf<List<Transacoes>>(emptyList())
    val transacoes = _transacoes

    private val _selectedTransaction = MutableStateFlow<Transacoes?>(null)
    val selectedTransaction: StateFlow<Transacoes?> = _selectedTransaction.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()
    private val _tipoMessage = MutableStateFlow<String?>(null)
    val tipoMessage: StateFlow<String?> = _tipoMessage.asStateFlow()

    init {
        getAtualizar()
    }

    fun getAtualizar() {
        getContas()
        getCategorias()
        getTransacoes()
    }

    fun getContas() {
        viewModelScope.launch {
            val contasFromFirestore = firestoreRepository.getContas()
            _contas.value = contasFromFirestore
        }
    }

    fun salvarConta(icon: String, color: String, descricao: String, saldo: String) {
        viewModelScope.launch {
            firestoreRepository.salvarConta(icon, color, descricao, saldo)
        }
    }

    fun getCategorias() {
        viewModelScope.launch {
            val categoriasFromFirestore = firestoreRepository.getCategorias()
            _categorias.value = categoriasFromFirestore
        }
    }

    fun salvarCategoria(icon: String, color: String, descricao: String) {
        viewModelScope.launch {
            firestoreRepository.salvarCategoria(icon, color, descricao)
        }
    }

    fun getTransacoes() {
        viewModelScope.launch {
            val transacoesFromFirestore = firestoreRepository.getTransacoes()
            _transacoes.value = transacoesFromFirestore
        }
    }

    fun salvarTransacao(
        descricao: String,
        valor: Double,
        tipo: String,
        situacao: String,
        categoriaId: String,
        contaId: String,
        data: String,
        lancamento: String,
        parcelas: Int,
        dataVencimento: String,
        dataPagamento: String,
        observacao: String
    ) {
        viewModelScope.launch {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val parsedDate = dateFormat.parse(data)
                val parsedDateV = dateFormat.parse(dataVencimento)
                val parsedDateP = dateFormat.parse(dataPagamento)
                val timestampDate = Timestamp(parsedDate!!)
                val timestampDateV = Timestamp(parsedDateV!!)
                val timestampDateP = Timestamp(parsedDateP!!)
                val parcelasString = parcelas.toString()

                firestoreRepository.salvarTransacao(
                    descricao, valor, tipo, situacao, categoriaId, contaId, timestampDate,
                    lancamento, parcelasString, timestampDateV, timestampDateP, observacao
                )

                _message.value = "Transação salva com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao salvar a transação: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun selectTransaction(transaction: Transacoes) {
        _selectedTransaction.value = transaction
    }

    fun editarTransacao(
        transacao: Transacoes,
        alterarTodas: Boolean
    ) {
        viewModelScope.launch {
            try {
                if (alterarTodas) {
                    firestoreRepository.editarTransacoesDoGrupo(transacao.grupoId, transacao)
                } else {
                    firestoreRepository.editarTransacaoUnica(transacao)
                }

                _message.value = "Transação editada com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao editar a transação: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun editarSituacao(
        transacaoId: String,
        transacaoSituacao: String,
        transacaoTipo: String,
        transacaoValor: Double,
        contaId: String,
        contaSaldo: Double
    ) {
        viewModelScope.launch {
            try {
                firestoreRepository.editarSituacao(
                    transacaoId, transacaoSituacao, transacaoTipo,
                    transacaoValor, contaId, contaSaldo
                )
                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao editar a situação da transação: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun excluirTransacao(grupoId: String) {
        viewModelScope.launch {
            try {
                firestoreRepository.excluirTransacao(grupoId)

                _message.value = "Transação excluída com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao excluir a transação: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }
}