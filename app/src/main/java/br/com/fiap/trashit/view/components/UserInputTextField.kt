package br.com.fiap.trashit.view.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.trashit.R

@Composable
fun UserInputTextField(
    text: String,
    value: String,
    onCheckedFunction: (it: String) -> Unit,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    enabled: Boolean = false,
    isError: Boolean = false,
    modifier: Modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
) {
    OutlinedTextField(
        label = {
            val textColor = if (isError) Color.Red else Color.White
            Text(
                text = text,
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )
        },
        value = value,
        singleLine = true,
        onValueChange = { onCheckedFunction(it) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.White,
            focusedBorderColor = colorResource(id = R.color.trashIt_green),
            cursorColor = colorResource(id = R.color.trashIt_green),
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            disabledBorderColor = Color.DarkGray,
            disabledTextColor = Color.LightGray,
            errorCursorColor = Color.Red,
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
            errorTextColor = Color.Red
        ),
        modifier = modifier
    )
}