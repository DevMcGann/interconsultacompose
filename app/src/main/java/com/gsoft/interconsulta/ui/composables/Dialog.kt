package com.gsoft.interconsulta.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun MyDialog(viewModel: MainViewModel, whatList:String){
    var item by remember { mutableStateOf("") }

    AlertDialog(
        shape = RoundedCornerShape(15.dp) ,
        onDismissRequest = {  viewModel.showDialog.value= false },
        title = { Text("Nuevo Item") },
        text = {
            OutlinedTextField(
                value = item,
                onValueChange = { item = it },
                label = { Text("Agregar un Nuevo Item") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Black,
                    backgroundColor = Color.White
                )
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.showDialog.value = false
                    if (whatList == "studies"){
                        viewModel.addItemToStudies(item)
                    }
                    if(whatList == "laboratory"){
                        viewModel.addItemToLaboratory(item)
                    }
                }) {
                Text("Agregar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showDialog.value = false
                }) {
                Text("Cancelar")
            }
        },
    )
}

@Composable
fun MyDialogNote(viewModel: MainViewModel){
    var item by remember { mutableStateOf("") }

    AlertDialog(
        modifier = Modifier.height(350.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp) ,
        onDismissRequest = {  viewModel.showDialog.value= false },
        title = { Text("Nueva Nota") },
        text = {
            OutlinedTextField(
                modifier = Modifier.height(250.dp),
                maxLines = 8,
                value = item,
                onValueChange = { item = it },
                label = { Text("Contenido de la nota, 8 lineas máximo") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Black,
                    backgroundColor = Color.White
                )
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.showDialog.value = false
                    viewModel.addItemToNotes(item)

                }) {
                Text("Agregar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showDialog.value = false
                }) {
                Text("Cancelar")
            }
        },
    )
}


@Composable
fun simpleMessageDialog(message:String, viewModel : MainViewModel ){
    AlertDialog(
        onDismissRequest = {  viewModel.showMessageDialog.value= false },
        title = { Text("Mensaje") },
        text = {

        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.showMessageDialog.value = false
                    //viewModel.addItem(item)
                }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showMessageDialog.value = false
                }) {
                Text("Cancelar")
            }
        },
    )
}

@Composable
fun mySnackbar(message:String, viewModel: MainViewModel){
    Snackbar(action = {
        Button(onClick = {viewModel.showMessageDialog.value = false}) {
            Text("OK")
        }
    },
        modifier = Modifier.padding(1.dp)) {
        Text(message)
    }
}
