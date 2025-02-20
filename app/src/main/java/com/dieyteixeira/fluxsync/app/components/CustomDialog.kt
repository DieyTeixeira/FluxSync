package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.LightColor3

@Composable
fun CustomDialog(
    textConfirm: String,
    colorTextConfirm: Color,
    colorConfirm: Color,
    onClickConfirm: () -> Unit,
    textCancel: String,
    colorTextCancel: Color,
    colorCancel: Color,
    onClickCancel: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = ColorCards,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(24.dp)
                .fillMaxWidth(0.95f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(min = 300.dp)
                ) {
                    content()
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ButtonPersonalOutline(
                        onClick = onClickCancel,
                        text = textCancel,
                        colorText = colorTextCancel,
                        color = colorCancel,
                        height = 35.dp,
                        width = 120.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ButtonPersonalFilled(
                        onClick = onClickConfirm,
                        text = textConfirm,
                        colorText = colorTextConfirm,
                        color = colorConfirm,
                        height = 35.dp,
                        width = 120.dp
                    )
                }
            }
        }
    }
}