package com.dieyteixeira.fluxsync.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.components.ButtonPersonalFilled
import com.dieyteixeira.fluxsync.app.components.CustomDialog
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3

@Composable
fun ConfirmDialog(
    text: String,
    onClickClose: () -> Unit,
    onClickYes: () -> Unit
) {

    CustomDialog(
        onClickClose = onClickClose
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Confirmar ação",
                style = MaterialTheme.typography.headlineMedium,
                color = LightColor3
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = ColorFontesLight
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonPersonalFilled(
                    onClick = onClickClose,
                    text = "Não",
                    colorText = Color.White,
                    color = LightColor2,
                    height = 40.dp,
                    width = 100.dp
                )
                ButtonPersonalFilled(
                    onClick = onClickYes,
                    text = "Sim",
                    colorText = Color.White,
                    color = LightColor2,
                    height = 40.dp,
                    width = 100.dp
                )
            }
        }
    }
}

@Composable
fun AlertDialog(
    text: String,
    onClickClose: () -> Unit = {}
) {

    CustomDialog(
        onClickClose = { onClickClose() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Atenção!!",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 20.sp,
                color = LightColor3
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = ColorFontesLight,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
    }
}