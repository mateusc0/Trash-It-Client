package br.com.fiap.trashit.model

import androidx.room.Entity

@Entity( tableName = "tbl_usuario")
data class Usuario(
    val id: Long = 0,
    val nomeCompleto: String = "",
    val cpf: String = "",
    val email: String = "",
    val celular: String = "",
    val senha: String = "",
)