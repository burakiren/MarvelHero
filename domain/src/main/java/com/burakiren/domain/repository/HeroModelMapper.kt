package com.burakiren.domain.repository

interface HeroModelMapper<E, M> {
    fun fromEntity(from: E): M
    fun toEntity(from: M): E
}