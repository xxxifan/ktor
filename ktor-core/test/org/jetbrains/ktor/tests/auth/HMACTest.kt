package org.jetbrains.ktor.tests.auth

import org.jetbrains.ktor.auth.*
import org.jetbrains.ktor.util.*
import org.junit.*
import kotlin.test.*

class HMACTest {
    val key = hex("03e58645849684861816065")

    @Test
    fun regularAppends() {
        HMAC(key).apply {
            append("OK")
            append(1)
            append(1L)
            append(true)

            assertEquals("6727246c796cee0468f209d52721bd77d580018d", mac())
        }
    }

    @Test
    fun appendTo() {
        assertEquals("1ca710d651141124a5432a7efb667f4fc76e37fd", listOf("A", "B", "C").joinTo(HMAC(key)).mac())
    }

    @Test
    fun nonSha1() {
        assertEquals("c114439d9d49341f6b8c51f79e8b4e181aa60a9b4f626075b1ff3560ed80c2d1",
                HMAC(key, HMAC.Algorithms.HmacSHA256).apply { append("OK") }.mac())

        assertEquals("7577990fdb40cf348c7a426b7de3d2fd",
                HMAC(key, HMAC.Algorithms.HmacMD5).apply { append("OK") }.mac())
    }
}
