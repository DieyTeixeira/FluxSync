package com.dieyteixeira.fluxsync.ui.home.screen.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.LightColor4
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@Composable
fun HomeTab(
    viewModel: LoginViewModel,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = LightColor4,
                    shape = RoundedCornerShape(50)
                )
                .padding(20.dp, 5.dp)
                .clickable {
                    onSignOutClick()
                    viewModel.signOut()
                }
        ) {
            androidx.compose.material.Text(
                text = "SAIR",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        androidx.compose.material.Text(text = "Saldo Atual: R$ 2.500,00", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        androidx.compose.material.Text(
            text = "Receitas: R$ 5.000,00 | Despesas: R$ 2.500,00",
            fontSize = 18.sp
        )
    }
}