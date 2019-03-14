package com.exozet.freedomplayer

internal data class Exterior(
    var id: Int? = 0,
    var imageCollection: ImageCollection? = ImageCollection(),
    var createdAt: String? = "",
    var updatedAt: String? = "",
    var meta: Meta? = Meta(),
    var thumbnailMediaUrl: String? = ""
)

internal data class ImageCollection(
    var id: Int? = 0,
    var galleryHasMedias: List<GalleryHasMedia?>? = listOf()
)

internal data class GalleryHasMedia(
    var position: Int? = 0,
    var media: Media? = Media()
)

internal data class Media(
    var id: Int? = 0,
    var publicUrls: PublicUrls? = PublicUrls()
)

internal data class PublicUrls(
    var exterior_view_2160: String? = "",
    var interior_view_2160: String? = "",
    var exterior_view_1080: String? = "",
    var interior_view_1080: String? = "",
    var exterior_view_720: String? = "",
    var interior_view_720: String? = "",
    var exterior_view_480: String? = "",
    var interior_view_480: String? = "",
    var reference: String? = ""
)

internal data class Meta(
    var key: String? = ""
)

internal data class Interior(
    var id: Int? = 0,
    var imageMedia: ImageMedia? = ImageMedia(),
    var createdAt: String? = "",
    var updatedAt: String? = "",
    var meta: Meta? = Meta(),
    var thumbnailMediaUrl: String? = ""
)

internal data class ImageMedia(
    var id: Int? = 0,
    var publicUrls: PublicUrls? = PublicUrls()
)