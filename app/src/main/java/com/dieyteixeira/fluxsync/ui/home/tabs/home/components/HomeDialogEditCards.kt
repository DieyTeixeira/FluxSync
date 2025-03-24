package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.dieyteixeira.fluxsync.app.components.CustomDialogButton
import com.dieyteixeira.fluxsync.app.components.ReorderableItems
import com.dieyteixeira.fluxsync.app.components.move

@SuppressLint("MutableCollectionMutableState")
@Composable
fun EditCardsDialog(
    cards: List<String>,
    enabledCards: Map<String, Boolean>,
    onSave: (List<String>, Map<String, Boolean>) -> Unit,
    onDismiss: () -> Unit
) {
    val tempCards = remember { mutableStateListOf(*cards.toTypedArray()) }
    val tempEnabled = remember { mutableStateMapOf<String, Boolean>().apply { putAll(enabledCards) } }

    CustomDialogButton(
        textConfirm = "Salvar",
        colorTextConfirm = Color.White,
        colorConfirm = MaterialTheme.colorScheme.surfaceContainer,
        onClickConfirm = { onSave(tempCards.toList(), tempEnabled.toMap()) },
        textCancel = "Cancelar",
        colorTextCancel = MaterialTheme.colorScheme.surfaceContainer,
        colorCancel = MaterialTheme.colorScheme.surfaceContainer,
        onClickCancel = onDismiss
    ) {
        ReorderableItems(
            items = tempCards,
            tempEnabled = tempEnabled,
            colorCheck = MaterialTheme.colorScheme.surfaceContainer,
            onMove = { fromIndex, toIndex -> tempCards.move(fromIndex, toIndex)}
        )
    }
}