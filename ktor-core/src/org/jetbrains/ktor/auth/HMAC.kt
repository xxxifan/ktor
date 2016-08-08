package org.jetbrains.ktor.auth

import org.jetbrains.ktor.util.*
import java.nio.*
import javax.crypto.*
import javax.crypto.spec.*

class HMAC(private val mac: Mac) : Appendable {
    constructor(key: ByteArray, algorithm: String = Algorithms.HmacSHA1)
    : this(Mac.getInstance(algorithm).apply {
        init(SecretKeySpec(key, algorithm))
    })

    private val encoder by lazy { Charsets.UTF_8.newEncoder()!! }

    fun append(blob: ByteArray) {
        mac.update(blob)
    }

    fun append(text: String) {
        encoder.apply {
            reset()
            mac.update(encode(CharBuffer.wrap(text)))
        }
    }

    fun append(n: Number) {
        append(n.toString())
    }

    fun append(b: Boolean) {
        append(b.toString())
    }

    override fun append(csq: CharSequence?) = append(csq, 0, csq?.length ?: 0)

    override fun append(csq: CharSequence?, start: Int, end: Int): Appendable {
        if (csq == null) {
            append("null")
        } else {
            encoder.apply {
                reset()
                mac.update(encode(CharBuffer.wrap(csq, start, end)))
            }
        }

        return this
    }

    override fun append(c: Char): Appendable {
        append(c.toString())
        return this
    }

    fun mac() = hex(macBytes())
    fun macBytes() = mac.doFinal()!!

    object Algorithms {
        val HmacSHA1 = "HmacSHA1"
        val HmacSHA256 = "HmacSHA256"
        val HmacMD5 = "HmacMD5"
    }
}