package com.dieyteixeira.fluxsync.app.di.repository

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.core.graphics.toColorInt
import com.dieyteixeira.fluxsync.app.di.replace.stringToColor
import com.dieyteixeira.fluxsync.app.di.replace.stringToIcon
import com.google.firebase.firestore.QuerySnapshot

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

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

                val icon = stringToIcon(iconString)
                val color = stringToColor(colorString)

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