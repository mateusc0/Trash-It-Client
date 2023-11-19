package br.com.fiap.trashit.viewmodel

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.EnderecoAPI
import br.com.fiap.trashit.model.UsuarioAPI
import br.com.fiap.trashit.service.database.repository.EnderecoRepository
import br.com.fiap.trashit.service.database.repository.UsuarioRepository
import br.com.fiap.trashit.service.trashItService.RetrofitFactory
import br.com.fiap.trashit.view.components.trashItToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContaViewModel(val context: Context): ViewModel() {

    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
    private var _usuario = MutableStateFlow<UsuarioAPI>(
        UsuarioAPI()
    )

    val usuario: StateFlow<UsuarioAPI>
        get() = _usuario

    private var _usuarioPast = MutableStateFlow<UsuarioAPI>(
        UsuarioAPI()
    )

    val usuarioPast: StateFlow<UsuarioAPI>
        get() = _usuarioPast

    private var _endereco = MutableStateFlow<EnderecoAPI>(
        EnderecoAPI()
    )
    val endereco: StateFlow<EnderecoAPI>
        get() = _endereco

    private var _emailError = MutableStateFlow<Boolean>(false)
    val emailError: StateFlow<Boolean>
        get() = _emailError

    private var _celularError = MutableStateFlow<Boolean>(false)
    val celularError: StateFlow<Boolean>
        get() = _celularError

    private var _senhaVisible = MutableStateFlow<Boolean>(false)
    val senhaVisible: StateFlow<Boolean>
        get() = _senhaVisible

    private var _abrirAlterarSenha = MutableStateFlow<Boolean>(false)
    val abrirAlterarSenha: StateFlow<Boolean>
        get() = _abrirAlterarSenha

    fun updateEmail(email: String):Unit {
        _usuario.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }
    fun updateCelular(celular: String):Unit {
        _usuario.update { currentState ->
            currentState.copy(
                celular = celular
            )
        }
    }
    fun updateSenha(senha: String):Unit {
        GlobalScope.launch {
            _usuario.update {
                it.copy(senha = senha)
            }
            updateUsuario()
        }

    }
    /*fun logout():Unit {

        RetrofitFactory().getTrashItService().updateUsuario(_usuario.value.id,
            _usuario.value).execute().body()!!
    }*/

    /*_usuario.value != RetrofitFactory().getTrashItService().getUsuarioById(1)
    .execute().body()!!*/

    fun updateUsuario():Unit {
        GlobalScope.launch {
            emailErrorCheck()
            celularErrorCheck()
            if ( !_emailError.value && !_celularError.value &&
                (_usuarioPast.value != _usuario.value)) {
                Log.d("TESTING ALLL", (_usuarioPast != _usuario).toString())
                RetrofitFactory().getTrashItService().updateUsuario(
                    _usuario.value.id,
                    _usuario.value
                ).enqueue(object : Callback<UsuarioAPI> {
                    override fun onResponse(
                        call: Call<UsuarioAPI>,
                        response: Response<UsuarioAPI>,
                    ) {
                        _usuario.update { response.body()!! }
                    }

                    override fun onFailure(call: Call<UsuarioAPI>, t: Throwable) {
                        Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                                " iniciado ou está rodando adequadamente")
                        System.exit(0)
                    }

                })
                _usuario.update {
                    RetrofitFactory().getTrashItService().getUsuarioById(1).execute().body()!!
                }
            }
        }
    }

    fun emailErrorCheck() {
        if (Patterns.EMAIL_ADDRESS.matcher(_usuario.value.email).matches() ||
                _usuario.value.email.isEmpty()){
            _emailError.update { false }
        } else {
            _emailError.update { true }
        }
    }

    fun celularErrorCheck() {
        if (_usuario.value.celular.length == 11){
            _celularError.update { false }
        } else {
            _celularError.update { true }
        }
    }

    fun alterarVisualizacaoSenha() {
        _senhaVisible.update { _senhaVisible.value.not() }
    }

    fun toggleAlterarSenha() {
        _abrirAlterarSenha.update { _abrirAlterarSenha.value.not() }
    }

     fun refreshView(){
        val callUsuario: Call<UsuarioAPI> = RetrofitFactory().getTrashItService().getUsuarioById(1)
        val callEndereco:Call<EnderecoAPI> = RetrofitFactory().getTrashItService().getEnderecoById(1)
        callUsuario.enqueue(object: Callback<UsuarioAPI>{
            override fun onResponse(call: Call<UsuarioAPI>, response: Response<UsuarioAPI>) {
                _usuario.update { response.body()!!}
                _usuarioPast.update { response.body()!! }
            }

            override fun onFailure(call: Call<UsuarioAPI>, t: Throwable) {
                Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                        " iniciado ou está rodando adequadamente")
                System.exit(0)
            }

        })
         callEndereco.enqueue(object : Callback<EnderecoAPI> {
             override fun onResponse(call: Call<EnderecoAPI>, response: Response<EnderecoAPI>) {
                 _endereco.update { response.body()!! }
             }

             override fun onFailure(call: Call<EnderecoAPI>, t: Throwable) {
                 Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                         " iniciado ou está rodando adequadamente")
                 System.exit(0)
             }

         })

    }


}