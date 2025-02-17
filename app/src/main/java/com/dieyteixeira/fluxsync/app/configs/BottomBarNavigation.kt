package com.dieyteixeira.fluxsync.app.configs

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FormatLineSpacing
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Output
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomBarNavigation(
    onNavigateToHome: () -> Unit,
    onNavigateToList: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onNavigateToAdd: () -> Unit,
    content: @Composable () -> Unit
) {

    val bottomBarHeight = 85.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnectionPx = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .background(Color.Transparent)
            .nestedScroll(nestedScrollConnectionPx),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAdd() },
                backgroundColor = LightColor2,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(),
                modifier = Modifier
                    .offset {
                        IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt())
                    }
            ) {
                Icon(Icons.Filled.Add, "")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(55.dp)
                    .offset {
                        IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt())
                    },
                cutoutShape = RoundedCornerShape(50),
                backgroundColor = LightColor2,
                content = {
                    // Row para distribuir os botões ao redor do FAB
                    BottomNavigation(
                        backgroundColor = LightColor1,
                        contentColor = Color.White
                    ) {
//                        Spacer(modifier = Modifier.width(10.dp)) // Espaço antes dos ícones da esquerda

                        // Ícones à esquerda
                        Box(
                            modifier = Modifier
                                .weight(1f),
                            contentAlignment = Alignment.Center

                        ){
                            IconButton(onClick = { onNavigateToHome() }) {
                                Icon(
                                    Icons.Outlined.Home,
                                    contentDescription = "Home",
                                    tint = Color.White
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f),
                            contentAlignment = Alignment.Center

                        ){
                            IconButton(onClick = { onNavigateToList() }) {
                                Icon(
                                    Icons.Outlined.FormatListBulleted,
                                    contentDescription = "List",
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1.5f)) // Espaço flexível para centralizar o FAB

                        // Ícones à direita
                        Box(
                            modifier = Modifier
                                .weight(1f),
                            contentAlignment = Alignment.Center

                        ){
                            IconButton(onClick = { onNavigateToConfig() }) {
                                Icon(
                                    Icons.Outlined.Settings,
                                    contentDescription = "Settings",
                                    tint = Color.White
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f),
                            contentAlignment = Alignment.Center

                        ){
                            IconButton(onClick = { onNavigateToConfig() }) {
                                Icon(
                                    Icons.Outlined.Output,
                                    contentDescription = "Output",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .background(LightColor2)
                )
                content()
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BarNavigation(
    onNavigateToHome: () -> Unit,
    onNavigateToList: () -> Unit,
    onNavigateToConfig: () -> Unit,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current
    val bottomBarHeight = 85.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnectionPx = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .background(
                Color.Transparent
            )
            .nestedScroll(nestedScrollConnectionPx),
        scaffoldState = scaffoldState,
        floatingActionButton = { },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(55.dp)
                    .offset {
                        IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt())
                    },
                cutoutShape = RoundedCornerShape(50),
                backgroundColor = LightColor2,
                content = {
                    BottomNavigation(
                        backgroundColor = LightColor1,
                        contentColor = Color.White
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { onNavigateToHome() }) {
                            Icon(
                                Icons.Outlined.Home,
                                "",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { onNavigateToList() }) {
                            Icon(
                                Icons.Outlined.FormatListBulleted,
                                "",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { onNavigateToConfig() }) {
                            Icon(
                                Icons.Outlined.Settings,
                                "",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(80.dp))
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    )
}