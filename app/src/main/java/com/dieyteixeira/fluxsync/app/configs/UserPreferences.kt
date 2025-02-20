package com.dieyteixeira.fluxsync.app.configs

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dieyteixeira.fluxsync.app.configs.UserPreferences.Companion.CARDS_ORDER_KEY
import com.dieyteixeira.fluxsync.app.configs.UserPreferences.Companion.ENABLED_CARDS_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreferences(private val context: Context) {

    companion object {
        private val CARDS_ORDER_KEY = stringPreferencesKey("cards_order")
        private val ENABLED_CARDS_KEY = stringPreferencesKey("enabled_cards")
    }

    // Recuperar preferências do usuário
    val userPreferences: Flow<Pair<List<String>, Map<String, Boolean>>> = context.dataStore.data
        .map { preferences ->
            val cardsOrder = preferences[CARDS_ORDER_KEY]?.split(",") ?: listOf("Saldo", "Histórico")
            val enabledCards = preferences[ENABLED_CARDS_KEY]?.split(";")?.associate {
                val (key, value) = it.split(":")
                key to value.toBoolean()
            } ?: cardsOrder.associateWith { true }

            cardsOrder to enabledCards
        }

    // Salvar preferências do usuário
    suspend fun saveUserPreferences(cardsOrder: List<String>, enabledCards: Map<String, Boolean>) {
        val cardsOrderString = cardsOrder.joinToString(",")  // Ex: "Saldo,Transações,Histórico"
        val enabledCardsString = enabledCards.entries.joinToString(";") { "${it.key}:${it.value}" } // Ex: "Saldo:true;Transações:false"

        context.dataStore.edit { preferences ->
            preferences[CARDS_ORDER_KEY] = cardsOrderString
            preferences[ENABLED_CARDS_KEY] = enabledCardsString
        }
    }

    // Limpar preferências do usuário
    suspend fun clearUserPreferences() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}