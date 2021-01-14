package org.codejudge.application.model

import com.google.gson.annotations.SerializedName


class OpeningHours {
    @SerializedName("open_now")
    var open_now: Boolean? = null
}