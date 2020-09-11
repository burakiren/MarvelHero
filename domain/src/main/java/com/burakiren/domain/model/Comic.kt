package com.burakiren.domain.model

data class Comic(val items: List<ComicItem>)

data class ComicItem(val resourceURI: String, val name: String)