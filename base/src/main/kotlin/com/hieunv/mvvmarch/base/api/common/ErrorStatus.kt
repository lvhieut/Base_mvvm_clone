package com.hieunv.mvvmarch.base.api.common

enum class ErrorStatus(val code: Int, val message: String) {

    COMMON(500, "Common Error"),
    TIME_OUT(501, "Timeout Error"),
    UNKNOWN_HOST(502, "Unknown host Error")

}
