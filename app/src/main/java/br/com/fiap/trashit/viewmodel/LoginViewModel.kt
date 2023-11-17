package br.com.fiap.trashit.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.fiap.trashit.model.Endereco
import br.com.fiap.trashit.model.Lixeira
import br.com.fiap.trashit.model.Usuario
import br.com.fiap.trashit.service.database.repository.EnderecoRepository
import br.com.fiap.trashit.service.database.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(context: Context): ViewModel() {
    private val enderecoRepository = EnderecoRepository(context)
    private val usuarioRepository = UsuarioRepository(context)

    private val usuarioIntent = if (usuarioRepository.listarUsuariosEndereco(1).isEmpty()) {
        Usuario(
            id = 1,
            idEndereco = 1,
            nomeCompleto = "Seu Nome",
            cpf = "00000000000",
            email = "seuEmail@email.com",
            celular = "11900000000",
            senha = "senha",
            isLogged = false
        )
    } else {
        usuarioRepository.buscarUsuarioPorId(1)
    }

    private val _usuario = MutableStateFlow<Usuario>(usuarioIntent)
    val usuario: StateFlow<Usuario>
        get() = _usuario

    fun login(){
        if (enderecoRepository.listarEnderecos().isEmpty()) {
            val endereco = Endereco(
                id = 1, cep = "09211111", numero = "111", rua = "Rua Exemplo", complemento = "",
                bairro = "Bairro Exemplo", cidade = "Cidade Exemplo", uf = "Estado Exemplo", Lixeira()
            )
            enderecoRepository.salvar(endereco)
            usuarioRepository.salvar(_usuario.value)
        }
        usuarioRepository.atualizar(_usuario.value.copy(isLogged = true))
    }
}