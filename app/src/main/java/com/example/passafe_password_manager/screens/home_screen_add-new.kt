package com.example.passafe_password_manager.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passafe_password_manager.constants.secretKey
import com.example.passafe_password_manager.data.local.passData
import com.example.passafe_password_manager.encrypt
import com.example.passafe_password_manager.viewmodel.passViewModel
import java.lang.StringBuilder
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun add_New(viewModel: passViewModel) {
    var account_name by remember {
        mutableStateOf("Account Name")
    }
    var username by remember {
        mutableStateOf("Username/Email")
    }
    var password by remember {
        mutableStateOf("Password")
    }
    var suggestion by remember {
        mutableStateOf(false)
    }
    var error by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        , verticalArrangement = Arrangement.Bottom) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp), colors = CardDefaults.cardColors(
            Color(0xFFf9f9f9)
        )){
            TextField(value = account_name, onValueChange = {account_name = it},  singleLine = true, colors = TextFieldDefaults.textFieldColors(containerColor = Color.White, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color.Black, unfocusedTextColor = if(account_name != "Account Name") Color.Black else Color.LightGray), modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        suggestion = false
                        if (account_name.isNotEmpty()) error = false
                        if (account_name == "Account Name") account_name = ""
                    } else {
                        if (account_name == "") account_name = "Account Name"
                    }
                }
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 40.dp, bottom = 16.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)))
            if (error){
                Row(modifier = Modifier.padding(start = 40.dp, bottom = 16.dp)) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color(0xFFC70039))
                    Text(text = "Account name can't be empty", fontSize = 14.sp, color = Color(0xFFC70039))
                }
            }
            TextField(value = username, onValueChange = {username = it}, singleLine = true,colors = TextFieldDefaults.textFieldColors(containerColor = Color.White, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color.Black, unfocusedTextColor = if(username != "Username/Email") Color.Black else Color.LightGray), modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        suggestion = false
                        if (username == "Username/Email") username = ""
                    } else {
                        if (username == "") username = "Username/Email"
                    }
                }
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 16.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)))
            if (error){
                Row(modifier = Modifier.padding(start = 40.dp, bottom = 16.dp)) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color(0xFFC70039))
                    Text(text = "Username can't be empty", fontSize = 14.sp, color = Color(0xFFC70039))
                }
            }
            TextField(value = password, onValueChange = {password = it}, singleLine = true, colors = TextFieldDefaults.textFieldColors(containerColor = Color.White, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color.Black, unfocusedTextColor = if(password != "Password") Color.Black else Color.LightGray), modifier = Modifier
                .onFocusChanged { pass ->
                    if (pass.isFocused) {
                        suggestion = true
                        if (password == "Password") password = ""
                    } else {
                        if (password == "") password = "Password"
                    }
                }
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 16.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)))
            if (error){
                Row(modifier = Modifier.padding(start = 40.dp, bottom = 16.dp)) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color(0xFFC70039))
                    Text(text = "Password can't be empty", fontSize = 14.sp, color = Color(0xFFC70039))
                }
            }
            if (suggestion){
                    if (password.isEmpty() || password == "" || password == "Password")
                        suggestion = true else suggestion = false
                Text(text = "Suggested password: " + suggestStrongPass(), modifier = Modifier.padding(start = 40.dp, bottom = 12.dp), fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFFFFAA1D))
            }

            val encrypted_pass = encrypt(password, secretKey)
            Button(onClick = {
                error =
                    account_name.isEmpty() || account_name == "Account Name" || account_name == ""
                error =
                    username.isEmpty()|| username == "Username/Email" || username == ""
                error =
                    password.isEmpty()|| password == "Password" || password == ""
                if (!error){
                viewModel.insertData(passData(accountName = account_name, userName = username, password = encrypted_pass))}
                }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp), colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "Add New Account", modifier = Modifier
                    .align(Alignment.CenterVertically))
            }
        }
    }

}

fun suggestStrongPass(): String{
    val lowerCase = "abcdefghijklmnopqrstuvwxyz"
    val upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "0123456789"
    val specialChars = "!@#$%^&*()_+{}[];:<>,."

    val allChars = lowerCase + upperCase + numbers + specialChars
    val password = StringBuilder()
    password.append(lowerCase.random())
    password.append(upperCase.random())
    password.append(numbers.random())
    password.append(specialChars.random())

    // Add remaining characters randomly
    repeat(14 - 4) {
        password.append(allChars.random())
    }

    // Shuffle the password characters
    val shuffledPassword = password.toString().toList().shuffled().joinToString("")

    return shuffledPassword
}
