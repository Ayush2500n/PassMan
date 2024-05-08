package com.example.passafe_password_manager.screens

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passafe_password_manager.BiometricPromptManager
import com.example.passafe_password_manager.BiometricPromptManager.*
import com.example.passafe_password_manager.MainActivity
import com.example.passafe_password_manager.R
import com.example.passafe_password_manager.constants.secretKey
import com.example.passafe_password_manager.convertToStars
import com.example.passafe_password_manager.data.local.passData
import com.example.passafe_password_manager.decrypt
import com.example.passafe_password_manager.viewmodel.passViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun pass_show(
    allPass: List<passData>,
    itemcount: Int,
    viewModel: passViewModel,
    mainActivity: MainActivity
) {
    var secondClick by remember {
        mutableStateOf(false)
    }
    var isAuthenticated by remember {
        mutableStateOf(false)
    }
    var editState by remember {
        mutableStateOf(false)
    }
    var newAccount by remember {
        mutableStateOf(allPass[itemcount].accountName)
    }
    var newUsername by remember {
        mutableStateOf(allPass[itemcount].userName)
    }
    var newPassword by remember {
        mutableStateOf(allPass[itemcount].password)
    }
    var show_pass by remember {
        mutableStateOf(false)
    }
    val promptManager by lazy {
        BiometricPromptManager(mainActivity)
    }
    val biometricResult by promptManager.promptResults.collectAsState(initial = null)
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        
    }
    LaunchedEffect(biometricResult) {
        if (biometricResult is BiometricResult.AuthenticationNotSet){
            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            }
            launcher.launch(enrollIntent)
        }
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp), colors = CardDefaults.cardColors(
            Color(0xFFf9f9f9)
        )) {
            Text(text = "Account Details", modifier = Modifier.padding(bottom = 20.dp, top = 40.dp, start = 20.dp), color = colorResource(
                id = R.color.FAB_blue
            ), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Account Type", modifier = Modifier.padding(bottom = 5.dp, top = 20.dp, start = 20.dp), color = Color.LightGray)
            if (editState){
                TextField(value = newAccount, onValueChange = {newAccount = it},  singleLine = true, colors = TextFieldDefaults.textFieldColors(containerColor = Color.White, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color.Black, unfocusedTextColor = Color.Gray), modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)))
            }else {
                Text(
                    text = allPass[itemcount].accountName,
                    modifier = Modifier.padding(start = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(text = "Username/Email", modifier = Modifier.padding(top = 20.dp, start = 20.dp, bottom = 5.dp), color = Color.LightGray)
            if (editState){
                TextField(value = newUsername, onValueChange = {newUsername = it},  singleLine = true, colors = TextFieldDefaults.textFieldColors(containerColor = Color.White, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color.Black, unfocusedTextColor = Color.Gray), modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)))
            }else {
                Text(
                    text = allPass[itemcount].userName,
                    modifier = Modifier.padding(top = 5.dp, start = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(text = "Password", modifier = Modifier.padding(top = 20.dp, start = 20.dp, bottom = 5.dp), color = Color.LightGray)
            if (editState){
                TextField(value = convertToStars(decrypt(newPassword, secretKey)), onValueChange = {newPassword = it},  singleLine = true, colors = TextFieldDefaults.textFieldColors(containerColor = Color.White, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, focusedTextColor = Color.Black, unfocusedTextColor = Color.Gray), modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)))
            }else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = if (show_pass)
                            decrypt(allPass[itemcount].password, secretKey) else convertToStars(
                            decrypt(allPass[itemcount].password, secretKey)
                        ),
                        modifier = Modifier.padding(bottom = 40.dp, top = 5.dp, start = 20.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )

                    IconButton(onClick = {
                        if (!isAuthenticated){
                            promptManager.showBiometricPrompt("Authentication required", "Confirm it's you!")
                            isAuthenticated = true
                            show_pass = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = if (show_pass) R.drawable.eye_alt_svgrepo_com else R.drawable.eye_slash_alt_svgrepo_com),
                            contentDescription = null, modifier = Modifier.size(24.dp)
                        )
                    }

                    biometricResult?.let {
                        val message = when (it) {
                            is BiometricResult.AuthenticationError -> "Authentication error"
                            BiometricResult.AuthenticationFailed -> "Authentication Failed"
                            BiometricResult.AuthenticationNotSet -> "Authentication not set"
                            BiometricResult.AuthenticationSuccess -> "Authentication Success"
                            BiometricResult.FeatureUnavailable -> "Authentication unavailable"
                            BiometricResult.HardwareUnavailable -> "Hardware error"
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                if (editState) {
                    Button(onClick = {
                        GlobalScope.launch {
                            viewModel.editData(
                                passData(
                                    allPass[itemcount].id,
                                    newAccount,
                                    newUsername,
                                    newPassword
                                )
                            )
                        }
                    }, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.padding(top = 20.dp)) {
                        Text(text = "Edit", modifier = Modifier.padding(start = 30.dp, end = 30.dp))
                    }
                }
                if (!editState) {
                    Button(onClick = { editState = true }, colors = ButtonDefaults.buttonColors(Color.Black)) {
                        Text(text = "Edit", modifier = Modifier.padding(start = 30.dp, end = 30.dp))
                    }
                    Button(onClick = {
                        GlobalScope.launch {
                            viewModel.deleteData(allPass[itemcount])
                        }
                        return@Button
                    }, colors = ButtonDefaults.buttonColors(Color.Red)) {
                        Text(
                            text = "Delete",
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                        )
                    }
                }
            }
        }
    }
}