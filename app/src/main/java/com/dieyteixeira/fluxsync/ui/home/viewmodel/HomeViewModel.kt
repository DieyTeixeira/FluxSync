package com.dieyteixeira.fluxsync.ui.home.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Subcategoria
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.di.repository.FirestoreRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class HomeViewModel(
    private val firestoreRepository: FirestoreRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _contas = mutableStateOf<List<Conta>>(emptyList())
    val contas = _contas
    private val _categorias = mutableStateOf<List<Categoria>>(emptyList())
    val categorias = _categorias
    private val _subcategorias = mutableStateOf<List<Subcategoria>>(emptyList())
    val subcategorias = _subcategorias
    private val _transacoes = mutableStateOf<List<Transacoes>>(emptyList())
    val transacoes = _transacoes

    private val _selectedConta = MutableStateFlow<Conta?>(null)
    val selectedConta: StateFlow<Conta?> = _selectedConta.asStateFlow()
    private val _selectedCategoria = MutableStateFlow<Categoria?>(null)
    val selectedCategoria: StateFlow<Categoria?> = _selectedCategoria.asStateFlow()
    private val _selectedSubcategoria = MutableStateFlow<Subcategoria?>(null)
    val selectedSubcategoria: StateFlow<Subcategoria?> = _selectedSubcategoria.asStateFlow()
    private val _selectedTransaction = MutableStateFlow<Transacoes?>(null)
    val selectedTransaction: StateFlow<Transacoes?> = _selectedTransaction.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()
    private val _tipoMessage = MutableStateFlow<String?>(null)
    val tipoMessage: StateFlow<String?> = _tipoMessage.asStateFlow()

    private val _sliderPosition = MutableStateFlow(0f)
    val sliderPosition: StateFlow<Float> = _sliderPosition.asStateFlow()

    private val _adjustedFontSize = MutableStateFlow(0)
    val adjustedFontSize: StateFlow<Int> = _adjustedFontSize.asStateFlow()

    private val _selectedColor = MutableStateFlow("GREEN") // Cor padrão
    val selectedColor: StateFlow<String> = _selectedColor.asStateFlow()

    fun setSliderPosition(position: Float) {
        _sliderPosition.value = position
        val fontSize = position.roundToInt()
        _adjustedFontSize.value = fontSize
        viewModelScope.launch {
            userPreferences.saveFontSize(fontSize)
        }
    }

    fun setSelectedColor(color: String) {
        _selectedColor.value = color
        viewModelScope.launch {
            userPreferences.saveColorTheme(color)
        }
    }

    init {
        getAtualizar()
        viewModelScope.launch {
            userPreferences.getFontSize().collect { savedSize ->
                _adjustedFontSize.value = savedSize
                _sliderPosition.value = savedSize.toFloat()
            }
        }
        viewModelScope.launch {
            userPreferences.getColorTheme().collect { savedColor ->
                _selectedColor.value = savedColor
            }
        }
    }

    fun getAtualizar() {
        getContas()
        getCategorias()
        getSubcategorias()
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

    fun salvarCategoria(icon: String, color: String, descricao: String, tipo: String) {
        viewModelScope.launch {
            try {
                firestoreRepository.salvarCategoria(icon, color, descricao, tipo)

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

    fun getSubcategorias() {
        viewModelScope.launch {
            val subcategoriasFromFirestore = firestoreRepository.getSubcategorias()
            _subcategorias.value = subcategoriasFromFirestore
        }
    }

    fun salvarSubcategoria(icon: String, color: String, descricao: String, tipo: String) {
        viewModelScope.launch {
            try {
                firestoreRepository.salvarSubcategoria(icon, color, descricao, tipo)

                _message.value = "Subcategoria salva com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao salvar a subcategoria: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun selectSubcategoria(subcategoria: Subcategoria) {
        _selectedSubcategoria.value = subcategoria
    }

    fun editarSubcategoria(
        subcategoria: Subcategoria
    ) {
        viewModelScope.launch {
            try {
                firestoreRepository.editarSubcategoria(subcategoria)

                _message.value = "Subcategoria editada com sucesso!"
                _tipoMessage.value = "success"

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao editar a subcategoria: ${e.message}"
                _tipoMessage.value = "error"
            }
        }
    }

    fun excluirSubcategoria(subcategoriaId: String) {
        viewModelScope.launch {
            try {
                val transacoesVinculadas = _transacoes.value.any { it.subcategoriaId == subcategoriaId }

                if (transacoesVinculadas) {
                    _message.value = "Não é possível excluir esta subcategoria, pois há transações vinculadas a ela."
                    _tipoMessage.value = "vinculo"
                    return@launch
                }

                firestoreRepository.excluirSubcategoria(subcategoriaId)

                getAtualizar()
            } catch (e: Exception) {
                _message.value = "Erro ao excluir a subcategoria: ${e.message}"
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
        subcategoriaId: String,
        contaId: String,
        data: String,
        lancamento: String,
        parcelas: Int,
        observacao: String
    ) {
        viewModelScope.launch {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val parsedDate = dateFormat.parse(data)
                val timestampDate = Timestamp(parsedDate!!)
                val parcelasString = parcelas.toString()

                firestoreRepository.salvarTransacao(
                    descricao, valor, tipo, situacao, categoriaId, subcategoriaId, contaId,
                    timestampDate, lancamento, parcelasString, observacao
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

    fun verificarVencimento(dataTransacao: Date): String? {

        val hoje = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val dataTransacaoCal = Calendar.getInstance().apply {
            time = dataTransacao
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Calcular a diferença em milissegundos e converter para dias
        val diferencaEmMillis = dataTransacaoCal.timeInMillis - hoje.timeInMillis
        val diferencaEmDias = TimeUnit.MILLISECONDS.toDays(diferencaEmMillis).toInt()

        return when {
            diferencaEmDias < 0 -> "atrasada há ${-diferencaEmDias} dias" // Caso esteja vencida
            diferencaEmDias in 1..2 -> "vence em $diferencaEmDias dias" // Se vencer hoje ou nos próximos 2 dias
            diferencaEmDias == 0 -> "vence hoje" // Caso venha hoje
            else -> null // Caso não se encaixe nesses critérios
        }
    }

    fun getValoresPorDia(): List<Pair<Int, Double>> {
        val calendar = Calendar.getInstance()
        val anoAtual = calendar.get(Calendar.YEAR)
        val mesAtual = calendar.get(Calendar.MONTH) // Janeiro = 0, Fevereiro = 1...

        // Descobre quantos dias tem o mês atual
        val diasNoMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Cria um mapa inicial com todos os dias do mês e valor 0.0
        val saldoPorDia = mutableMapOf<Int, Double>().apply {
            for (dia in 1..diasNoMes) {
                this[dia] = 0.0
            }
        }

        // Filtra transações do mês e ano atuais
        val transacoesFiltradas = _transacoes.value.filter { transacao ->
            val dataTransacao = Calendar.getInstance().apply { time = transacao.data }
            dataTransacao.get(Calendar.YEAR) == anoAtual &&
                    dataTransacao.get(Calendar.MONTH) == mesAtual &&
                    transacao.situacao == "pendente"
        }

        // Soma receitas e despesas por dia
        transacoesFiltradas.forEach { transacao ->
            val diaTransacao = Calendar.getInstance().apply { time = transacao.data }.get(Calendar.DAY_OF_MONTH)
            val valorAjustado = if (transacao.tipo == "despesa") -transacao.valor else transacao.valor
            saldoPorDia[diaTransacao] = (saldoPorDia[diaTransacao] ?: 0.0) + valorAjustado
        }

        // Retorna a lista ordenada (dia, gasto)
        return saldoPorDia.toList().sortedBy { it.first }
    }
}