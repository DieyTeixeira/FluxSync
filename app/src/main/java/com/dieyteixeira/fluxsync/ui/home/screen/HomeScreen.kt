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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ManageStatusBarIcons
import com.dieyteixeira.fluxsync.ui.home.components.AddCategoriaForm
import com.dieyteixeira.fluxsync.ui.home.components.AddContaForm
import com.dieyteixeira.fluxsync.ui.home.components.AddSubcategoriaForm
import com.dieyteixeira.fluxsync.ui.home.components.AddTransactionForm
import com.dieyteixeira.fluxsync.ui.home.components.EditCategoriaForm
import com.dieyteixeira.fluxsync.ui.home.components.EditContaForm
import com.dieyteixeira.fluxsync.ui.home.components.EditSubcategoriaForm
import com.dieyteixeira.fluxsync.ui.home.components.EditTransactionForm
import com.dieyteixeira.fluxsync.ui.home.components.HomePrincipalScreen
import com.dieyteixeira.fluxsync.ui.home.components.NavigationBarItems
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition",
    "UnrememberedMutableState"
)
@Composable
fun HomeScreen(
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    onSignOutClick: () -> Unit
) {

    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val navigationBarItems = remember { NavigationBarItems.values() }
    var selectedIndex by remember { mutableStateOf(0) }
    var pulseEffect by remember { mutableStateOf(false) }

    var showAddTransaction by remember { mutableStateOf(false) }
    var showAddConta by remember { mutableStateOf(false) }
    var showAddCategoria by remember { mutableStateOf(false) }
    var showAddSubcategoria by remember { mutableStateOf(false) }

    var showEditTransaction by remember { mutableStateOf(false) }
    var showEditConta by remember { mutableStateOf(false) }
    var showEditCategoria by remember { mutableStateOf(false) }
    var showEditSubcategoria by remember { mutableStateOf(false) }

    val showForm by derivedStateOf {
        showAddTransaction || showEditTransaction ||
                showAddConta || showEditConta ||
                showAddCategoria || showEditCategoria ||
                showAddSubcategoria || showEditSubcategoria
    }

    val scaleAnim by animateFloatAsState(targetValue = if (showForm) 0.95f else 1.0f)
    val clipAnim by animateDpAsState(targetValue = if (showForm) 25.dp else 0.dp)

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
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceContainerHighest,
                            MaterialTheme.colorScheme.surfaceContainer,
                            MaterialTheme.colorScheme.surfaceContainerLowest)
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
                        ballColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ) {
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
                                            showAddTransaction = true
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(0.85f * pulseScale)
                                            .background(
                                                color = MaterialTheme.colorScheme.surfaceContainer,
                                                shape = RoundedCornerShape(50)
                                            )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(0.85f * pulseScale)
                                            .border(
                                                width = 5.dp,
                                                brush = Brush.linearGradient(
                                                    colors = listOf(MaterialTheme.colorScheme.surfaceContainerLow, MaterialTheme.colorScheme.surfaceContainerHigh)
                                                ),
                                                shape = RoundedCornerShape(50)
                                            )
                                    )
                                    Image(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = "Bottom Bar Icon",
                                        modifier = Modifier.size(20.dp * pulseScale),
                                        colorFilter = ColorFilter.tint(Color.White)
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
                                    Image(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = "Bottom Bar Icon",
                                        modifier = Modifier.size(20.dp),
                                        colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 0.75f))
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
                                                    color = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = revealProgress),
                                                    shape = RoundedCornerShape(
                                                        shapeForm, 100, 100, 100
                                                    )
                                                )
                                        )
                                        Image(
                                            painter = painterResource(id = item.icon),
                                            contentDescription = "Bottom Bar Icon",
                                            modifier = Modifier.size(20.dp),
                                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = revealProgress))
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
                                loginViewModel = loginViewModel,
                                homeViewModel = homeViewModel,
                                userPreferences = userPreferences,
                                onAddClick = { add ->
                                    when (add) {
                                        "transacao" -> showAddTransaction = true
                                        "conta" -> showAddConta = true
                                        "categoria" -> showAddCategoria = true
                                        "subcategoria" -> showAddSubcategoria = true
                                    }
                                },
                                onEditClick = { edit ->
                                    when (edit) {
                                        "transacao" -> showEditTransaction = true
                                        "conta" -> showEditConta = true
                                        "categoria" -> showEditCategoria = true
                                        "subcategoria" -> showEditSubcategoria = true
                                    }
                                },
                                onSignOutClick = onSignOutClick,
                                onClick = {
                                    selectedIndex = 1
                                }
                            )
                        }
                    }
                }
            }
            this@Column.AnimatedVisibility(
                visible = showForm,
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
                        if (showAddTransaction) {
                            AddTransactionForm(
                                homeViewModel = homeViewModel,
                                onClose = { showAddTransaction = false }
                            )
                        }

                        if (showEditTransaction) {
                            EditTransactionForm(
                                homeViewModel = homeViewModel,
                                transacao = homeViewModel.selectedTransaction.value,
                                contas = homeViewModel.contas.value,
                                categorias = homeViewModel.categorias.value,
                                subcategorias = homeViewModel.subcategorias.value,
                                onClose = { showEditTransaction = false }
                            )
                        }

                        if (showAddConta) {
                            AddContaForm(
                                homeViewModel = homeViewModel,
                                onClose = { showAddConta = false }
                            )
                        }

                        if (showEditConta) {
                            EditContaForm(
                                homeViewModel = homeViewModel,
                                conta = homeViewModel.selectedConta.value,
                                onClose = { showEditConta = false }
                            )
                        }

                        if (showAddCategoria) {
                            AddCategoriaForm(
                                homeViewModel = homeViewModel,
                                onClose = { showAddCategoria = false }
                            )
                        }

                        if (showEditCategoria) {
                            EditCategoriaForm(
                                homeViewModel = homeViewModel,
                                categoria = homeViewModel.selectedCategoria.value,
                                onClose = { showEditCategoria = false }
                            )
                        }

                        if (showAddSubcategoria) {
                            AddSubcategoriaForm(
                                homeViewModel = homeViewModel,
                                onClose = { showAddSubcategoria = false }
                            )
                        }

                        if (showEditSubcategoria) {
                            EditSubcategoriaForm(
                                homeViewModel = homeViewModel,
                                subcategoria = homeViewModel.selectedSubcategoria.value,
                                onClose = { showEditSubcategoria = false }
                            )
                        }
                    }
                }
            }
        }
    }
}