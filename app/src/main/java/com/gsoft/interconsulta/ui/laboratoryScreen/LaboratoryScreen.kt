package com.gsoft.interconsulta.ui.laboratoryScreen

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gsoft.interconsulta.R
import com.gsoft.interconsulta.ui.composables.*
import com.gsoft.interconsulta.utils.NEW_PATIENT_ROUTE
import com.gsoft.interconsulta.utils.darkerBlue
import com.gsoft.interconsulta.utils.materialBlue700
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun LaboratoryScreen(navController: NavController, viewModel: MainViewModel){
    var mensaje by remember { mutableStateOf("") }


   /* fun showMyDialog(){
        viewModel.showDialog.value = true
    }*/

   fun goToPickImage(){
       viewModel.selectedCategory.value = "laboratorio"
       navController.navigate("pickImage")
   }

Scaffold(
        topBar = {
            if (viewModel.showMessageDialog.value) {
                mySnackbar(message = mensaje, viewModel = viewModel)
            }},
        bottomBar = {
            BottomAppBar(backgroundColor = darkerBlue) {
                MyBottomBar(navController = navController,viewModel, viewModel.currentBackNavigationTo.value  , { goToPickImage() } )
            }
        }
    ) {
        Column(
            Modifier
                //.verticalScroll(state)
                .fillMaxHeight()
                .fillMaxWidth()
                .background(materialBlue700),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CategoryHeader(image = R.drawable.laboratorio, tittle ="LABORATORIO")

            //Dialog
            if(viewModel.showDialog.value){
                MyDialog(viewModel = viewModel, "laboratory")
            }

            //Lista
            ItemList(viewModel.laboratoryList, "laboratory", viewModel)

            Spacer(modifier = Modifier.height(130.dp))
        } //mainColumn
    }
}