package org.codejudge.application.model
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

class BySearchResult {
    @SerializedName("formatted_address")
    var formatted_address: String? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("opening_hours")
    var opening_hours: OpeningHours? = null
    @SerializedName("place_id")
    var place_id: String? = null
    @SerializedName("plus_code")
    var plus_code: PlusCode? = null
    @SerializedName("price_level")
    var price_level: Any? = null
    @SerializedName("rating")
    var rating: Any? = null
    @SerializedName("reference")
    var reference: Any? = null
    @SerializedName("types")
    var types:  @RawValue List<String>? = null
    @SerializedName("user_ratings_total")
    var user_ratings_total: Double? = null
}