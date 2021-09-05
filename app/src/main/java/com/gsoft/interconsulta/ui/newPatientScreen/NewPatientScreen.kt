package com.gsoft.interconsulta.ui.newPatientScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gsoft.interconsulta.R
import com.gsoft.interconsulta.data.models.Patient
import com.gsoft.interconsulta.ui.composables.AddPatient
import com.gsoft.interconsulta.ui.composables.Category
import com.gsoft.interconsulta.ui.composables.MyBottomBar
import com.gsoft.interconsulta.ui.composables.mySnackbar
import com.gsoft.interconsulta.utils.*
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun NewPatientScreen(navController: NavController,viewModel : MainViewModel){


    var mensaje by remember { mutableStateOf("") }
    // Smoothly scroll 100px on first composition
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    fun addNewPatient(){
        if(viewModel.nombre.value.isNullOrEmpty() || viewModel.dni.value.isNullOrEmpty()){
            mensaje = "Debes completar nombre y Dni para continuar.."
            viewModel.showMessageDialog.value = true
        }
        var labList : MutableList<String> = mutableListOf()

        for (item in viewModel.laboratoryList){
            labList.add(item)
        }

        var studyList : MutableList<String> = mutableListOf()

        for (item in viewModel.studiesList){
            studyList.add(item)
        }

        var noteList : MutableList<String> = mutableListOf()

        for (item in viewModel.notesList){
            noteList.add(item)
        }

        val paciente = Patient(
            dni = viewModel.dni.value,
            nombre = viewModel.nombre.value,
            laboratory = labList,
            studies =studyList ,
            notes =noteList
        )

        try {
            viewModel.addPatient(paciente)
            mensaje = "Paciente Agregado"
            viewModel.showMessageDialog.value = true
            viewModel.clearViewModel()
        }catch (e:Exception){
            mensaje = "Hubo un error al Agregar Paciente"
            viewModel.showMessageDialog.value = true
        }
    }



    Scaffold (
        topBar = {
            if (viewModel.showMessageDialog.value) {
            mySnackbar(message = mensaje, viewModel = viewModel)
        }},

        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .width(350.dp)
                    .padding(horizontal = 15.dp)
            ) {
                Card(
                    backgroundColor = Color.Black,
                    shape = CircleShape
                ) {
                    IconButton(onClick = { navController.navigate(MAIN_SCREEN_ROUTE){
                        viewModel.showMessageDialog.value = false
                        popUpTo(MAIN_SCREEN_ROUTE) { inclusive = true }
                    } }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = Color.Green,
                            modifier = Modifier
                                .size(128.dp)
                                .padding(4.dp)
                        )
                    }
                }
                Card(
                    backgroundColor = Color.Black,
                    shape = CircleShape
                ) {
                    IconButton(onClick = { addNewPatient() }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "add",
                            tint = Color.Green,
                            modifier = Modifier
                                .size(128.dp)
                                .padding(4.dp)
                        )
                    }
                }

            }
        }
            ){
        Column(
            Modifier
                .verticalScroll(state)
                .fillMaxHeight()
                .fillMaxWidth()
                .background(materialBlue700)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           //////////////////////// BODY ///////////////////////////////////
            AddPatient(viewModel = viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            Category(navController = navController, tittle = "ESTUDIOS", drawable = R.drawable.estudios , route = STUDIES_ROUTE )
            Spacer(modifier = Modifier.height(20.dp))
            Category(navController = navController, tittle ="LABORATORIO" , drawable = R.drawable.laboratorio, route =  LABORATORY_ROUTE)
            Spacer(modifier = Modifier.height(20.dp))
            Category(navController = navController, tittle = "NOTAS", drawable = R.drawable.notas , route = NOTES_ROUTE)
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}