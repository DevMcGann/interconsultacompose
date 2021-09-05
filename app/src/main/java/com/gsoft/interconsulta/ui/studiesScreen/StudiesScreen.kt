package com.gsoft.interconsulta.ui.studiesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gsoft.interconsulta.ui.composables.*
import com.gsoft.interconsulta.utils.*
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun StudiesScreen(navController: NavController, viewModel: MainViewModel){

    var mensaje by remember { mutableStateOf("") }

    fun showMyDialog(){
        viewModel.showDialog.value = true
    }

    Scaffold(
        topBar = {
            if (viewModel.showMessageDialog.value) {
                mySnackbar(message = mensaje, viewModel = viewModel)
            }},
        bottomBar = {
            BottomAppBar(backgroundColor = darkerBlue) {
                MyBottomBar(
                    navController = navController,viewModel, viewModel.currentBackNavigationTo.value ,
                    { showMyDialog() })
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

            CategoryHeader(image = com.gsoft.interconsulta.R.drawable.estudios, tittle ="ESTUDIOS")

            //Dialog
            if(viewModel.showDialog.value){
                MyDialog(viewModel = viewModel, "studies")
            }

            //Lista
            ItemList(viewModel.studiesList, "studies", viewModel)

            Spacer(modifier = Modifier.height(130.dp))

        } //mainColumn
    }//scaffold
} //composable