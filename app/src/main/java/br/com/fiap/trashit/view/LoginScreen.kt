package br.com.fiap.trashit.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.trashit.R
import br.com.fiap.trashit.view.components.UserInputTextField
import br.com.fiap.trashit.viewmodel.LoginViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginScreen(context: Context,viewModel: LoginViewModel, navController: NavController) {
    val conta by viewModel.usuario.collectAsState()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.height(120.dp))
                Text(fontSize = 48.sp,text = "TrashIt", fontWeight = FontWeight.Light, color = Color.White)
                Icon(
                    painter = painterResource(id = R.drawable.trash),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = colorResource(id = R.color.trashIt_green)
                )
            }
            Spacer(modifier = Modifier.height(120.dp))
            /*OutlinedTextField(
                label = { Text(text = "Email") },
                value = viewModel.email,
                onValueChange = {viewModel.updateEmail(it)}
            )*/
            UserInputTextField(
            text = "Email",
            value = conta.email,
            onCheckedFunction = {},
            visualTransformation = VisualTransformation.None
        )

            Spacer(modifier = Modifier.height(32.dp))
           /* OutlinedTextField(
                label = { Text(text = "Senha") },
                value = viewModel.password,
                onValueChange = {viewModel.updatePassword(it)},
                visualTransformation = PasswordVisualTransformation()
            )*/
            UserInputTextField(
                text = "Senha",
                value = conta.senha,
                onCheckedFunction = {},
                visualTransformation = PasswordVisualTransformation()
            )
            Text(
                text = "Esqueci minha senha",
                color = colorResource(id = R.color.shady_grey),
                fontSize = 15.sp,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 45.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            LoginButton(navController = navController, onCheckedFunction = viewModel::login)
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "TrashIt Company",
                color = colorResource(id = R.color.shady_grey),
                fontSize = 15.sp
            )

        }
}

@Composable
fun LoginButton(navController: NavController, onCheckedFunction: () -> Unit) {
    OutlinedButton(
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            onCheckedFunction()
            navController.navigate("lixeira")
        }
    ) {
        Text(
            text = "Entrar",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 50.dp)
        )
    }
}
