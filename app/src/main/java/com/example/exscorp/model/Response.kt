package com.example.exscorp.model

class Response<T>(t: T?, mStatus: Status,message:String?) {
    val status:Status = mStatus
    val errMessage:String? = message
    val data: T? = t
}