package br.com.fiap.trashit.model

import androidx.room.Entity
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

@Entity(tableName = "tbl_coleta")
data class Coleta (
    val id: Long = 0,
    val dtColeta: Date = Timestamp.from(Instant.now()),
    val lixeira: Lixeira = Lixeira()
)