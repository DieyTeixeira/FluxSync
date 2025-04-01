package com.dieyteixeira.fluxsync.ui.home.tabs.graphic.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dieyteixeira.fluxsync.app.components.IconCategoria
import com.dieyteixeira.fluxsync.app.di.model.Grafico
import com.dieyteixeira.fluxsync.app.theme.ColorFontesDark
import com.dieyteixeira.fluxsync.app.theme.ColorFontesLight
import com.dieyteixeira.fluxsync.app.theme.ColorGrayMedium

@Composable
fun GraphicDetails(
    data: List<Grafico>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 7.dp)
    ) {
        val totalSum = data.sumOf { it.valor }

        data.forEachIndexed { index, details ->
            val percentual = (details.valor / totalSum) * 100

            Surface(
                modifier = Modifier
                    .padding(vertical = 5.dp),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconCategoria(
                            color = details.color,
                            icon = details.icon
                        )
//                        Box(
//                            modifier = Modifier
//                                .background(
//                                    color = details.color,
//                                    shape = RoundedCornerShape(10.dp)
//                                )
//                                .size(25.dp)
//                        ) {
//                            Image(
//                                painter = painterResource(id = details.icon),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .size(20.dp)
//                            )
//                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 15.dp),
                                text = details.nome,
                                style = MaterialTheme.typography.displayMedium,
                                color = ColorFontesDark
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .padding(start = 3.dp)
                                    .background(ColorGrayMedium)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = ColorGrayMedium,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp, 5.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "%.2f%%".format(percentual),
                            style = MaterialTheme.typography.headlineSmall,
                            color = ColorFontesDark
                        )
                        Text(
                            text = "R$ %.2f".format(details.valor),
                            style = MaterialTheme.typography.displaySmall,
                            color = ColorFontesLight
                        )
                    }
                }
            }
        }
    }
}