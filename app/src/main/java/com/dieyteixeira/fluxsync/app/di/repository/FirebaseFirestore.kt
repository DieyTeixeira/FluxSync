package com.dieyteixeira.fluxsync.app.di.repository

import android.annotation.SuppressLint
import android.util.Log
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Transacoes
import com.dieyteixeira.fluxsync.app.di.replace.colorToStringCategoria
import com.dieyteixeira.fluxsync.app.di.replace.colorToStringConta
import com.dieyteixeira.fluxsync.app.di.replace.iconToStringCategoria
import com.dieyteixeira.fluxsync.app.di.replace.iconToStringConta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.dieyteixeira.fluxsync.app.di.replace.stringToColorCategoria
import com.dieyteixeira.fluxsync.app.di.replace.stringToColorConta
import com.dieyteixeira.fluxsync.app.di.replace.stringToIconCategoria
import com.dieyteixeira.fluxsync.app.di.replace.stringToIconConta
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Calendar
import java.util.Date

@Suppress("UNCHECKED_CAST")
class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun getContas(): List<Conta> {
        val user = auth.currentUser
        val userEmail = user?.email ?: return emptyList()

        return try {
            val querySnapshot: QuerySnapshot = db.collection(userEmail)
                .document("Conta")
                .collection("Contas")
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                val id = document.id
                val iconString = document.getString("icon") ?: return@mapNotNull null
                val colorString = document.getString("color") ?: return@mapNotNull null
                val descricao = document.getString("descricao") ?: return@mapNotNull null
                val saldo  = document.getDouble("saldo") ?: return@mapNotNull null

                val icon = stringToIconConta(iconString)
                val color = stringToColorConta(colorString)

                Conta(id = id, icon = icon, color = color, descricao = descricao, saldo = saldo)
            }
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao recuperar contas", e)
            emptyList()
        }
    }

    suspend fun salvarConta(
        icon: String,
        color: String,
        descricao: String,
        saldo: Double
    ) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return
        val novoId = db.collection("Contas").document().id

        val contaMap = hashMapOf(
            "id" to novoId,
            "icon" to icon,
            "color" to color,
            "descricao" to descricao,
            "saldo" to saldo
        )

        try {
            db.collection(userEmail)
                .document("Conta")
                .collection("Contas")
                .document(novoId)
                .set(contaMap)
                .await()
            Log.d("FirestoreRepository", "Conta salva com ID: $novoId")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao salvar conta", e)
        }
    }

    fun editarConta(conta: Conta) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        val transacaoRef = db.collection(userEmail).document("Conta").collection("Contas").document(conta.id)

        transacaoRef.update(
            mapOf(
                "icon" to iconToStringConta(conta.icon),
                "color" to colorToStringConta(conta.color),
                "descricao" to conta.descricao,
                "saldo" to conta.saldo
            )
        )
    }

    fun excluirConta(contaId: String) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        db.collection(userEmail).document("Conta").collection("Contas").document(contaId).delete()
    }

    suspend fun getCategorias(): List<Categoria> {
        val user = auth.currentUser
        val userEmail = user?.email ?: return emptyList()

        return try {
            val querySnapshot: QuerySnapshot = db.collection(userEmail)
                .document("Categoria")
                .collection("Categorias")
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                val id = document.id
                val iconString = document.getString("icon") ?: return@mapNotNull null
                val colorString = document.getString("color") ?: return@mapNotNull null
                val descricao = document.getString("descricao") ?: return@mapNotNull null

                val icon = stringToIconCategoria(iconString)
                val color = stringToColorCategoria(colorString)

                Categoria(id = id, icon = icon, color = color, descricao = descricao)
            }
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao recuperar categorias", e)
            emptyList()
        }
    }

    suspend fun salvarCategoria(
        icon: String,
        color: String,
        descricao: String
    ) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return
        val novoId = db.collection("Categorias").document().id

        val categoriaMap = hashMapOf(
            "id" to novoId,
            "icon" to icon,
            "color" to color,
            "descricao" to descricao
        )

        try {
            db.collection(userEmail)
                .document("Categoria")
                .collection("Categorias")
                .document(novoId)
                .set(categoriaMap)
                .await()
            Log.d("FirestoreRepository", "Categoria salva com ID: $novoId")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao salvar categoria", e)
        }
    }

    fun editarCategoria(categoria: Categoria) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        val transacaoRef = db.collection(userEmail).document("Categoria").collection("Categorias").document(categoria.id)

        transacaoRef.update(
            mapOf(
                "icon" to iconToStringCategoria(categoria.icon),
                "color" to colorToStringCategoria(categoria.color),
                "descricao" to categoria.descricao
            )
        )
    }

    fun excluirCategoria(categoriaId: String) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        db.collection(userEmail).document("Categoria").collection("Categorias").document(categoriaId).delete()
    }

    suspend fun getTransacoes(): List<Transacoes> {
        val user = auth.currentUser
        val userEmail = user?.email ?: return emptyList()

        return try {
            val querySnapshot: QuerySnapshot = db.collection(userEmail)
                .document("Lancamento")
                .collection("Lancamentos")
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                val id = document.id
                val grupoId = document.getString("grupoId") ?: return@mapNotNull null
                val descricao = document.getString("descricao") ?: return@mapNotNull null
                val valor = document.getDouble("valor") ?: return@mapNotNull null
                val tipo = document.getString("tipo") ?: return@mapNotNull null
                val situacao = document.getString("situacao") ?: return@mapNotNull null
                val categoriaId = document.getString("categoriaId") ?: return@mapNotNull null
                val contaId = document.getString("contaId") ?: return@mapNotNull null
                val data = document.getTimestamp("data")?.toDate() ?: return@mapNotNull null
                val lancamento = document.getString("lancamento") ?: return@mapNotNull null
                val parcelas = document.getString("parcelas") ?: return@mapNotNull null
                val dataVencimento = document.getTimestamp("dataVencimento")?.toDate() ?: Date()
                val dataPagamento = document.getTimestamp("dataPagamento")?.toDate() ?: Date()
                val observacao = document.getString("observacao") ?: ""

                Transacoes(
                    id = id,
                    grupoId = grupoId,
                    descricao = descricao,
                    valor = valor,
                    tipo = tipo,
                    situacao = situacao,
                    categoriaId = categoriaId,
                    contaId = contaId,
                    data = data,
                    lancamento = lancamento,
                    parcelas = parcelas,
                    dataVencimento = dataVencimento,
                    dataPagamento = dataPagamento,
                    observacao = observacao
                )
            }
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao recuperar transações", e)
            emptyList()
        }
    }

    suspend fun salvarTransacao(
        descricao: String,
        valor: Double,
        tipo: String,
        situacao: String,
        categoriaId: String,
        contaId: String,
        data: Timestamp,
        lancamento: String,
        parcelas: String,
        dataVencimento: Timestamp,
        dataPagamento: Timestamp,
        observacao: String
    ) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        try {
            val transacoesRef = db.collection(userEmail).document("Lancamento").collection("Lancamentos") // Correção do caminho da coleção
            val grupoId = transacoesRef.document().id
            val grupoIdNull = ""

            when (lancamento) {
                "Único" -> {
                    val novoId = transacoesRef.document().id
                    val transacaoMap = criarTransacaoMap(
                        novoId, grupoIdNull, descricao, valor, tipo, situacao, categoriaId, contaId,
                        data, lancamento, parcelas, dataVencimento, dataPagamento, observacao
                    )
                    transacoesRef.document(novoId).set(transacaoMap).await()
                }

                "Fixo" -> {
                    val calendar = Calendar.getInstance()
                    calendar.time = data.toDate()

                    for (i in 0..11) {
                        val novoId = transacoesRef.document().id
                        val transacaoMap = criarTransacaoMap(
                            novoId, grupoId, descricao, valor, tipo, situacao, categoriaId, contaId,
                            Timestamp(calendar.time), lancamento, i.toString(),
                            Timestamp(calendar.time), Timestamp(calendar.time), observacao
                        )
                        transacoesRef.document(novoId).set(transacaoMap).await()
                        calendar.add(Calendar.MONTH, 1)
                    }
                }

                "Parcelado" -> {
                    val parcelasInt = parcelas.toInt()
                    val valorParcela = (valor / parcelasInt)
                    val calendar = Calendar.getInstance()
                    calendar.time = data.toDate()

                    for (i in 1..parcelasInt) {
                        val novoId = transacoesRef.document().id
                        val numParcelas = i.toString() + "/" + parcelas
                        val transacaoMap = criarTransacaoMap(
                            novoId, grupoId, descricao, valorParcela, tipo, situacao, categoriaId, contaId,
                            Timestamp(calendar.time), lancamento, numParcelas,
                            Timestamp(calendar.time), Timestamp(calendar.time), observacao
                        )
                        transacoesRef.document(novoId).set(transacaoMap).await()
                        calendar.add(Calendar.MONTH, 1)
                    }
                }
            }

            Log.d("FirestoreRepository", "Transação salva com sucesso!")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao salvar transação", e)
        }
    }

    private fun criarTransacaoMap(
        id: String,
        grupoId: String,
        descricao: String,
        valor: Double,
        tipo: String,
        situacao: String,
        categoriaId: String,
        contaId: String,
        data: Timestamp,
        lancamento: String,
        parcelas: String,
        dataVencimento: Timestamp,
        dataPagamento: Timestamp,
        observacao: String

    ): Map<String, Any> {
        return mapOf(
            "id" to id,
            "grupoId" to grupoId,
            "descricao" to descricao,
            "valor" to valor,
            "tipo" to tipo,
            "situacao" to situacao,
            "categoriaId" to categoriaId,
            "contaId" to contaId,
            "data" to data,
            "lancamento" to lancamento,
            "parcelas" to parcelas,
            "dataVencimento" to dataVencimento,
            "dataPagamento" to dataPagamento,
            "observacao" to observacao
        )
    }

    suspend fun editarTransacaoUnica(transacao: Transacoes) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        val transacaoRef = db.collection(userEmail).document("Lancamento").collection("Lancamentos").document(transacao.id)

        transacaoRef.update(
            mapOf(
                "descricao" to transacao.descricao,
                "valor" to transacao.valor,
                "categoriaId" to transacao.categoriaId,
                "contaId" to transacao.contaId,
                "observacao" to transacao.observacao
            )
        )
    }

    suspend fun editarTransacoesDoGrupo(grupoId: String, transacao: Transacoes) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        val transacoes = db.collection(userEmail).document("Lancamento").collection("Lancamentos")
            .whereEqualTo("grupoId", grupoId)
            .get()
            .await()

        for (doc in transacoes.documents) {
            doc.reference.update(
                mapOf(
                    "descricao" to transacao.descricao,
                    "valor" to transacao.valor,
                    "categoriaId" to transacao.categoriaId,
                    "contaId" to transacao.contaId,
                    "observacao" to transacao.observacao
                )
            )
        }
    }

    @SuppressLint("DefaultLocale")
    suspend fun editarSituacao(
        transacaoId: String,
        transacaoSituacao: String,
        transacaoTipo: String,
        transacaoValor: Double,
        contaId: String,
        contaSaldo: Double
    ) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        val novaSituacao = if (transacaoSituacao == "pendente") "efetivado" else "pendente"

        val calculo = if (transacaoTipo == "receita") transacaoValor else -transacaoValor
        val saldoCalculado = BigDecimal(
            if (transacaoSituacao == "pendente") {
                contaSaldo + calculo
            } else {
                contaSaldo - calculo
            }
        ).setScale(2, RoundingMode.HALF_EVEN).toDouble()

        try {
            db.collection(userEmail).document("Lancamento").collection("Lancamentos")
                .document(transacaoId)
                .update("situacao", novaSituacao)
                .await()

            db.collection(userEmail).document("Conta").collection("Contas")
                .document(contaId)
                .update("saldo", saldoCalculado)
                .await()

            Log.d("Firebase", "Situação atualizada com sucesso!")

        } catch (e: Exception) {
            Log.e("Firebase", "Erro ao atualizar situação", e)
        }
    }

    suspend fun excluirTransacao(grupoId: String) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        try {
            val transacoesRef = db.collection(userEmail)
                .document("Lancamento")
                .collection("Lancamentos")

            val querySnapshot = transacoesRef
                .whereEqualTo("grupoId", grupoId)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                transacoesRef.document(document.id).delete().await()
            }

            Log.d("FirestoreRepository", "Transações do grupo $grupoId excluídas com sucesso!")

        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao excluir transações", e)
        }
    }
}