package com.gsoft.interconsulta.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsoft.interconsulta.utils.darkerBlue
import com.gsoft.interconsulta.utils.textFieldBackground
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun AddPatient(viewModel: MainViewModel){
    Card(
        shape = RoundedCornerShape(15.dp),
        backgroundColor = darkerBlue,
        modifier = Modifier
            .padding(12.dp)
            .height(320.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = "Datos del paciente", color= Color.White, fontSize = 25.sp)
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                shape = RoundedCornerShape(percent = 20),
                value = viewModel.nombre.value,
                onValueChange = { viewModel.nombre.value = it },
                label = { Text("Nombre del Paciente",color= Color.White, fontSize = 18.sp) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Black,
                    backgroundColor = textFieldBackground,
                    textColor = Color.White,
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                shape = RoundedCornerShape(percent = 20),
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.None, false, KeyboardType.Number) ,
                value = viewModel.dni.value,
                onValueChange = { viewModel.dni.value = it },
                label = { Text("DNI del Paciente",color= Color.White, fontSize = 18.sp) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Black,
                    backgroundColor = textFieldBackground,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                shape = RoundedCornerShape(percent = 20),
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.None, false,) ,
                value = viewModel.surgery.value,
                onValueChange = { viewModel.surgery.value = it },
                label = { Text("Cirugia a realizar",color= Color.White, fontSize = 18.sp) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Black,
                    backgroundColor = textFieldBackground,
                    textColor = Color.White
                )
            )
        }
    }
}