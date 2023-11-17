package br.com.fiap.trashit.service.database.repository

import android.content.Context
import br.com.fiap.trashit.model.Coleta
import br.com.fiap.trashit.service.database.TrashItDb

class ColetaRepository(context: Context) {
    private val db = TrashItDb.getDatabase(context).coletaDao()

    fun salvar(coleta: Coleta): Long {
        return db.salvar(coleta)
    }

    fun atualizar(coleta: Coleta): Int {
        return db.atualizar(coleta)
    }

    fun excluir(coleta: Coleta): Int {
        return db.excluir(coleta)
    }

    fun buscarColetaPorId(id: Long): Coleta {
        return db.buscarColetaPorId(id)
    }

    fun listarColetas(idResidencia: Long): List<Coleta> {
        return db.listarColetas(idResidencia)
    }
 }