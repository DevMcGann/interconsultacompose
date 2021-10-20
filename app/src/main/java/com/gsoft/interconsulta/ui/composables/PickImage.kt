package com.gsoft.interconsulta.ui.laboratoryScreen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gsoft.interconsulta.ui.composables.mySnackbar
import com.gsoft.interconsulta.utils.MAIN_SCREEN_ROUTE
import com.gsoft.interconsulta.utils.STUDIES_ROUTE
import com.gsoft.interconsulta.viewModel.MainViewModel

@Composable
fun PickImage(category:String?, viewModel: MainViewModel, navController: NavController) {

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var mensaje by remember { mutableStateOf("") }

    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    fun putImageOnList(){
        if(imageUri != null){
            if (category == "studies"){
                viewModel.addUriToStudies(imageUri!!)
            }
            if (category == "laboratory"){
                viewModel.addUriToLaboratory(imageUri!!)
            }
            mensaje = "Imagen agregada"
            viewModel.showMessageDialog.value = true
            navController.navigate(STUDIES_ROUTE)
        }

    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        backgroundColor = Color.Black,
        topBar = {
            if (viewModel.showMessageDialog.value) {
                mySnackbar(message = mensaje, viewModel = viewModel)
            }},
        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(onClick = {
                    bitmap.value = null
                    imageUri = null
                } ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "delete",
                        tint = Color.Green,
                        modifier = Modifier
                            .size(128.dp)
                            .padding(4.dp)
                    )
                }
                IconButton(onClick = {putImageOnList()}) {
                    Icon(
                        Icons.Filled.Send,
                        contentDescription = "Accept",
                        tint = Color.Green,
                        modifier = Modifier
                            .size(128.dp)
                            .padding(4.dp)
                    )
                }
            }
        }
    ) {

    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Seleccionar Imagen de ${viewModel.selectedCategory.value}")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let {  btm ->
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier.size(400.dp))
            }
        }

    }
}