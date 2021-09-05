package com.gsoft.interconsulta.ui.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gsoft.interconsulta.R
import com.gsoft.interconsulta.ui.composables.MyButton
import com.gsoft.interconsulta.utils.NEW_PATIENT_ROUTE
import com.gsoft.interconsulta.utils.SEARCH_PATIENT_ROUTE
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel){
    
    Scaffold(
        topBar = {}
    ) {
        Box(

        ){
            Image(
                painter = painterResource(R.drawable.portada),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.4F,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {

                MyButton(text = "Nuevo Paciente", function = {
                    viewModel.currentBackNavigationTo.value = NEW_PATIENT_ROUTE
                    navController.navigate(
                    NEW_PATIENT_ROUTE){popUpTo(NEW_PATIENT_ROUTE) { inclusive = true }} }
                )
                Spacer(modifier = Modifier.height(30.dp))
                MyButton(text = "Buscar Paciente", function = {
                    viewModel.currentBackNavigationTo.value = SEARCH_PATIENT_ROUTE
                    navController.navigate(
                    SEARCH_PATIENT_ROUTE){popUpTo(SEARCH_PATIENT_ROUTE) { inclusive = true }} } )

            }
        }
    }

}