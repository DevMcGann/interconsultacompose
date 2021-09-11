package com.gsoft.interconsulta.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsoft.interconsulta.utils.yellowish
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun NotasList(list: MutableList<String>, viewModel: MainViewModel){
    LazyColumn(
        modifier = Modifier.height(400.dp)
    ){
        for (element in list){
            item{
                NoteItem(note = element,  viewModel = viewModel)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun NoteItem(note: String, viewModel: MainViewModel) {
    // Smoothly scroll 100px on first composition
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Card(
        shape = RoundedCornerShape(15.dp) ,
        elevation = 4.dp,
        backgroundColor = yellowish,
        modifier = Modifier
        // .verticalScroll(state)
        .height(330.dp)
        .fillMaxWidth()
            .padding(2.dp)
            .verticalScroll(state),) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)
                .scrollable(state = state, orientation = Orientation.Vertical)
        ) {

            Text(note,
                textAlign = TextAlign.Center ,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            modifier = Modifier.height(250.dp),
                maxLines = 8
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement= Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { viewModel.removeItemFromNotes(note) }) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "delete",
                        tint = Color.White,
                        modifier = Modifier.size(128.dp)
                    )
                }
            }
        }
    }
}
