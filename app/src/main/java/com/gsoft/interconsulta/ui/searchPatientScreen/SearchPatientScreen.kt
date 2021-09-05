package com.gsoft.interconsulta.ui.searchPatientScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gsoft.interconsulta.R
import com.gsoft.interconsulta.data.models.Patient
import com.gsoft.interconsulta.ui.composables.Category
import com.gsoft.interconsulta.ui.composables.PatientInfoCard
import com.gsoft.interconsulta.ui.composables.mySnackbar
import com.gsoft.interconsulta.utils.*
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun SearchPatientScreen(navController: NavController, viewModel: MainViewModel){
    var mensaje by remember { mutableStateOf("") }
    var searchDNI by remember { mutableStateOf("")}

    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    fun fetchPatient(searchDNI: String) {
        viewModel.clearViewModel()
        if(searchDNI.isNullOrEmpty()){
            mensaje = "Debes poner un DNI válido antes de buscar"
            viewModel.showMessageDialog.value = true
        }else{
            viewModel.fetchPatient(searchDNI)
        }
    }

    fun editPatient(){
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
            viewModel.updatePaciente(paciente)
            mensaje = "Paciente actualizado"
            viewModel.showMessageDialog.value = true
            viewModel.clearViewModel()
            navController.navigate(
                MAIN_SCREEN_ROUTE
            ) { popUpTo(MAIN_SCREEN_ROUTE) { inclusive = true } }
        }catch(e:Exception){
            mensaje = "No se pudo editar el Paciente"
            viewModel.showMessageDialog.value = true
        }
    }

    fun deletePatient(){
        try {
            viewModel.deletePaciente(viewModel.dni.value)
            mensaje = "Paciente Eliminado"
            viewModel.showMessageDialog.value = true
            viewModel.clearViewModel()
        }catch(e:Exception){
            mensaje = "No se pudo editar el Paciente"
            viewModel.showMessageDialog.value = true
        }
    }


  Scaffold(
      backgroundColor = materialBlue700,
      topBar = {
          if (viewModel.showMessageDialog.value) {
              mySnackbar(message = mensaje, viewModel = viewModel)
          }
               },
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
                  IconButton(onClick = { editPatient() }) {
                      Icon(
                          Icons.Filled.Edit,
                          contentDescription = "Edit",
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
                  IconButton(onClick = { deletePatient() }) {
                      Icon(
                          Icons.Filled.Delete,
                          contentDescription = "delete",
                          tint = Color.Red,
                          modifier = Modifier
                              .size(128.dp)
                              .padding(4.dp)
                      )
                  }
              }
          }
      }
  ) {
      Column(
          Modifier
              .verticalScroll(state)
              .fillMaxHeight()
              .fillMaxWidth()
              .background(materialBlue700)
              .padding(12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(percent = 20),
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.None, false, KeyboardType.Number) ,
                value = searchDNI,
                onValueChange = { searchDNI = it },
                label = { Text("DNI del Paciente",color= Color.White, fontSize = 18.sp) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Black,
                    backgroundColor = textFieldBackground,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))

            Card(
                backgroundColor = Color.Black,
                shape = CircleShape
            ) {
                IconButton(onClick = { fetchPatient(searchDNI) }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "search",
                        tint = Color.Green,
                        modifier = Modifier
                            .size(128.dp)
                            .padding(4.dp)
                    )
                }
            } //card del textInput
        }//row

          //circular progress indicator
          if(viewModel.isLoading.value){
              Spacer(modifier = Modifier.height(18.dp))
              CircularProgressIndicator(
                  modifier = Modifier
                      .height(50.dp)
                      .width(50.dp),
                  color = Color.Green
              )
          }
          //paciente encontrado
          if(viewModel.showPatient.value){
              searchDNI = ""
              PatientInfoCard(name = viewModel.nombre.value, dni =viewModel.dni.value )
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
}