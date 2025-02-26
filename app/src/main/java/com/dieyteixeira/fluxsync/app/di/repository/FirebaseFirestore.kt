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
                val iconString = document.getString("icon") ?: return@mapNotNull null
                val colorString = document.getString("color") ?: return@mapNotNull null
                val descricao = document.getString("descricao") ?: return@mapNotNull null
                val saldoString  = document.getString("saldo") ?: return@mapNotNull null

                val icon = stringToIconConta(iconString)
                val color = stringToColorConta(colorString)
                val saldo = saldoString.replace(",", ".").toDoubleOrNull() ?: return@mapNotNull null

                Conta(icon = icon, color = color, descricao = descricao, saldo = saldo)
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

        val contaMap = hashMapOf(
            "icon" to icon,
            "color" to color,
            "descricao" to descricao,
            "saldo" to saldo
        )

        try {
            val documentRef = db.collection(userEmail)
                .document("Conta")
                .collection("Contas")
                .add(contaMap)
                .await()
            Log.d("FirestoreRepository", "Conta salva com ID: ${documentRef.id}")
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
                val iconString = document.getString("icon") ?: return@mapNotNull null
                val colorString = document.getString("color") ?: return@mapNotNull null
                val descricao = document.getString("descricao") ?: return@mapNotNull null

                val icon = stringToIconCategoria(iconString)
                val color = stringToColorCategoria(colorString)

                Categoria(icon = icon, color = color, descricao = descricao)
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

        val categoriaMap = hashMapOf(
            "icon" to icon,
            "color" to color,
            "descricao" to descricao
        )

        try {
            val documentRef = db.collection(userEmail)
                .document("Categoria")
                .collection("Categorias")
                .add(categoriaMap)
                .await()
            Log.d("FirestoreRepository", "Categoria salva com ID: ${documentRef.id}")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao salvar categoria", e)
        }
    }
}