package br.com.fiap.trashit.service.database.repository

import android.content.Context
import br.com.fiap.trashit.model.Endereco
import br.com.fiap.trashit.service.database.TrashItDb

class EnderecoRepository(context: Context) {
    private val db = TrashItDb.getDatabase(context).enderecoDao()

    fun salvar(endereco: Endereco): Long {
        return db.salvar(endereco)
    }

    fun atualizar(endereco: Endereco): Int {
        return db.atualizar(endereco)
    }

    fun excluir(endereco: Endereco): Int {
        return db.excluir(endereco)
    }

    fun buscarEnderecoPorId(id: Long): Endereco {
        return db.buscarEnderecoPorId(id)
    }

    fun listarEnderecos(): List<Endereco> {
        return db.listarEnderecos()
    }
}