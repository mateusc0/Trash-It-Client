package br.com.fiap.trashit.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.trashit.R
import br.com.fiap.trashit.view.components.ScreenLabel
import br.com.fiap.trashit.view.components.trashItToast
import br.com.fiap.trashit.viewmodel.LixeiraViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LixeiraScreen(viewModel: LixeiraViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()
    if (viewModel.endereco.value.id == 0L) { viewModel.refreshView() }
    val alert: String
    val buttonText: String
    val buttomColor: Int
    val toastText: String
    if (uiState.precisaColeta){
        alert= "Coleta Ativa"
        buttonText = "Cancelar"
        buttomColor = R.color.plastic_red
        toastText = "Coleta cancelada"

    } else {
        alert = "Selecione os tipos de resíduos para sua coleta"
        buttonText = "Trash It"
        buttomColor = R.color.trashIt_green
        toastText = "Nossa equipe foi notificada"
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = Color.White).fillMaxSize()
    ) {
        ScreenLabel(text = "Lixeira", painterResource(id = R.drawable.baseline_delete_24))
        /*Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(text = "Lixeira")
        }*/
        Spacer(modifier = Modifier.height(35.dp))
        Text(text = alert, fontSize = 28.sp, fontWeight = FontWeight.Light, color = Color.Black, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 12.dp))
        Spacer(modifier = Modifier.height(35.dp))
        Column {
            LixeiraCheckbox(
                text = "Plástico",
                checkedBoolean = uiState.temPlastico,
                enabledBoolean = uiState.precisaColeta,
                onCheckedFunction = viewModel::updateTemPlastico
            )
            LixeiraCheckbox(
                text = "Papel",
                checkedBoolean = uiState.temPapel,
                enabledBoolean = uiState.precisaColeta,
                onCheckedFunction = viewModel::updateTemPapel
            )
            LixeiraCheckbox(
                text = "Vidro",
                checkedBoolean = uiState.temVidro,
                enabledBoolean = uiState.precisaColeta,
                onCheckedFunction = viewModel::updateTemVidro
            )
            LixeiraCheckbox(
                text = "Metal",
                checkedBoolean = uiState.temMetal,
                enabledBoolean = uiState.precisaColeta,
                onCheckedFunction = viewModel::updateTemMetal
            )
            LixeiraCheckbox(
                text = "Orgânico",
                checkedBoolean = uiState.temOrganico,
                enabledBoolean = uiState.precisaColeta,
                onCheckedFunction = viewModel::updateTemOrganico
            )
            /*Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.temPlastico,
                    enabled = uiState.precisaColeta.not(),
                    onCheckedChange = {
                        viewModel.updateTemPlastico(it)
                    }
                )
                Text(text = "Plástico")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.temPapel,
                    enabled = uiState.precisaColeta.not(),
                    onCheckedChange = {
                        viewModel.updateTemPapel(it)
                    }
                )
                Text(text = "Papel")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.temVidro,
                    enabled = uiState.precisaColeta.not(),
                    onCheckedChange = {
                        viewModel.updateTemVidro(it)
                    }
                )
                Text(text = "Vidro")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.temMetal,
                    enabled = uiState.precisaColeta.not(),
                    onCheckedChange = {LocalContext.current
                        viewModel.updateTemMetal(it)
                    }
                )
                Text(text = "Metal")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.temOrganico,
                    enabled = uiState.precisaColeta.not(),
                    onCheckedChange = {
                        viewModel.updateTemOrganico(it)
                    }
                )
                Text(text = "Orgânico")
            }*/
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = buttomColor)
            ),
            shape = RoundedCornerShape(24.dp),
            onClick = {
                viewModel.alterarLixeira(toastText = toastText)

                if (uiState.precisaColeta.not()) {
                    GlobalScope.launch {
                        delay(3000)
                        viewModel.realizarColeta()
                    }
                }
            },
            modifier = Modifier
                .height(80.dp)
                .width(280.dp)
        ) {
            Text(text = buttonText, fontSize = 40.sp, fontWeight = FontWeight.Light, color = Color.White)

            if (uiState.precisaColeta.not()){
                Icon(
                    painter = painterResource(id = R.drawable.recycle),
                    contentDescription = "",
                    modifier = Modifier.padding( start = 15.dp),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }

}

@Composable
fun LixeiraCheckbox(
    text: String,
    checkedBoolean: Boolean,
    enabledBoolean: Boolean,
    onCheckedFunction: (it: Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checkedBoolean,
            enabled = enabledBoolean.not(),
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.trashIt_green),
                disabledCheckedColor = colorResource(id = R.color.disabled_green)
            ),
            onCheckedChange = {
                onCheckedFunction(it)
            }
        )
        Text(text = text,color = Color.Black, fontSize = 26.sp, fontWeight = FontWeight.Light)
    }

}