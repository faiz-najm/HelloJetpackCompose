package com.bangkit.hellojetpackcompose

import com.google.gson.Gson
import java.net.URLEncoder

fun String.urlEncode(): String = URLEncoder.encode(this, "utf-8")

fun <A> String.fromJson(type: Class<A>): A = Gson().fromJson(this, type)

fun <A> A.toJson(): String? = Gson().toJson(this)