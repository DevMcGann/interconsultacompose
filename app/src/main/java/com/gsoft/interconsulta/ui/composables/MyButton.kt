package com.gsoft.interconsulta.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyButton(
    text: String,
    function: () -> Unit,
    background: Color? = Color.Blue,
    textColor: Color ? = Color.White,
    size: Int? = 20,
    shape: Shape? = CircleShape,
    innerPadding: Int? = 20,
    border: Int? = 2,
    borderColor: Color? = Color.Black
){
    Button(
        onClick = function,
        shape = shape!!,
        contentPadding = PaddingValues(innerPadding!!.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = background!!,
            contentColor = textColor!!
        ),
        border = BorderStroke(border!!.dp, borderColor!!)
    )
    {
        Text(text, style = TextStyle(fontSize = size!!.sp))
    }
}