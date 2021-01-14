package org.codejudge.application.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

class BySearchResponse {
    @SerializedName("results")
    var results: @RawValue List<BySearchResult>? = null
}