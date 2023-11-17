package br.com.fiap.trashit.view

import android.telephony.PhoneNumberUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.trashit.R
import br.com.fiap.trashit.view.components.ScreenLabel
import br.com.fiap.trashit.view.components.UserInputTextField
import br.com.fiap.trashit.viewmodel.ContaViewModel
import java.util.Locale

@Composable
fun ContaScreen(viewModel: ContaViewModel, navController: NavController) {
        val conta by viewModel.usuario.collectAsState()
        val endereco by viewModel.endereco.collectAsState()
        val emailError by viewModel.emailError.collectAsState()
        val celularError by viewModel.celularError.collectAsState()
        val senhaVisible by viewModel.senhaVisible.collectAsState()
        val abrirAlterarSenha by viewModel.abrirAlterarSenha.collectAsState()

        val context = LocalContext.current

        if (abrirAlterarSenha){
                AlterarSenhaDialog(viewModel = viewModel)
        }

        Column(modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.shady_grey))
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ScreenLabel(text = "Conta", painterResource(id = R.drawable.baseline_person_2_24))
                UserInputTextField(
                        text = "Nome Completo",
                        value = conta.nomeCompleto,
                        onCheckedFunction = {},
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                UserInputTextField(
                        text = "CPF",
                        value = conta.cpf,
                        onCheckedFunction = {},
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                UserInputTextField(
                        text = "Email",
                        value = conta.email,
                        onCheckedFunction = viewModel::updateEmail,
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        enabled = true,
                        isError = emailError,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                )
                MensagemError(
                        mensagem = "*Preencha com um E-mail válido ( exemplo@email.com )",
                        error = emailError
                )
                Spacer(modifier = Modifier.height(10.dp))
                UserInputTextField(
                        text = "Celular",
                        value = conta.celular,
                        onCheckedFunction = viewModel::updateCelular,
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        enabled = true,
                        isError = celularError,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                )
                MensagemError(
                        mensagem = "*Preencha com um celular válido, apenas números",
                        error = celularError
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                        UserInputTextField(
                                text = "Senha",
                                value = conta.senha,
                                onCheckedFunction = {},
                                visualTransformation = if(senhaVisible) VisualTransformation.None
                                else PasswordVisualTransformation(),
                                KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .weight(4F)
                        )

                        Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Alterar Senha",
                                tint = colorResource(id = R.color.trashIt_green),
                                modifier = Modifier
                                        .weight(1F)
                                        .size(36.dp)
                                        .clickable {
                                                viewModel.toggleAlterarSenha()
                                        }
                        )
                        Icon(
                                painter = painterResource(
                                        id = if (senhaVisible) R.drawable.visible_svgrepo_com
                                        else R.drawable.not_visible_svgrepo_com
                                ),
                                contentDescription = "Mudar visibilidade da senha",
                                tint = colorResource(id = R.color.trashIt_green),
                                modifier = Modifier
                                        .weight(1F)
                                        .size(36.dp)
                                        .clickable {
                                                viewModel.alterarVisualizacaoSenha()
                                        }
                        )

                }
                Spacer(modifier = Modifier.height(10.dp))
                UserInputTextField(
                        text = "CEP",
                        value = endereco.cep,
                        onCheckedFunction = {},
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                /*UserInput(label = "Nome Completo", placeHolder = "Nome Usuário", value = "", modifier = Modifier.fillMaxWidth())
                UserInput(label = "CPF", placeHolder = "000.000.000-00", value = "")
                UserInput(label = "Email", placeHolder = "exemplo@gmail.com", value = "")
                UserInput(label = "Celular", placeHolder = "(00)00000-0000", value = "")
                UserInput(label = "Senha", placeHolder = "**********", value = "")
                UserInput(label = "CEP", placeHolder = "00000-000", value = "")*/
                Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        /*UserInput(
                                label = "Nº Residêcia",
                                placeHolder = conta.isLogged.toString(),
                                value = "",
                                modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        UserInput(
                                label = "Endereço",
                                placeHolder = "Rua exemplo, Bairro Exemplo",
                                value = "",
                                modifier = Modifier.weight(2f)
                        )*/
                        UserInputTextField(
                                text = "NºCasa",
                                value = endereco.numero,
                                onCheckedFunction = {},
                                visualTransformation = VisualTransformation.None,
                                modifier = Modifier
                                        .weight(1F)
                                        .padding(
                                                start = 10.dp,
                                                end = 5.dp
                                        )
                        )
                        UserInputTextField(
                                text = "Endereco",
                                value = "${endereco.rua}, ${endereco.bairro}",
                                onCheckedFunction = {},
                                visualTransformation = VisualTransformation.None,
                                modifier = Modifier
                                        .weight(2F)
                                        .padding(end = 10.dp)
                        )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row{
                        /*Button(
                                shape = RoundedCornerShape(24.dp),
                                onClick = { viewModel.updateUsuario() },
                                colors = ButtonDefaults
                                                .textButtonColors(containerColor =
                                                        colorResource(id = R.color.TrashItGreen)
                                                )
                        ) {
                                Text(
                                        text = "Salvar",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(
                                                horizontal = 20.dp,
                                                vertical = 10.dp
                                        )
                                )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(onClick = {
                                viewModel.logout()
                                navController.navigate("login")
                        }) {
                                Text(text = "Sair")
                        }*/
                        SpecialButtons(
                                text = "Salvar",
                                color = colorResource(id = R.color.trashIt_green),
                                modifier = Modifier.width(140.dp)
                        ) {

                                viewModel.updateUsuario()
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        SpecialButtons(
                                text = "Sair",
                                color = colorResource(id = R.color.trashIt_green),
                                modifier = Modifier.width(140.dp)
                        ) {
                                viewModel.logout()
                                navController.navigate("login")
                        }
                }
                Spacer(modifier = Modifier.height(10.dp))
                SpecialButtons(
                        text = "Deletar Conta",
                        enabled = false,
                        color = colorResource(id = R.color.disabled_red),
                        modifier = Modifier.width(300.dp)
                ) {}
                Spacer(modifier = Modifier.height(100.dp))
        }
}

@Composable
fun SpecialButtons(
        text: String,
        color: Color,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        onCLickFunction: () -> Unit
) {
        Button(
                shape = RoundedCornerShape(24.dp),
                onClick = onCLickFunction,
                colors = ButtonDefaults
                        .textButtonColors(
                                containerColor = color,
                                disabledContainerColor = color,
                                disabledContentColor = Color.LightGray
                        ),
                enabled = enabled,
                modifier = modifier
        ) {
                Text(
                        text = text,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        modifier = Modifier.padding(
                                vertical = 10.dp
                        )
                )
        }
}

@Composable
fun MensagemError(mensagem: String,error: Boolean) {
        if (error){
                Text(
                        text = mensagem,
                        color = Color.Red,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                )
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlterarSenhaDialog(viewModel: ContaViewModel) {
        AlertDialog(onDismissRequest = { viewModel.toggleAlterarSenha()}) {
                Card(
                        colors = CardDefaults
                                .cardColors(containerColor =
                                colorResource(id = R.color.shady_grey)
                                )
                ) {
                        var novaSenha by remember {
                                mutableStateOf("")
                        }
                        var confirmacao by remember {
                                mutableStateOf("")
                        }

                        var visibilidade by remember {
                                mutableStateOf(false)
                        }

                        var senhasDiferentes by remember {
                                mutableStateOf(false)
                        }

                        var senhaVazia by remember {
                                mutableStateOf(false)
                        }

                        var tamanhoSenha by remember {
                                mutableStateOf(false)
                        }

                        Text(
                                text = "Alterando senha",
                                color = Color.White,
                                fontSize = 26.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 40.dp)
                        )

                        UserInputTextField(
                                text = "Nova senha",
                                value = novaSenha,
                                enabled = true,
                                onCheckedFunction = { novaSenha = it },
                                visualTransformation =
                                if(visibilidade) VisualTransformation.None
                                else PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        UserInputTextField(
                                text = "Repita a senha",
                                value = confirmacao,
                                enabled = true,
                                onCheckedFunction = { confirmacao = it },
                                visualTransformation =
                                if(visibilidade) VisualTransformation.None
                                else PasswordVisualTransformation()
                        )
                        MensagemError(
                                mensagem = "*Senhas vazias",
                                error = senhaVazia
                        )
                        MensagemError(
                                mensagem = "*Senhas diferentes",
                                error = senhasDiferentes
                        )
                        MensagemError(
                                mensagem = "*Senha deve ter entre 7 e 20 caracteres",
                                error = tamanhoSenha
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                Icon(
                                        painter = painterResource(id =
                                                if(visibilidade) R.drawable.visible_svgrepo_com
                                                        else R.drawable.not_visible_svgrepo_com
                                        ),
                                        contentDescription = "Mudar visibilidade da senha",
                                        tint = colorResource(id = R.color.trashIt_green),
                                        modifier = Modifier
                                                .size(36.dp)
                                                .clickable {
                                                        visibilidade = visibilidade.not()
                                                }
                                )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                Spacer(modifier = Modifier.width(10.dp))
                                SpecialButtons(
                                        text = "Cancelar",
                                        color = colorResource(id = R.color.disabled_red),
                                        modifier = Modifier.width(120.dp)
                                ) {
                                        viewModel.toggleAlterarSenha()
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                SpecialButtons(
                                        text = "Alterar",
                                        color = colorResource(id = R.color.trashIt_green),
                                        modifier = Modifier.width(120.dp)
                                ) {
                                        tamanhoSenha = false
                                        senhasDiferentes = false
                                        senhaVazia = false
                                        if(novaSenha != confirmacao) {
                                                senhasDiferentes = true
                                        } else if (novaSenha.length !in 7..20) {
                                                tamanhoSenha = true
                                        } else if (novaSenha.isEmpty()) {
                                                senhaVazia = true
                                        } else {
                                                viewModel.updateSenha(novaSenha)
                                                viewModel.toggleAlterarSenha()
                                        }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                }
        }

}
