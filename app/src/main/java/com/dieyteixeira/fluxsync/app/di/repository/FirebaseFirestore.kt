package com.dieyteixeira.fluxsync.app.di.repository

import android.util.Log
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.dieyteixeira.fluxsync.app.di.replace.stringToColorCategoria
import com.dieyteixeira.fluxsync.app.di.replace.stringToColorConta
import com.dieyteixeira.fluxsync.app.di.replace.stringToIconCategoria
import com.dieyteixeira.fluxsync.app.di.replace.stringToIconConta
import com.google.firebase.firestore.QuerySnapshot
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
                val saldoString  = document.getString("saldo") ?: return@mapNotNull null

                val icon = stringToIconConta(iconString)
                val color = stringToColorConta(colorString)
                val saldo = saldoString.replace(",", ".").toDoubleOrNull() ?: return@mapNotNull null

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
        saldo: String
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

    suspend fun salvarTransacao(
        descricao: String,
        valor: Double,
        tipo: String, // "receita" ou "despesa"
        categoriaId: String,
        contaId: String,
        data: Date,
        lancamento: String, // "único", "fixo" ou "parcelado"
        parcelas: Int = 0,
        observacao: String = ""
    ) {
        val user = auth.currentUser
        val userEmail = user?.email ?: return

        try {
            val transacoesRef = db.collection(userEmail)
                .document("Transacoes")
                .collection("Lançamentos")

            when (lancamento) {
                "único" -> {
                    val novoId = transacoesRef.document().id
                    val transacaoMap = criarTransacaoMap(novoId, descricao, valor, tipo, categoriaId, contaId, data, lancamento, observacao)
                    transacoesRef.document(novoId).set(transacaoMap).await()
                }

                "fixo" -> {
                    val calendar = Calendar.getInstance()
                    calendar.time = data

                    for (i in 0..11) { // Criando para os próximos 12 meses
                        val novoId = transacoesRef.document().id
                        val transacaoMap = criarTransacaoMap(novoId, descricao, valor, tipo, categoriaId, contaId, calendar.time, lancamento, observacao)
                        transacoesRef.document(novoId).set(transacaoMap).await()
                        calendar.add(Calendar.MONTH, 1) // Avança um mês
                    }
                }

                "parcelado" -> {
                    val valorParcela = valor / parcelas
                    val calendar = Calendar.getInstance()
                    calendar.time = data

                    for (i in 1..parcelas) {
                        val novoId = transacoesRef.document().id
                        val transacaoMap = criarTransacaoMap(novoId, descricao, valorParcela, tipo, categoriaId, contaId, calendar.time, lancamento, observacao, i)
                        transacoesRef.document(novoId).set(transacaoMap).await()
                        calendar.add(Calendar.MONTH, 1) // Avança um mês para a próxima parcela
                    }
                }
            }

            Log.d("FirestoreRepository", "Transação salva com sucesso!")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao salvar transação", e)
        }
    }

    // Função auxiliar para criar o map da transação
    private fun criarTransacaoMap(
        id: String,
        descricao: String,
        valor: Double,
        tipo: String,
        categoriaId: String,
        contaId: String,
        data: Date,
        lancamento: String,
        observacao: String,
        parcela: Int = 0
    ): Map<String, Any> {
        return mapOf(
            "id" to id,
            "descricao" to descricao,
            "valor" to valor,
            "tipo" to tipo,
            "categoriaId" to categoriaId,
            "contaId" to contaId,
            "data" to data,
            "lancamento" to lancamento,
            "parcelas" to parcela,
            "observacao" to observacao,
            "situacao" to "pendente", // Inicialmente, todas as transações ficam como "pendente"
            "dataPagamento" to null // Será preenchido quando for marcado como pago
        ) as Map<String, Any>
    }
}