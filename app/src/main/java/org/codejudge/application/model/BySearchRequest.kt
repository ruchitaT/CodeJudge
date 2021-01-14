package org.codejudge.application.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

class BySearchRequest {
    @SerializedName("location")
    var location: String?= null
    @SerializedName("radius")
    var radius: String?= null
    @SerializedName("type")
    var type: String?= null
    @SerializedName("key")
    var key: String?= null
}

