package com.burakiren.domain.model

data class MarvelResponse(val data: MarvelData)

data class MarvelData(val results: List<Hero>)