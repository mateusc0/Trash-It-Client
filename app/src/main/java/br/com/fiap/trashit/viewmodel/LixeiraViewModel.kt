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
import br.com.fiap.trashit.model.ColetaAPI
import br.com.fiap.trashit.model.EnderecoAPI
import br.com.fiap.trashit.model.Lixeira
import br.com.fiap.trashit.model.UsuarioAPI
import br.com.fiap.trashit.service.database.repository.ColetaRepository
import br.com.fiap.trashit.service.database.repository.EnderecoRepository
import br.com.fiap.trashit.service.trashItService.RetrofitFactory
import br.com.fiap.trashit.view.components.trashItToast
import br.com.fiap.trashit.viewmodel.uiState.LixeiraUiState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LixeiraViewModel(val context: Context): ViewModel() {
    private val enderecoRepository = EnderecoRepository(context)
    private val coletaRepository = ColetaRepository(context)
    private var _endereco = MutableStateFlow<EnderecoAPI>(EnderecoAPI())

    val endereco: StateFlow<EnderecoAPI>
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

    /*var temPlastico by mutableStateOf(endereco.value.lixeira.temPlastico)
    var temPapel by mutableStateOf(endereco.value.lixeira.temPapel)
    var temMetal by mutableStateOf(endereco.value.lixeira.temMetal)
    var temVidro by mutableStateOf(endereco.value.lixeira.temVidro)
    var temOrganico by mutableStateOf(endereco.value.lixeira.temOrganico)
    var precisaColeta by mutableStateOf(endereco.value.lixeira.precisaColeta)*/



    fun alterarLixeira(toastText: String): Unit {
        GlobalScope.launch{
            val enderecoAtt = _endereco.value.copy(
                lixeira = Lixeira(
                    precisaColeta = (uiState.value.precisaColeta).not(),
                    temPlastico = uiState.value.temPlastico,
                    temPapel = uiState.value.temPapel,
                    temMetal = uiState.value.temMetal,
                    temVidro = uiState.value.temVidro,
                    temOrganico = uiState.value.temOrganico
                ))
            if (
                uiState.value.temPlastico ||
                uiState.value.temPapel ||
                uiState.value.temMetal ||
                uiState.value.temVidro ||
                uiState.value.temOrganico
            ) {
                val call4:Call<EnderecoAPI> = RetrofitFactory()
                    .getTrashItService().updateEndereco(id = 1, endereco = enderecoAtt )
                call4.enqueue(object : Callback<EnderecoAPI>{
                    override fun onResponse(call: Call<EnderecoAPI>, response: Response<EnderecoAPI>) {
                        Log.d("TESTE API ENDERECO", "onResponse: ${response.body()}")
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

                    override fun onFailure(call: Call<EnderecoAPI>, t: Throwable) {
                        Log.d("TESTE API ENDERECO", "onResponse: ${t.message}")
                    }

                })
                //trashItToast(text = toastText, context = context)
            }
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
            if (_uiState.value.precisaColeta){
                val coleta = ColetaAPI(
                    id = 0,
                    lixeira = _endereco.value.lixeira
                )
                val callColeta:Call<ColetaAPI> = RetrofitFactory()
                    .getTrashItService().saveColeta(idEndereco = 1, coleta = coleta)
                val callEndereco:Call<EnderecoAPI> = RetrofitFactory()
                    .getTrashItService()
                    .updateEndereco(id = 1, endereco = _endereco.value.copy(lixeira = Lixeira()))
                callColeta.enqueue(object : Callback<ColetaAPI>{
                    override fun onResponse(call: Call<ColetaAPI>, response: Response<ColetaAPI>) {
                        Log.d("TESTE COLETA API", "onResponse: ${response.body()}")
                    }

                    override fun onFailure(call: Call<ColetaAPI>, t: Throwable) {
                        Log.d("TESTE COLETA API", "onResponse: ${t.message}")
                    }

                })
                callEndereco.enqueue(object : Callback<EnderecoAPI>{
                    override fun onResponse(call: Call<EnderecoAPI>, response: Response<EnderecoAPI>) {
                        Log.d("TESTE API ENDERECO", "onResponse: ${response.body()}")
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

                    override fun onFailure(call: Call<EnderecoAPI>, t: Throwable) {
                        Log.d("TESTE API ENDERECO", "onResponse: ${t.message}")
                    }
                })
                makeNotification()
            }
        }
        val coleta: ColetaAPI = ColetaAPI(id = 2, lixeira = _endereco.value.lixeira)
        val lixeira2:Lixeira =  Lixeira(temOrganico = true, temMetal = true)
        val enderecoAPI: EnderecoAPI = EnderecoAPI(id = 1, cep = "09211111",
        numero = "111",
        rua = "Rua Exemplo",
        complemento = "",
        bairro = "Bairro Exemplo",
        cidade = "cidade",
        uf= "SP",
        lixeira = lixeira2,
        )
        val usuarioAPI: UsuarioAPI = UsuarioAPI(
            id = 1,
            nomeCompleto = "Seu Nome",
            cpf = "00000000000",
            email = "seuEmail@email.com",
            celular = "11922222222",
            senha = "senha"
        )

        val call:Call<UsuarioAPI> = RetrofitFactory().getTrashItService().getUsuarioById(1)
        val call1:Call<EnderecoAPI> = RetrofitFactory().getTrashItService().getEnderecoById(1)
        val call2:Call<List<ColetaAPI>> = RetrofitFactory().getTrashItService().getColetasByEnderecoId(1)
        //val call3:Call<ColetaAPI> = RetrofitFactory().getTrashItService().saveColeta(idEndereco = 1, coleta = coleta)
        //val call4:Call<EnderecoAPI> = RetrofitFactory().getTrashItService().updateEndereco(id = 1, endereco = enderecoAPI )
        val call5: Call<UsuarioAPI> = RetrofitFactory().getTrashItService().updateUsuario(id = 1, usuario = usuarioAPI)
        val call6: Call<UsuarioAPI> = RetrofitFactory().getTrashItService()
            .getUsuarioByEmailESenha(email = "seuEmail@email.com", senha = "senha")

        call.enqueue(object: Callback<UsuarioAPI>{
            override fun onResponse(call: Call<UsuarioAPI>, response: Response<UsuarioAPI>) {
                Log.d("TESTE API", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<UsuarioAPI>, t: Throwable) {
                Log.d("TESTE API", "onResponse: ${t.message}")
            }

        })

        call1.enqueue(object : Callback<EnderecoAPI> {
            override fun onResponse(call: Call<EnderecoAPI>, response: Response<EnderecoAPI>) {
                Log.d("TESTE API", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<EnderecoAPI>, t: Throwable) {
                Log.d("TESTE API", "onResponse: ${t.message}")
            }

        })

        call2.enqueue(object : Callback<List<ColetaAPI>> {
            override fun onResponse(
                call: Call<List<ColetaAPI>>,
                response: Response<List<ColetaAPI>>,
            ) {
                Log.d("TESTE API", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<List<ColetaAPI>>, t: Throwable) {
                Log.d("TESTE API", "onResponse: ${t.message}")
            }

        })

        /*call3.enqueue(object : Callback<ColetaAPI>{
            override fun onResponse(call: Call<ColetaAPI>, response: Response<ColetaAPI>) {
                Log.d("TESTE2 API", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<ColetaAPI>, t: Throwable) {
                Log.d("TESTE2 API", "onResponse: ${t.message}")
            }

        })*/

        /*call4.enqueue(object : Callback<EnderecoAPI>{
            override fun onResponse(call: Call<EnderecoAPI>, response: Response<EnderecoAPI>) {
                Log.d("TESTE API ENDERECO", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<EnderecoAPI>, t: Throwable) {
                Log.d("TESTE API", "onResponse: ${t.message}")
            }

        })*/

        call5.enqueue( object : Callback<UsuarioAPI> {
            override fun onResponse(call: Call<UsuarioAPI>, response: Response<UsuarioAPI>) {
                Log.d("TESTE API USUARIO UPDATE", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<UsuarioAPI>, t: Throwable) {
                Log.d("TESTEAPI USUARIO UPDATE", "onResponse: ${t.message}")
            }

        })
        call6.enqueue( object  : Callback<UsuarioAPI> {
            override fun onResponse(call: Call<UsuarioAPI>, response: Response<UsuarioAPI>) {
                Log.d("TESTE API USUARIO", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<UsuarioAPI>, t: Throwable) {
                Log.d("TESTEAPI USUARIO", "onResponse: ${t.message}")
            }

        })



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
        val callEndereco:Call<EnderecoAPI> = RetrofitFactory()
            .getTrashItService().getEnderecoById(1)
        callEndereco.enqueue(object : Callback<EnderecoAPI> {
            override fun onResponse(call: Call<EnderecoAPI>, response: Response<EnderecoAPI>) {
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

            override fun onFailure(call: Call<EnderecoAPI>, t: Throwable) {
                Log.d("TESTE API", "onResponse: ${t.message}")
            }

        })
    }
}