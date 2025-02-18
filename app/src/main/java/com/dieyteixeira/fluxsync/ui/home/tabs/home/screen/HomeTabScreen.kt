package com.dieyteixeira.fluxsync.ui.home.tabs.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor4
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardNotifications
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardSaldo
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeCardTransactions
import com.dieyteixeira.fluxsync.ui.home.tabs.home.components.HomeTopBar
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel

@Composable
fun HomeTabScreen(
    viewModel: LoginViewModel,
    onSignOutClick: () -> Unit
) {

    var isSaldoVisivel by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(LightColor1)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(35.dp))
                    HomeTopBar()
                    Spacer(modifier = Modifier.height(22.dp))
                    HomeCardNotifications()
                }
            }
        }
        item {
            HomeCardSaldo(
                isSaldoVisivel = isSaldoVisivel,
                onVisibilityChange = { isSaldoVisivel = it }
            )
        }
        item {
            HomeCardTransactions(
                isSaldoVisivel = isSaldoVisivel,
                onVisibilityChange = { isSaldoVisivel = it }
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
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
                    Text(
                        text = "SAIR",
                        color = Color.White
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun PreviewHomeTab() {
//    HomeTab(
//        onSignOutClick = {}
//    )
//}