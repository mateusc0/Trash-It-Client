package br.com.fiap.trashit.model

import androidx.room.ColumnInfo
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

data class Lixeira(
    @ColumnInfo(name = "status_coleta") var precisaColeta: Boolean = false,
    @ColumnInfo(name = "coleta_plastico") var temPlastico: Boolean = false,
    @ColumnInfo(name = "coleta_papel") var temPapel: Boolean = false,
    @ColumnInfo(name = "coleta_vidro") var temVidro: Boolean = false,
    @ColumnInfo(name = "coleta_metal") var temMetal: Boolean = false,
    @ColumnInfo(name = "coleta_organico") var temOrganico: Boolean = false,
    @ColumnInfo(name = "data_notificacao") var dtNotificacao: Date = Timestamp.from(Instant.now())
)