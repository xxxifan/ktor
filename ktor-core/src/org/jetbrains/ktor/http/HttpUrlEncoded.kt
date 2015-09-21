package org.jetbrains.ktor.http

import org.jetbrains.ktor.application.*
import org.jetbrains.ktor.util.*
import java.net.*

public fun ApplicationRequest.parseUrlEncodedParameters(): ValuesMap {
    val parameters = content.get<String>().split("&").map { it.substringBefore("=") to it.substringAfter("=", "") }
    val encoding = contentCharset?.name() ?: parameters.firstOrNull { it.first == "_charset_" }?.second ?: "UTF-8"

    return parameters.fold(ValuesMap.Builder()) { builder, pair ->
        builder.append(URLDecoder.decode(pair.first, encoding), URLDecoder.decode(pair.second, encoding))
        builder
    }.build()
}
