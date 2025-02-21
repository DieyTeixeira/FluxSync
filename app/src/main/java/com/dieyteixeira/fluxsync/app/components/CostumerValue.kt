package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.ExpandCircleDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import kotlinx.coroutines.delay

@Composable
fun CustomField(
    value: String,
    onClickVisibility: () -> Unit = {}
) {

    var clickVisibility by remember { mutableStateOf(false) }

    LaunchedEffect(clickVisibility) {
        if (clickVisibility) {
            delay(100)
            onClickVisibility()
            clickVisibility = false
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Transparent)
            .padding(horizontal = 35.dp)
            .clickable{
                clickVisibility = true
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "R$ ",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = value.ifEmpty { "0,00" },
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun CustomKeyboard(
    onClick: (digit: Int) -> Unit,
    onClickClose: () -> Unit,
    onClickBackspace: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(230.dp)
            .height(330.dp)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
            listOf(-2, 0, -1)
        ).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { num ->
                    when (num) {
                        -1 -> IconButton(Icons.Filled.Backspace, 30, onClickBackspace, Modifier.weight(1f))
                        -2 -> IconButton(Icons.Filled.ExpandCircleDown, 35, onClickClose, Modifier.weight(1f))
                        else -> NumberButton(num, onClick, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun NumberButton(
    number: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var pressKey by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(pressKey) {
        if (pressKey) {
            delay(200)
            pressKey = false
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(
                color = if (pressKey) LightColor3 else Color.LightGray,
                shape = RoundedCornerShape(100)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                @OptIn(ExperimentalStdlibApi::class)
                onClick(number)
                pressKey = true
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = if (pressKey) 35.sp else 30.sp,
            fontWeight = FontWeight.Bold,
            color = if (pressKey) Color.White else LightColor3
        )
    }
}

@Composable
private fun IconButton(
    icon: ImageVector,
    size: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var pressKey by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(pressKey) {
        if (pressKey) {
            delay(200)
            pressKey = false
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
                pressKey = true
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(if (pressKey) (size+5).dp else size.dp),
            imageVector = icon,
            contentDescription = "Icon Action",
            tint = if (pressKey) LightColor3 else Color.LightGray
        )
    }
}

fun formatCurrencyInput(currentValue: String, newDigit: Int): String {
    val cleanValue = currentValue.replace(",", "").replace(".", "").filter { it.isDigit() } // Remove símbolos
    if (cleanValue.length >= 7) return currentValue // Impede mais de 7 dígitos

    val newValue = (cleanValue + newDigit.toString()) // Adiciona o novo dígito

    val numericValue = newValue.toLongOrNull() ?: 0L
    val formatted = numericValue.toString().padStart(3, '0') // Garante no mínimo 000

    val integerPart = formatted.dropLast(2).reversed().chunked(3).joinToString(".").reversed().trimStart('.') // Formata milhar
    val decimalPart = formatted.takeLast(2)

    return "$integerPart,$decimalPart"
}

fun removeLastDigit(currentValue: String): String {
    val cleanValue = currentValue.replace(",", "").replace(".", "").filter { it.isDigit() }
    if (cleanValue.length <= 2) return "0,00" // Se apagar tudo, volta ao estado inicial

    val newValue = cleanValue.dropLast(1) // Remove o último número

    val numericValue = newValue.toLongOrNull() ?: 0L
    val formatted = numericValue.toString().padStart(3, '0')

    val integerPart = formatted.dropLast(2).reversed().chunked(3).joinToString(".").reversed().trimStart('.')
    val decimalPart = formatted.takeLast(2)

    return "$integerPart,$decimalPart"
}