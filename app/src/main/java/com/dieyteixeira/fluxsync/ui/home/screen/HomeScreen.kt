package com.dieyteixeira.fluxsync.ui.home.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dieyteixeira.fluxsync.app.theme.Background
import com.dieyteixeira.fluxsync.app.theme.LightBackground
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.app.theme.LightColor4
import com.dieyteixeira.fluxsync.app.theme.LightColor5
import com.dieyteixeira.fluxsync.app.theme.ManageStatusBarIcons
import com.dieyteixeira.fluxsync.ui.home.screen.tabs.ChartTab
import com.dieyteixeira.fluxsync.ui.home.screen.tabs.HomeTab
import com.dieyteixeira.fluxsync.ui.home.screen.tabs.SettingsTab
import com.dieyteixeira.fluxsync.ui.home.screen.tabs.TransactionTab
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: LoginViewModel,
    onSignOutClick: () -> Unit
) {

    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

    val navController = rememberNavController()
    val navigationBarItems = remember { NavigationBarItems.values() }
    var selectedIndex by remember { mutableStateOf(0) }
    var pulseEffect by remember { mutableStateOf(false) }
    var startDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(pulseEffect) {
        if (pulseEffect) {
            delay(100)
            pulseEffect = false
        }
        if (startDialog) {
            delay(300)
            showDialog = true
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier.height(64.dp),
                selectedIndex = selectedIndex,
                cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
                ballAnimation = Straight(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = Background,
                ballColor = LightColor5
            ) {
                navigationBarItems.forEach { item ->
                    if (item.ordinal == 2) {
                        val pulseScale by animateFloatAsState(
                            targetValue = if (pulseEffect) 1.1f else 1f,
                            animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .noRippleClickable {
                                    pulseEffect = true
                                    startDialog = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(0.85f * pulseScale)
                                    .background(
                                        color = LightColor4,
                                        shape = RoundedCornerShape(50)
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(0.85f * pulseScale)
                                    .border(
                                        width = 5.dp,
                                        brush = LightBackground,
                                        shape = RoundedCornerShape(50)
                                    )
                            )
                            Icon(
                                modifier = Modifier.size(30.dp * pulseScale),
                                imageVector = item.selectedIcon,
                                contentDescription = "Bottom Bar Icon",
                                tint = Color.White
                            )
                        }
                    } else {
                        val isSelected = selectedIndex == item.ordinal
                        val revealProgress by animateFloatAsState(
                            targetValue = if (isSelected) 1f else 0f,
                            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                        )
                        val shapeForm by animateIntAsState(
                            targetValue = if (isSelected) 100 else 0,
                            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .noRippleClickable { selectedIndex = item.ordinal },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(25.dp),
                                imageVector = item.unselectedIcon,
                                contentDescription = "Bottom Bar Icon",
                                tint = Color.LightGray
                            )
                            Box(
                                modifier = Modifier
                                    .size(45.dp)
                                    .drawWithContent {
                                        val height = size.height * revealProgress
                                        clipRect(top = 0f, bottom = height) {
                                            this@drawWithContent.drawContent()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(37.dp)
                                        .rotate(45f)
                                        .border(
                                            width = 2.dp,
                                            color = LightColor3.copy(alpha = revealProgress),
                                            shape = RoundedCornerShape(
                                                shapeForm, 100, 100, 100
                                            )
                                        )
                                )
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    imageVector = item.unselectedIcon,
                                    contentDescription = "Bottom Bar Icon",
                                    tint = Color.White.copy(alpha = revealProgress)
                                )
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    imageVector = item.selectedIcon,
                                    contentDescription = "Bottom Bar Icon",
                                    tint = LightColor3.copy(alpha = revealProgress)
                                )
                            }
                        }
                    }
                }
            }
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .background(LightColor3)
            )
            NavigationBarItems.entries[selectedIndex].screen(viewModel, onSignOutClick)
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    startDialog = false
                },
                title = { Text(text = "Novo Registro") },
                text = { Text(text = "Adicionar nova transação.") },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        startDialog = false
                    }) {
                        Text(text = "Fechar")
                    }
                }
            )
        }
    }
}

enum class NavigationBarItems(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: @Composable (LoginViewModel, () -> Unit) -> Unit,
) {
    Home(
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Filled.Home,
        screen = { viewModel, onSignOutClick -> HomeTab(viewModel, onSignOutClick) }
    ),
    Transaction(
        selectedIcon = Icons.Outlined.Description,
        unselectedIcon = Icons.Filled.Description,
        screen = { _, _ -> TransactionTab() }
    ),
    Add(
        selectedIcon = Icons.Outlined.Add,
        unselectedIcon = Icons.Filled.Add,
        screen = { _, _ -> }
    ),
    Chart(
        selectedIcon = Icons.Outlined.InsertChart,
        unselectedIcon = Icons.Filled.InsertChart,
        screen = { _, _ -> ChartTab() }
    ),
    Settings(
        selectedIcon = Icons.Outlined.Settings,
        unselectedIcon = Icons.Filled.Settings,
        screen = { _, _ -> SettingsTab() }
    )
}