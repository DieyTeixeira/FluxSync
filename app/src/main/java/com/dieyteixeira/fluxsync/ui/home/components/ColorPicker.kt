package com.dieyteixeira.fluxsync.ui.home.components

import android.graphics.Color.parseColor
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.*
import androidx.core.graphics.toColorInt
import com.dieyteixeira.fluxsync.app.di.replace.stringToColorCategoria

@Composable
fun ColorPicker(
    onSelectedColor: (Color) -> Unit
) {
    val controller = rememberColorPickerController()
    var colorSelected = controller.selectedColor.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = {
                colorSelected = it.color
            }
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AlphaTile(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(
                        color = Color.LightGray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        onSelectedColor(colorSelected)
                    },
                controller = controller
            )
            Text(
                text = "Confirmar cor",
                style = MaterialTheme.typography.displayMedium,
                color = if (colorSelected.luminance() < 0.5f) Color.White else Color.Black
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))
    }
}