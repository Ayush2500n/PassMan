package com.example.passafe_password_manager.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Observer
import com.example.passafe_password_manager.MainActivity
import com.example.passafe_password_manager.R
import com.example.passafe_password_manager.constants.secretKey
import com.example.passafe_password_manager.convertToStars
import com.example.passafe_password_manager.data.local.passData
import com.example.passafe_password_manager.decrypt
import com.example.passafe_password_manager.viewmodel.passViewModel


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun home_screen(allPass: List<passData>, viewModel: passViewModel, mainActivity: MainActivity) {
    var showAddNew by remember {
        mutableStateOf(false)
    }
    var list by remember {
        mutableStateOf(allPass)
    }
    viewModel.getAllData().observe(mainActivity, Observer {
        list = it
    })
    var count = 0
    println("List as of home_screen is $allPass")
    Column(modifier = Modifier.fillMaxSize()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp), shape = RectangleShape, colors = CardDefaults.cardColors(Color.White), border = BorderStroke(width = 0.4.dp, color = Color.LightGray)
        ) {
            Text(text = "PassMan", modifier = Modifier
                .fillMaxHeight()
                .padding(top = 30.dp, start = 12.dp), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
        pass_list(list, count, viewModel, mainActivity)
        if (showAddNew) {
                Dialog(onDismissRequest = { showAddNew = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
                    add_New(viewModel = viewModel)
                }
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {
            FloatingActionButton(onClick = {showAddNew = true }, modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 28.dp, end = 20.dp)
                .size(80.dp), containerColor = colorResource(id = R.color.FAB_blue)) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.White)
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun pass_list(
    allPass: List<passData>,
    count: Int,
    viewModel: passViewModel,
    mainActivity: MainActivity
) {
    var count = count
    LazyColumn(content = {
        val itemcount = allPass.size
        items(itemcount){
            singleItem(allPass, count, viewModel, mainActivity)
            count++
        }
    })
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun singleItem(
    allPass: List<passData>,
    itemcount: Int,
    viewModel: passViewModel,
    mainActivity: MainActivity
) {
    var show_pass by remember {
        mutableStateOf(false)
    }
    val decrypt_pass = decrypt(allPass[itemcount].password, secretKey)
    val starPass = convertToStars(decrypt_pass)
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .height(60.dp), shape = RoundedCornerShape(24.dp)) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row() {
                Text(text = allPass[itemcount].accountName, modifier = Modifier.padding(start = 12.dp, end = 12.dp), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = starPass)
            }
            if (show_pass){
                Dialog(onDismissRequest = { show_pass = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
                    pass_show(allPass, itemcount, viewModel, mainActivity)
                }
            }
            IconButton(onClick = { show_pass = true }) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
}
