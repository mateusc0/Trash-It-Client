package br.com.fiap.trashit.viewmodel

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.Endereco
import br.com.fiap.trashit.model.Usuario
import br.com.fiap.trashit.service.trashItService.RetrofitFactory
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
    private var _usuario = MutableStateFlow<Usuario>(
        Usuario()
    )

    val usuario: StateFlow<Usuario>
        get() = _usuario

    private var _usuarioPast = MutableStateFlow<Usuario>(
        Usuario()
    )

    val usuarioPast: StateFlow<Usuario>
        get() = _usuarioPast

    private var _endereco = MutableStateFlow<Endereco>(
        Endereco()
    )
    val endereco: StateFlow<Endereco>
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
                ).enqueue(object : Callback<Usuario> {
                    override fun onResponse(
                        call: Call<Usuario>,
                        response: Response<Usuario>,
                    ) {
                        _usuario.update { response.body()!! }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                                " iniciado ou está rodando adequadamente")
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
        val callUsuario: Call<Usuario> = RetrofitFactory().getTrashItService().getUsuarioById(1)
        val callEndereco:Call<Endereco> = RetrofitFactory().getTrashItService().getEnderecoById(1)
        callUsuario.enqueue(object: Callback<Usuario>{
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                _usuario.update { response.body()!!}
                _usuarioPast.update { response.body()!! }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                        " iniciado ou está rodando adequadamente")
            }

        })
         callEndereco.enqueue(object : Callback<Endereco> {
             override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                 _endereco.update { response.body()!! }
             }

             override fun onFailure(call: Call<Endereco>, t: Throwable) {
                 Log.d("TRASHIT - ERROR", "Menssagem: Verifique se o serviço foi" +
                         " iniciado ou está rodando adequadamente")
             }

         })

    }


}