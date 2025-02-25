package com.dieyteixeira.fluxsync.ui.home.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.LightBackground
import com.dieyteixeira.fluxsync.app.theme.LightColor1
import com.dieyteixeira.fluxsync.app.theme.LightColor2
import com.dieyteixeira.fluxsync.app.theme.LightColor3
import com.dieyteixeira.fluxsync.app.theme.LightColor4
import com.dieyteixeira.fluxsync.app.theme.LightColor5
import com.dieyteixeira.fluxsync.app.theme.ManageStatusBarIcons
import com.dieyteixeira.fluxsync.ui.home.components.HomeAddTransactionScreen
import com.dieyteixeira.fluxsync.ui.home.components.HomePrincipalScreen
import com.dieyteixeira.fluxsync.ui.home.components.NavigationBarItems
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: LoginViewModel,
    onSignOutClick: () -> Unit
) {

    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val navigationBarItems = remember { NavigationBarItems.values() }
    var selectedIndex by remember { mutableStateOf(0) }
    var pulseEffect by remember { mutableStateOf(false) }
    var showTransactionScreen by remember { mutableStateOf(false) }
    val scaleAnim by animateFloatAsState(targetValue = if (showTransactionScreen) 0.95f else 1.0f)
    val clipAnim by animateDpAsState(targetValue = if (showTransactionScreen) 25.dp else 0.dp)

    LaunchedEffect(pulseEffect) {
        if (pulseEffect) {
            delay(100)
            pulseEffect = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(LightColor5, LightColor3, LightColor1)
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Scaffold(
                bottomBar = {
                    AnimatedNavigationBar(
                        modifier = Modifier
                            .height(64.dp),
                        selectedIndex = selectedIndex,
                        cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
                        ballAnimation = Straight(tween(300)),
                        indentAnimation = Height(tween(300)),
                        barColor = ColorCards,
                        ballColor = LightColor4
                    )     {
                            navigationBarItems.forEach { item ->
                                if (item.ordinal == 2) {
                                    val pulseScale by animateFloatAsState(
                                        targetValue = if (pulseEffect) 1.1f else 1f,
                                        animationSpec = tween(
                                            durationMillis = 100,
                                            easing = FastOutSlowInEasing
                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .noRippleClickable {
                                                pulseEffect = true
                                                showTransactionScreen = true
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize(0.85f * pulseScale)
                                                .background(
                                                    color = LightColor3,
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
                                        animationSpec = tween(
                                            durationMillis = 1000,
                                            easing = FastOutSlowInEasing
                                        )
                                    )
                                    val shapeForm by animateIntAsState(
                                        targetValue = if (isSelected) 100 else 0,
                                        animationSpec = tween(
                                            durationMillis = 1000,
                                            easing = FastOutSlowInEasing
                                        )
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
                                            tint = Color.Gray.copy(alpha = 0.75f)
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
                                                        color = LightColor2.copy(alpha = revealProgress),
                                                        shape = RoundedCornerShape(
                                                            shapeForm, 100, 100, 100
                                                        )
                                                    )
                                            )
                                            Icon(
                                                modifier = Modifier.size(25.dp),
                                                imageVector = item.unselectedIcon,
                                                contentDescription = "Bottom Bar Icon Unselected",
                                                tint = Color.White.copy(alpha = revealProgress)
                                            )
                                            Icon(
                                                modifier = Modifier.size(25.dp),
                                                imageVector = item.selectedIcon,
                                                contentDescription = "Bottom Bar Icon Selected",
                                                tint = LightColor2.copy(alpha = revealProgress)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                }
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .scale(scaleAnim)
                                .clip(RoundedCornerShape(clipAnim))
                                .align(Alignment.TopCenter)
                        ) {
                            HomePrincipalScreen(
                                selectedIndex = selectedIndex,
                                viewModel = viewModel,
                                userPreferences = userPreferences,
                                onSignOutClick = onSignOutClick
                            )
                        }
                    }
                }
            }
            this@Column.AnimatedVisibility(
                visible = showTransactionScreen,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeOut()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(50.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                            )
                    ) {
                        HomeAddTransactionScreen(
                            onClose = { showTransactionScreen = false }
                        )
                    }
                }
            }
        }
    }
}