package com.dieyteixeira.fluxsync.ui.home.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.repository.FirestoreRepository
import com.google.firebase.Timestamp
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

    init {
        getContas()
        getCategorias()
    }

    fun getAtualizar() {
        getContas()
        getCategorias()
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

    fun salvarTransacao(
        descricao: String,
        valor: Double,
        tipo: String,
        situacao: String,
        categoriaId: String,
        contaId: String,
        data: String,
        lancamento: String, // "único", "fixo" ou "parcelado"
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
                val timestampDate = Timestamp(parsedDate)
                val timestampDateV = Timestamp(parsedDateV)
                val timestampDateP = Timestamp(parsedDateP)

                firestoreRepository.salvarTransacao(
                    descricao = descricao,
                    valor = valor,
                    tipo = tipo,
                    situacao = situacao,
                    categoriaId = categoriaId,
                    contaId = contaId,
                    data = timestampDate,
                    lancamento = lancamento,
                    parcelas = parcelas,
                    dataVencimento = timestampDateV,
                    dataPagamento = timestampDateP,
                    observacao = observacao
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Erro ao salvar transação", e)
            }
        }
    }
}