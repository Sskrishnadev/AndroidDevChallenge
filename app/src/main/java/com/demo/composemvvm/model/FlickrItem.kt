package com.demo.composemvvm.model


import com.google.gson.annotations.SerializedName

data class FlickrItem(
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("date_taken") val date_taken: String? = null,
    @SerializedName("published") val published: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("tags") val tags: String? = null,
    @SerializedName("media") val media: Media? = null,
) {
    override fun toString(): String {
        return "Title : $title , Description : $description , Author : $author"
    }

    data class Media(
        @SerializedName("m") val m: String?,
    )
}