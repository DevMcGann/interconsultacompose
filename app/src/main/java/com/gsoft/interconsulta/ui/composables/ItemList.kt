package com.gsoft.interconsulta.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun ItemList(list: MutableList<String>, whatList:String, viewModel: MainViewModel){
    LazyColumn(
        modifier = Modifier.height(380.dp)
    ){
        for (element in list){
            item{
                RowItem(item = element, whatList = whatList, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun RowItem(item:String, viewModel: MainViewModel, whatList: String){
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
        ){
            IconButton(onClick = {
               if (whatList == "studies"){
                   viewModel.removeItemFromStudies(item)
               }
                if(whatList == "laboratory"){
                    viewModel.removeItemItemFromLaboratory(item)
                }
            }
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete"
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(item)


        }
    }
}