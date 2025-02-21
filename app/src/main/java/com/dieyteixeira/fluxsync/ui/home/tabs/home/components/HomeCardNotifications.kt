package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.ColorCards

@Composable
fun HomeCardNotifications() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(20.dp, 0.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color.Gray,
                spotColor = Color.Gray
            )
            .background(
                color = ColorCards,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Não existem notificações", fontSize = 18.sp)
    }
}