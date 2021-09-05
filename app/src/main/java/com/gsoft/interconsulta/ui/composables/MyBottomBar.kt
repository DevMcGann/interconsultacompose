package com.gsoft.interconsulta.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gsoft.interconsulta.viewModel.MainViewModel
import kotlin.reflect.KSuspendFunction0

@Composable
fun MyBottomBar(
    navController: NavController,
    viewModel: MainViewModel,
    route:String,
    function: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                navController.navigate(route){
                    viewModel.showMessageDialog.value = false
                    popUpTo(route) { inclusive = true }
                }
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = Color.White,
                    modifier = Modifier.size(128.dp)
                )
            }

            IconButton(onClick = function ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "add",
                    tint = Color.White,
                    modifier = Modifier.size(128.dp)
                )
            }
        }
    }
}