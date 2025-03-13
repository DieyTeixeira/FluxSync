package com.dieyteixeira.fluxsync.ui.home.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
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

    private val _selectedConta = MutableStateFlow<Conta?>(null)
    val selectedConta: StateFlow<Conta?> = _selectedConta.asStateFlow()
    private val _selectedCategoria = MutableStateFlow<Categoria?>(null)
    val selectedCategoria: StateFlow<Categoria?> = _selectedCategoria.asStateFlow()
    private val _selectedTransaction = MutableStateFlow<Transacoes?>(null)
    val selectedTransaction: StateFlow<Transacoes?> = _selectedTransaction.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()
    private val _tipoMessage = MutableStateFlow<String?>(null)
    val tipoMessage: StateFlow<String?> = _tipoMessage.asStateFlow()

    private val _percentuaisParaGrafico = MutableStateFlow<List<Float>>(emptyList())
    val percentuaisParaGrafico: StateFlow<List<Float>> = _percentuaisParaGrafico

    private val _categoriasParaGrafico = MutableStateFlow<List<String>>(emptyList())
    val categoriasParaGrafico: StateFlow<List<String>> = _categoriasParaGrafico

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

    fun salvarConta(icon: String, color: String, descricao: String, saldo: Double) {
        viewModelScope.launch {
            try {
                firestoreRepository.salvarConta(icon, color, descricao, saldo)

                _message.value = "Conta salva com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao salvar a conta: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun selectConta(conta: Conta) {
        _selectedConta.value = conta
    }

    fun editarConta(
        conta: Conta
    ) {
        viewModelScope.launch {
            try {
                firestoreRepository.editarConta(conta)

                _message.value = "Conta editada com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao editar a conta: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun excluirConta(contaId: String) {
        viewModelScope.launch {
            try {
                val transacoesVinculadas = _transacoes.value.any { it.contaId == contaId }

                if (transacoesVinculadas) {
                    _message.value = "Não é possível excluir esta conta, pois há transações vinculadas a ela."
                    _tipoMessage.value = "vinculo"
                    return@launch
                }

                firestoreRepository.excluirConta(contaId)

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao excluir a conta: ${e.message}"
                _tipoMessage.value = "error"
            }
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
            try {
                firestoreRepository.salvarCategoria(icon, color, descricao)

                _message.value = "Categoria salva com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao salvar a categoria: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun selectCategoria(categoria: Categoria) {
        _selectedCategoria.value = categoria
    }

    fun editarCategoria(
        categoria: Categoria
    ) {
        viewModelScope.launch {
            try {
                firestoreRepository.editarCategoria(categoria)

                _message.value = "Categoria editada com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao editar a categoria: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun excluirCategoria(categoriaId: String) {
        viewModelScope.launch {
            try {
                val transacoesVinculadas = _transacoes.value.any { it.categoriaId == categoriaId }

                if (transacoesVinculadas) {
                    _message.value = "Não é possível excluir esta categoria, pois há transações vinculadas a ela."
                    _tipoMessage.value = "vinculo"
                    return@launch
                }

                firestoreRepository.excluirCategoria(categoriaId)

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao excluir a categoria: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun getTransacoes() {
        viewModelScope.launch {
            val transacoesFirestore = firestoreRepository.getTransacoes()
            _transacoes.value = transacoesFirestore
        }
    }

    fun getTransactionById(transactionId: String) {
        viewModelScope.launch {
            val transacoesFirestore = _transacoes.value.find { it.id == transactionId }
            _selectedTransaction.value = transacoesFirestore
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

    fun clearMessage() {
        _message.value = null
        _tipoMessage.value = null
    }

    fun getTransacoesAgrupadasPorCategoria() {
        viewModelScope.launch {
            if (_transacoes.value.isEmpty()) {
                _message.value = "Transações não carregadas!"
                _tipoMessage.value = "error"
                return@launch
            }

            val totalValor = _transacoes.value.sumOf {
                it.valor ?: 0.0
            }

            val transacoesAgrupadas = _transacoes.value.groupBy { it.categoriaId }
                .mapValues { entry ->
                    entry.value.sumOf { it.valor ?: 0.0 }
                }

            val transacoesComPercentuais = transacoesAgrupadas.mapValues { entry ->
                val percentual = if (totalValor > 0) (entry.value / totalValor) * 100 else 0.0
                percentual
            }

            val percentuais = transacoesComPercentuais.values.toList().map { it.toFloat() }

            val categorias = transacoesComPercentuais.keys.toList()

            // Atualizando o gráfico com as categorias e valores
            _percentuaisParaGrafico.value = percentuais
            _categoriasParaGrafico.value = categorias
        }
    }
}