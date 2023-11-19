package br.com.fiap.trashit.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.R
import br.com.fiap.trashit.model.Coleta
import br.com.fiap.trashit.model.Endereco
import br.com.fiap.trashit.model.Lixeira
import br.com.fiap.trashit.model.Usuario
import br.com.fiap.trashit.service.trashItService.RetrofitFactory
import br.com.fiap.trashit.viewmodel.uiState.LixeiraUiState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LixeiraViewModel(val context: Context): ViewModel() {
    private var _endereco = MutableStateFlow<Endereco>(Endereco())

    val endereco: StateFlow<Endereco>
        get() = _endereco

    private val _uiState = MutableStateFlow<LixeiraUiState>(LixeiraUiState(
        temPlastico = _endereco.value.lixeira.temPlastico,
        temPapel = _endereco.value.lixeira.temPapel,
        temMetal = _endereco.value.lixeira.temMetal,
        temVidro = _endereco.value.lixeira.temVidro,
        temOrganico = _endereco.value.lixeira.temOrganico,
        precisaColeta = _endereco.value.lixeira.precisaColeta
    ))
    val uiState: StateFlow<LixeiraUiState>
        get() = _uiState

    fun alterarLixeira(toastText: String): Unit {
        GlobalScope.launch{
            val enderecoAtt = _endereco.value.copy(
                lixeira = Lixeira(
                    precisaColeta = if (!(
                        uiState.value.temPlastico ||
                        uiState.value.temPapel ||
                        uiState.value.temMetal ||
                        uiState.value.temVidro ||
                        uiState.value.temOrganico)
                    ) false
                    else (uiState.value.precisaColeta).not(),

                    temPlastico = uiState.value.temPlastico,
                    temPapel = uiState.value.temPapel,
                    temMetal = uiState.value.temMetal,
                    temVidro = uiState.value.temVidro,
                    temOrganico = uiState.value.temOrganico
                ))
            /*if (
                uiState.value.temPlastico ||
                uiState.value.temPapel ||
                uiState.value.temMetal ||
                uiState.value.temVidro ||
                uiState.value.temOrganico
            ) {*/
                val call4:Call<Endereco> = RetrofitFactory()
                    .getTrashItService().updateEndereco(id = 1, endereco = enderecoAtt )
                call4.enqueue(object : Callback<Endereco>{
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        _endereco.update { response.body()!! }
                        _uiState.update { LixeiraUiState(
                            temPlastico = _endereco.value.lixeira.temPlastico,
                            temPapel = _endereco.value.lixeira.temPapel,
                            temMetal = _endereco.value.lixeira.temMetal,
                            temVidro = _endereco.value.lixeira.temVidro,
                            temOrganico = _endereco.value.lixeira.temOrganico,
                            precisaColeta = _endereco.value.lixeira.precisaColeta
                        ) }
                    }

                    override fun onFailure(call: Call<Endereco>, t: Throwable) {
                        Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                                " iniciado ou está rodando adequadamente")
                    }

                })
                //trashItToast(text = toastText, context = context)
        //    }
            //trashItToast(text = "Sua lixeira já está vazia", context = context)

        }
    }

    fun updateTemPlastico(value: Boolean):Unit {
        _uiState.update { currentState -> currentState.copy(
            temPlastico = value
        ) }
    }
    fun updateTemPapel(value: Boolean):Unit {
        _uiState.update { currentState -> currentState.copy(
            temPapel = value
        ) }
    }
    fun updateTemMetal(value: Boolean):Unit {
        _uiState.update { currentState -> currentState.copy(
            temMetal = value
        ) }
    }
    fun updateTemVidro(value: Boolean):Unit {
        _uiState.update { currentState -> currentState.copy(
            temVidro = value
        ) }
    }
    fun updateTemOrganico(value: Boolean):Unit {
        _uiState.update { currentState -> currentState.copy(
            temOrganico = value
        ) }
    }

    fun realizarColeta():Unit {
        GlobalScope.launch {
            //delay(3000)
            if (_uiState.value.precisaColeta &&
                        ( uiState.value.temPlastico ||
                        uiState.value.temPapel ||
                        uiState.value.temMetal ||
                        uiState.value.temVidro ||
                        uiState.value.temOrganico
                )){
                val coleta = Coleta(
                    id = 0,
                    lixeira = _endereco.value.lixeira
                )
                val callColeta:Call<Coleta> = RetrofitFactory()
                    .getTrashItService().saveColeta(idEndereco = 1, coleta = coleta)
                val callEndereco:Call<Endereco> = RetrofitFactory()
                    .getTrashItService()
                    .updateEndereco(id = 1, endereco = _endereco.value.copy(lixeira = Lixeira()))
                callColeta.enqueue(object : Callback<Coleta>{
                    override fun onResponse(call: Call<Coleta>, response: Response<Coleta>) {
                        Log.d("TESTE COLETA API", "onResponse: SUCCESS!!!")
                    }

                    override fun onFailure(call: Call<Coleta>, t: Throwable) {
                        Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                                " iniciado ou está rodando adequadamente")
                    }

                })
                callEndereco.enqueue(object : Callback<Endereco>{
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        _endereco.update { response.body()!! }
                        _uiState.update { LixeiraUiState(
                            temPlastico = _endereco.value.lixeira.temPlastico,
                            temPapel = _endereco.value.lixeira.temPapel,
                            temMetal = _endereco.value.lixeira.temMetal,
                            temVidro = _endereco.value.lixeira.temVidro,
                            temOrganico = _endereco.value.lixeira.temOrganico,
                            precisaColeta = _endereco.value.lixeira.precisaColeta
                        )}
                    }

                    override fun onFailure(call: Call<Endereco>, t: Throwable) {
                        Log.d("TESTE API ENDERECO", "onResponse: ${t.message}")
                    }
                })
                makeNotification()
            }
        }
        val coleta: Coleta = Coleta(id = 2, lixeira = _endereco.value.lixeira)
        val lixeira2:Lixeira =  Lixeira(temOrganico = true, temMetal = true)
        val enderecoAPI: Endereco = Endereco(id = 1, cep = "09211111",
        numero = "111",
        rua = "Rua Exemplo",
        complemento = "",
        bairro = "Bairro Exemplo",
        cidade = "cidade",
        uf= "SP",
        lixeira = lixeira2,
        )
        val usuarioAPI: Usuario = Usuario(
            id = 1,
            nomeCompleto = "Seu Nome",
            cpf = "00000000000",
            email = "seuEmail@email.com",
            celular = "11922222222",
            senha = "senha"
        )
    }

    fun makeNotification() {
        val CHANNEL_ID = "CHANNEL_ID_NOTIFICATION"
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)

        builder
            .setSmallIcon(R.drawable.trash)
            .setContentTitle("Trash It")
            .setContentText("Coleta de lixo realizada")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val intent = Intent(context, LixeiraViewModel::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        builder.setContentIntent(pendingIntent)

        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if(notificationChannel == null){ notificationChannel = NotificationChannel(
                    CHANNEL_ID,
                    "Trash It Channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
                    notificationChannel.enableVibration(true)
                }
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())

    }

    fun refreshView() {
        val callEndereco:Call<Endereco> = RetrofitFactory()
            .getTrashItService().getEnderecoById(1)
        callEndereco.enqueue(object : Callback<Endereco> {
            override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                _endereco.update { response.body()!! }
                _uiState.update { LixeiraUiState(
                    temPlastico = _endereco.value.lixeira.temPlastico,
                    temPapel = _endereco.value.lixeira.temPapel,
                    temMetal = _endereco.value.lixeira.temMetal,
                    temVidro = _endereco.value.lixeira.temVidro,
                    temOrganico = _endereco.value.lixeira.temOrganico,
                    precisaColeta = _endereco.value.lixeira.precisaColeta
                )}
            }

            override fun onFailure(call: Call<Endereco>, t: Throwable) {
                Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                        " iniciado ou está rodando adequadamente")
            }

        })
    }
}