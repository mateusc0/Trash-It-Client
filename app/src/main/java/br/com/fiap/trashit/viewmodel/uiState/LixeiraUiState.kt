package br.com.fiap.trashit.viewmodel.uiState

data class LixeiraUiState(
    var temPlastico: Boolean,
    var temPapel: Boolean,
    var temMetal: Boolean,
    var temVidro: Boolean,
    var temOrganico: Boolean,
    var precisaColeta: Boolean
)