package br.com.fiap.trashit.viewmodel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.Endereco
import br.com.fiap.trashit.model.Usuario
import br.com.fiap.trashit.service.database.repository.EnderecoRepository
import br.com.fiap.trashit.service.database.repository.UsuarioRepository
import br.com.fiap.trashit.view.components.trashItToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern

class ContaViewModel(val context: Context): ViewModel() {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
    private val enderecoRepository = EnderecoRepository(context)
    private val usuarioRepository = UsuarioRepository(context)

    private var _usuario = MutableStateFlow<Usuario>(usuarioRepository.buscarUsuarioPorId(1))
    val usuario: StateFlow<Usuario>
        get() = _usuario

    private var _endereco = MutableStateFlow<Endereco>(enderecoRepository.buscarEnderecoPorId(1))
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
        val usuarioBanco = usuarioRepository.buscarUsuarioPorId(1)
        _usuario.update {
            usuarioBanco.copy(senha = senha)
        }
        updateUsuario()
    }
    fun logout():Unit {
        _usuario.update { currentState ->
            currentState.copy(
                isLogged = false
            )
        }
        usuarioRepository.atualizar(_usuario.value)
    }

    fun updateUsuario():Unit {
        emailErrorCheck()
        celularErrorCheck()
        if (_usuario.value != usuarioRepository.buscarUsuarioPorId(1) && !_emailError.value
            && !_celularError.value
            ) {
                usuarioRepository.atualizar(_usuario.value)
                _usuario.update {
                    usuarioRepository.buscarUsuarioPorId(1)
                }
                trashItToast(text = "Usuário atualizado", context = context)
        } else {
            trashItToast(text = "Altere alguma informação", context= context )
        }
    }

    private fun emailErrorCheck() {
        if (Patterns.EMAIL_ADDRESS.matcher(_usuario.value.email).matches() ||
                _usuario.value.email.isEmpty()){
            _emailError.update { false }
        } else {
            _emailError.update { true }
        }
    }

    private fun celularErrorCheck() {
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


}