package org.codejudge.application.model

import com.google.gson.annotations.SerializedName

class PlusCode {
    @SerializedName("compound_code")
    var compound_code: String? = null
    @SerializedName("global_code")
    var global_code: String? = null
}