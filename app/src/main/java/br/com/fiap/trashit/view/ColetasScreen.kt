package br.com.fiap.trashit.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.trashit.R
import br.com.fiap.trashit.model.Lixeira
import br.com.fiap.trashit.view.components.ScreenLabel
import br.com.fiap.trashit.viewmodel.ColetasViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ColetasScreen(viewModel: ColetasViewModel, navController: NavController) {
        val listaColetas by viewModel.listaColetas.collectAsState()
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val simpleTimeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")
        if (viewModel.refreshed == false) { GlobalScope.launch{viewModel.refreshView() }}

        Box(modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.shady_grey))
        ) {
                if (listaColetas.isEmpty()){
                        Text(
                                text = "Nenhuma coleta realizada até o momento",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier.align(alignment = Alignment.Center)
                        )
                } else {
                        LazyColumn(
                                modifier = Modifier.padding(
                                        bottom = 80.dp,
                                        start = 2.dp,
                                        end = 2.dp
                                )
                        ) {
                                item { Spacer(modifier = Modifier.height(45.dp)) }

                                items(listaColetas) {
                                        CardColeta(
                                                idColeta = it.id,
                                                dataColeta = it.dtColeta,
                                                lixeira = it.lixeira,
                                                simpleDateFormat = simpleDateFormat,
                                                simpleTimeFormat = simpleTimeFormat
                                        )
                                }

                                item { Spacer(modifier = Modifier.height(15.dp)) }
                        }
                }
                ScreenLabel(text = "Histórico", painterResource(id = R.drawable.baseline_access_time_24))
        }
}

@Composable
fun CardColeta(
        idColeta: Long,
        dataColeta: Date,
        lixeira: Lixeira,
        simpleDateFormat: SimpleDateFormat,
        simpleTimeFormat: SimpleDateFormat
) {
        val dataFormatada = simpleDateFormat.format(dataColeta)
        val horaColeta = simpleTimeFormat.format(dataColeta)

        Card(
                colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                ),
                modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(start = 4.dp, end = 4.dp, bottom = 10.dp)) {
             Column(
                     modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
                     verticalArrangement = Arrangement.SpaceBetween
             ) {
                     Row(
                             modifier = Modifier
                                     .fillMaxWidth()
                                     .padding(vertical = 10.dp, horizontal = 10.dp),
                             horizontalArrangement = Arrangement.SpaceBetween,
                             verticalAlignment = Alignment.Top
                     ) {
                             Text(
                                     text = "Coleta Realizada - $dataFormatada\n$horaColeta",
                                     fontSize = 26.sp,
                                     fontWeight = FontWeight.Light,
                                     color = Color.Black
                             )
                             Icon(
                                     imageVector = Icons.Rounded.Check,
                                     contentDescription = null,
                                     tint = colorResource(id = R.color.trashIt_green)
                             )
                     }
                     Row(modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)) {
                             MaterialLabel(
                                     materialBoolean = lixeira.temPlastico,
                                     materialColor = colorResource(id = R.color.plastic_red),
                                     materialName = "Plástico"
                             )
                             MaterialLabel(
                                     materialBoolean = lixeira.temPapel,
                                     materialColor = colorResource(id = R.color.paper_blue),
                                     materialName = "Papel"
                             )
                             MaterialLabel(
                                     materialBoolean = lixeira.temVidro,
                                     materialColor = colorResource(id = R.color.vitro_green),
                                     materialName = "Vidro"
                             )
                             MaterialLabel(
                                     materialBoolean = lixeira.temMetal,
                                     materialColor = colorResource(id = R.color.metal_yellow),
                                     materialName = "Metal"
                             )
                             MaterialLabel(
                                     materialBoolean = lixeira.temOrganico,
                                     materialColor = colorResource(id = R.color.organic_orange),
                                     materialName = "Orgânico"
                             )


                     }
             }   
        }
}

@Composable
fun MaterialLabel(materialBoolean: Boolean, materialColor: Color, materialName: String) {
        if (materialBoolean){
                Box(modifier = Modifier
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(materialColor)
                ){
                        Text(
                                text = materialName,
                                fontSize = 17.sp,
                                color = Color.White,
                                modifier = Modifier.padding(
                                vertical = 2.dp,
                                horizontal = 8.dp
                        ))
                }
        }
}