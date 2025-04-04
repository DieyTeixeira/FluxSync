package com.dieyteixeira.fluxsync.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.di.model.Categoria
import com.dieyteixeira.fluxsync.app.di.model.Conta
import com.dieyteixeira.fluxsync.app.di.model.Subcategoria
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesMedium
import com.dieyteixeira.fluxsync.app.theme.ColorGrayDark
import com.dieyteixeira.fluxsync.app.theme.ColorNegative
import com.dieyteixeira.fluxsync.app.theme.ColorPositive
import com.dieyteixeira.fluxsync.ui.home.state.formatarValor

@Composable
fun CategoriasList(
    categorias: Categoria,
    isMostrarButtons: Boolean = false,
    onClickCategoria: (Categoria) -> Unit = {},
    onClickEditar: () -> Unit = {},
    onClickDelete: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = ColorBackground.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 15.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickCategoria(categorias)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconCategoria(
                    color = categorias.color,
                    icon = categorias.icon
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = categorias.descricao,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
            }
            ButtonSinal(categorias.tipo)
        }
        if (isMostrarButtons) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorCards
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorGrayDark
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonPersonalIcon( // editar
                    onClick = {
                        onClickEditar()
                    },
                    icon = R.drawable.icon_editar,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    size = 35.dp,
                    sizeIcon = 18.dp
                )
                ButtonPersonalIcon( // excluir
                    onClick = {
                        onClickDelete()
                    },
                    icon = R.drawable.icon_excluir,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    size = 35.dp
                )
            }
        }
    }
}

@Composable
fun ContasList(
    contas: Conta,
    isMostrarButtons: Boolean = false,
    onClickConta: (Conta) -> Unit,
    onClickEditar: () -> Unit = {},
    onClickDelete: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = ColorBackground.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 15.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickConta(contas)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconConta(
                icon = contas.icon,
                color = contas.color
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = contas.descricao,
                    style = MaterialTheme.typography.headlineSmall
                )
                Row {
                    Text(
                        text = "Saldo:  ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ColorFontesMedium
                    )
                    Text(
                        text = formatarValor(contas.saldo),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (contas.saldo > 0) ColorPositive else ColorNegative
                    )
                }
            }
        }
        if (isMostrarButtons) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorCards
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorGrayDark
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonPersonalIcon( // editar
                    onClick = {
                        onClickEditar()
                    },
                    icon = R.drawable.icon_editar,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    size = 35.dp,
                    sizeIcon = 18.dp
                )
                ButtonPersonalIcon( // excluir
                    onClick = {
                        onClickDelete()
                    },
                    icon = R.drawable.icon_excluir,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    size = 35.dp
                )
            }
        }
    }
}

@Composable
fun SubcategoriasList(
    subcategorias: Subcategoria,
    isMostrarButtons: Boolean = false,
    onClickSubcategoria: (Subcategoria) -> Unit = {},
    onClickEditar: () -> Unit = {},
    onClickDelete: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = ColorBackground.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 15.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickSubcategoria(subcategorias)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconCategoria(
                color = subcategorias.color,
                icon = subcategorias.icon
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = subcategorias.descricao,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
        }
        if (isMostrarButtons) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorCards
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1f to ColorGrayDark
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonPersonalIcon( // editar
                    onClick = {
                        onClickEditar()
                    },
                    icon = R.drawable.icon_editar,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    size = 35.dp,
                    sizeIcon = 18.dp
                )
                ButtonPersonalIcon( // excluir
                    onClick = {
                        onClickDelete()
                    },
                    icon = R.drawable.icon_excluir,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    size = 35.dp
                )
            }
        }
    }
}