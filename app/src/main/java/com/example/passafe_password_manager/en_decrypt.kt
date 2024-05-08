package com.example.passafe_password_manager

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object constants{
    val secretKey = "We1c0me159@Ayush_Devdaas"
}

@OptIn(ExperimentalEncodingApi::class)
fun encrypt(password: String, secretKey: String): String {
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val key = SecretKeySpec(secretKey.toByteArray(), "AES")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encryptedBytes = cipher.doFinal(password.toByteArray())
    return Base64.encode(encryptedBytes)
}


fun convertToStars(encryptedPass: String): String {
    val length = encryptedPass.length
    return "*".repeat(length)
}

@OptIn(ExperimentalEncodingApi::class)
fun decrypt(encryptedPass: String, secretKey: String): String{
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val key = SecretKeySpec(secretKey.toByteArray(), "AES")
    cipher.init(Cipher.DECRYPT_MODE, key)
    val decryptedBytes = cipher.doFinal(Base64.decode(encryptedPass))
    return String(decryptedBytes)
}