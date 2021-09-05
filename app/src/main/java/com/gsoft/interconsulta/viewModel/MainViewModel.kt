package com.gsoft.interconsulta.viewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gsoft.interconsulta.data.models.Patient
import com.gsoft.interconsulta.data.repository.PatientsRepository
import com.gsoft.interconsulta.utils.Resultado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var repo: PatientsRepository
) : ViewModel() {

    var currentPatient = mutableStateOf<Patient>(Patient())

    var currentBackNavigationTo = mutableStateOf("")

    var showDialog =  mutableStateOf(false)

    var showMessageDialog = mutableStateOf(false)

    var showPatient = mutableStateOf(false)

    var isLoading = mutableStateOf(false)

    var error = mutableStateOf(false)

    var nombre =  mutableStateOf("")

    var dni = mutableStateOf("")

    var laboratoryList = mutableStateListOf<String>()
        private set

    var studiesList = mutableStateListOf<String>()
        private set

    var notesList = mutableStateListOf<String>()
        private set


    fun addItemToLaboratory(item: String) {
        laboratoryList.add(item)
    }

    fun removeItemItemFromLaboratory(item: String) {
        laboratoryList.remove(item)
    }

    fun addItemToStudies(item:String){
        studiesList.add(item)
    }

    fun removeItemFromStudies(item:String){
        studiesList.remove(item)
    }

    fun addItemToNotes(item:String){
        notesList.add(item)
    }

    fun removeItemFromNotes(item:String){
        notesList.remove(item)
    }

    fun clearViewModel(){
        dni.value = ""
        nombre.value = ""
        studiesList.clear()
        laboratoryList.clear()
        notesList.clear()
        showPatient.value = false
    }


    //repo operations

    fun addPatient(patient: Patient){
        repo.insert(patient)
    }

    fun updatePaciente (patient: Patient){
        repo.update(patient)
    }

    fun deletePaciente(dni: String){
        repo.delete(dni)
    }

     fun fetchPatient (patientDni:String){
        viewModelScope.launch {
            isLoading.value = true
            error.value = false
            showPatient.value = false
            val result = repo.getPaciente(patientDni)
            when(result) {
                is Resultado.Loading -> {isLoading.value = true}
                is Resultado.Success -> {
                    if(result.data != null){
                        currentPatient.value = result.data
                         dni.value = result.data.dni
                        nombre.value = result.data.nombre
                        if( result.data.laboratory != null){
                            for (lab in result.data.laboratory){
                                addItemToLaboratory(lab)
                            }
                        }
                        if( result.data.studies != null){
                            for (stu in result.data.studies){
                                addItemToStudies(stu)
                            }
                        }
                        if( result.data.notes != null){
                            for (note in result.data.notes){
                                addItemToNotes(note)
                            }
                        }
                        showPatient.value = true

                    }
                    isLoading.value = false
                    error.value = false
                }
                is Resultado.Failure -> {
                    isLoading.value = false
                    showPatient.value = false
                    error.value = true
                }
            }
         }
     }
}