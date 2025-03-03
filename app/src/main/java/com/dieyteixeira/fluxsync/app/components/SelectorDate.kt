package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.LightColor1

@Composable
fun SeletorAno(
    anoInicial: Int = anoAtual(),
    onAnoAlterado: (Int) -> Unit = {}
) {
    var anoAtual by remember { mutableStateOf(anoInicial) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(ColorCards, RoundedCornerShape(10.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(25.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    anoAtual -= 1
                    onAnoAlterado(anoAtual)
                }
                .background(
                    LightColor1,
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "-",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ColorFontesDark
            )
        }

        Button(
            onClick = {
                anoAtual -= 1
                onAnoAlterado(anoAtual)
            },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "-",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ColorFontesDark
            )
        }

        Text(
            text = "$anoAtual",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = ColorFontesDark,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        Box(
            modifier = Modifier
                .size(25.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    anoAtual += 1
                    onAnoAlterado(anoAtual)
                }
                .background(
                    ColorFontesDark,
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ColorFontesDark
            )
        }
    }
}