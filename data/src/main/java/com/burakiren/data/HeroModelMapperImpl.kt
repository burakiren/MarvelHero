package com.burakiren.data

import com.burakiren.domain.model.Hero
import com.burakiren.domain.model.ThumbnailResponse
import com.burakiren.domain.repository.HeroModelMapper

class HeroModelMapperImpl : HeroModelMapper<HeroEntity, Hero> {
    override fun fromEntity(from: HeroEntity) =
        Hero(from.id, from.name, from.description, ThumbnailResponse(from.path, from.extension), null)

    override fun toEntity(from: Hero) =
        HeroEntity(
            from.id,
            from.name,
            from.description,
            from.thumbnail.path,
            from.thumbnail.extension,
            System.currentTimeMillis()
        )
}