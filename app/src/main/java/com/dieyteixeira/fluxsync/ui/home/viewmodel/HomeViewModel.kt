package com.dieyteixeira.fluxsync.ui.home.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.repository.AuthRepository
import com.dieyteixeira.fluxsync.app.di.repository.FirestoreRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _categorias = mutableStateOf<List<Categoria>>(emptyList())
    val categorias = _categorias

    init {
        getCategorias()
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
}