package br.com.fiap.trashit.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.trashit.R

@Composable
fun ScreenLabel(text: String, icon: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 12.dp, end = 2.dp, top = 2.dp, bottom = 2.dp)
            )
            Icon(
                painter = icon,
                contentDescription = null,
                tint = colorResource(id = R.color.trashIt_green),
                modifier = Modifier.padding(start = 2.dp, end = 12.dp, top = 2.dp, bottom = 2.dp)
            )
        }


    }
}
