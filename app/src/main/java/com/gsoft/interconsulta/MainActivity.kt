package com.gsoft.interconsulta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsoft.interconsulta.ui.laboratoryScreen.LaboratoryScreen
import com.gsoft.interconsulta.ui.mainScreen.MainScreen
import com.gsoft.interconsulta.ui.newPatientScreen.NewPatientScreen
import com.gsoft.interconsulta.ui.notesScreen.NotesScreen
import com.gsoft.interconsulta.ui.searchPatientScreen.SearchPatientScreen
import com.gsoft.interconsulta.ui.studiesScreen.StudiesScreen
import com.gsoft.interconsulta.ui.theme.InterconsultaTheme
import com.gsoft.interconsulta.utils.*
import com.gsoft.interconsulta.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterconsultaTheme() {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<MainViewModel>()

                NavHost(navController, startDestination = MAIN_SCREEN_ROUTE) {

                    composable(route = MAIN_SCREEN_ROUTE) {
                        MainScreen(navController, viewModel)
                    }

                    composable(route = NEW_PATIENT_ROUTE) {
                        NewPatientScreen(navController, viewModel)
                    }

                    composable(route = SEARCH_PATIENT_ROUTE) {
                        SearchPatientScreen(navController, viewModel)
                    }

                    composable(route = STUDIES_ROUTE) {
                        StudiesScreen(navController, viewModel)
                    }
                    composable(route = LABORATORY_ROUTE){
                        LaboratoryScreen(navController = navController, viewModel = viewModel )
                    }
                    composable(route = NOTES_ROUTE){
                        NotesScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }

 /*   @Composable
    fun InterconsultaApp(content: @Composable () -> Unit) {
        InterconsultaTheme {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {

            }
        }
    }*/
}


