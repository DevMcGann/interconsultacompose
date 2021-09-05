package com.gsoft.interconsulta.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsoft.interconsulta.utils.darkerBlue

@Composable
fun PatientInfoCard(name:String, dni:String){
    Card(
        shape = RoundedCornerShape(15.dp),
        backgroundColor = darkerBlue,
        modifier = Modifier
            .padding(12.dp)
            .height(200.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = "Paciente", color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = name, color = Color.White, fontSize = 30.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Dni", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = dni, color = Color.White, fontSize = 30.sp,fontWeight = FontWeight.ExtraBold)
        }
    }
}