package com.gsoft.interconsulta.ui.notesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gsoft.interconsulta.ui.composables.*
import com.gsoft.interconsulta.utils.NEW_PATIENT_ROUTE
import com.gsoft.interconsulta.utils.darkerBlue
import com.gsoft.interconsulta.utils.materialBlue700
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun NotesScreen(navController: NavController, viewModel: MainViewModel){
    var mensaje by remember { mutableStateOf("") }

    // Smoothly scroll 100px on first composition
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

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
                MyBottomBar(navController = navController,viewModel,viewModel.currentBackNavigationTo.value  , {showMyDialog()} )
            }
        }
    ) {
        Column(
            Modifier
                .verticalScroll(state)
                .fillMaxHeight()
                .fillMaxWidth()
                .background(materialBlue700),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CategoryHeader(image = com.gsoft.interconsulta.R.drawable.notas, tittle ="NOTAS")

            //Dialog
            if(viewModel.showDialog.value){
                MyDialogNote(viewModel = viewModel)
            }


            NotasList(list = viewModel.notesList, viewModel = viewModel)

            Spacer(modifier = Modifier.height(130.dp))

        } //mainColumn
    }
}