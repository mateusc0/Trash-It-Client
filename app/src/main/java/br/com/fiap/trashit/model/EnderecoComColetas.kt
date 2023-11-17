package br.com.fiap.trashit.model

import androidx.room.Embedded
import androidx.room.Relation

data class EnderecoComColetas(
    @Embedded val endereco: Endereco,
    @Relation(
        parentColumn = "id_endereco",
        entityColumn = "id_endereco_dono"
    )
    val listaColetas: List<Coleta>
)
