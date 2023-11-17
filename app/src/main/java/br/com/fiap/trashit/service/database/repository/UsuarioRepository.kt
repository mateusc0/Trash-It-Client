package br.com.fiap.trashit.service.database.repository

import android.content.Context
import br.com.fiap.trashit.model.Usuario
import br.com.fiap.trashit.service.database.TrashItDb

class UsuarioRepository(context: Context) {
    private val db = TrashItDb.getDatabase(context).usuarioDao()

    fun salvar(usuario: Usuario): Long {
        return db.salvar(usuario)
    }

    fun atualizar(usuario: Usuario): Int {
        return db.atualizar(usuario)
    }

    fun excluir(usuario: Usuario): Int {
        return db.excluir(usuario)
    }

    fun buscarUsuarioPorId(id: Long): Usuario {
        return db.buscarUsuarioPorId(id)
    }
    fun buscarUsuarioPorEmailESenha(email: String, senha: String): Usuario {
        return db.buscarUsuarioPorEmailESenha(email, senha)
    }

    fun listarUsuariosEndereco(idEndereco: Long): List<Usuario> {
        return db.listarUsuariosEndereco(idEndereco)
    }
}