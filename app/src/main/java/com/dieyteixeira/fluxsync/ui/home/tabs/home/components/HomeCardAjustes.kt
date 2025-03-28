package com.dieyteixeira.fluxsync.ui.home.tabs.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.R
import com.dieyteixeira.fluxsync.app.theme.ColorBackground
import com.dieyteixeira.fluxsync.app.theme.ColorCards
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorLine
import com.dieyteixeira.fluxsync.ui.home.state.ItemAjuste
import com.dieyteixeira.fluxsync.ui.home.state.itemsAjuste

@Composable
fun HomeCardAjusts(
    onEditItem: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 3.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ajustes",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ColorFontesDark,
                    modifier = Modifier.padding(0.dp, 3.dp)
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.95f).height(1.dp).background(ColorLine).align(Alignment.CenterHorizontally))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 295.dp)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(itemsAjuste.size) { index ->
                AjusteItem(
                    itemText = itemsAjuste[index],
                    onItemClick = {
                        onEditItem(itemsAjuste[index].text)
                    }
                )
            }
        }
    }
}

@Composable
fun AjusteItem(
    itemText: ItemAjuste,
    onItemClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .weight(3f)
                .height(40.dp)
                .background(
                    color = ColorBackground.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp)
                )
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = itemText.text,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .background(
                    color = ColorBackground.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp)
                )
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    onItemClick()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_editar),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainer)
            )
        }
    }
}