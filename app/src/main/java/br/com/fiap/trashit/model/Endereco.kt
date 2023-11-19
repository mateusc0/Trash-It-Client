package br.com.fiap.trashit.model

import androidx.room.Entity

@Entity(tableName = "tbl_endereco")
data class Endereco (
    val id: Long = 0,
    val cep: String = "",
    var numero: String = "",
    val rua: String = "",
    var complemento: String? = "",
    val bairro: String = "",
    val cidade: String = "",
    val uf: String = "",
    val lixeira: Lixeira = Lixeira()
)
