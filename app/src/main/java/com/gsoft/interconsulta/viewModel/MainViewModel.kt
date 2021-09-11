package com.gsoft.interconsulta.viewModel

import android.net.Uri
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

    var selectedCategory = mutableStateOf("")

    var nombre =  mutableStateOf("")

    var dni = mutableStateOf("")

    var surgery = mutableStateOf("")

    ///////////////////////listas

    var laboratoryList = mutableStateListOf<String>()
        private set
    var laboratoryListUri = mutableStateListOf<Uri?>(null)

    var studiesList = mutableStateListOf<String>()
        private set
    var studiesListUri = mutableStateListOf<Uri?>(null)


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

    fun addUriToLaboratory(uri:Uri){
        laboratoryListUri.add(uri)
    }
    fun removeUriFromLaboratory(uri:Uri){
        laboratoryListUri.remove(uri)
    }

    fun addUriToStudies(uri:Uri){
        studiesListUri.add(uri)
    }
    fun removeUriFromStudies(uri:Uri){
        studiesListUri.remove(uri)
    }


    fun addItemToNotes(item:String){
        notesList.add(item)
    }

    fun removeItemFromNotes(item:String){
        notesList.remove(item)
    }

    ///////////END LISTAS

    fun clearViewModel(){
        dni.value = ""
        nombre.value = ""
        studiesList.clear()
        laboratoryList.clear()
        laboratoryListUri.clear()
        studiesListUri.clear()
        notesList.clear()
        showPatient.value = false
    }


    //repo operations

    fun addPatient(patient: Patient){
        repo.insert(patient)
    }

    fun finishInsertNewPatient(){
        var labList : MutableList<String> = mutableListOf()

        for (item in laboratoryList){
            labList.add(item)
        }

        var studyList : MutableList<String> = mutableListOf()

        for (item in studiesList){
            studyList.add(item)
        }

        var noteList : MutableList<String> = mutableListOf()

        for (item in notesList){
            noteList.add(item)
        }

        val paciente = Patient(
            dni = dni.value,
            nombre = nombre.value,
            surgery= surgery.value,
            laboratory = labList,
            studies =studyList ,
            notes =noteList
        )

        addPatient(paciente)

    }

    fun uploadStudiesToStorageAndGetURL() = liveData(Dispatchers.IO){
        var lista : MutableList<Uri> = mutableListOf()
        emit(Resultado.Loading())
        try{
            for (uri in studiesListUri){
                lista.add(uri!!)
            }
            emit(repo.uploadImagesAndGetURL(lista))
        }catch (e:Exception){
            emit(Resultado.Failure(e))
        }
    }
    fun uploadStudiesToStorageAndGetURL2() = liveData<MutableList<String>>(Dispatchers.IO){
        var lista : MutableList<Uri> = mutableListOf()
        try{
            //studieslist is populated with images selected from phone`gallery
            for (uri in studiesListUri){
                lista.add(uri!!)
            }
           repo.uploadImagesAndGetURL2(lista)
        }catch (e:Exception){

        }
    }

    fun uploadLaboratoryToStorageAndGetURL() = liveData(Dispatchers.IO){
        var lista : MutableList<Uri> = mutableListOf()
        emit(Resultado.Loading())
        try{
            for (uri in laboratoryListUri){
                lista.add(uri!!)
            }
            emit(repo.uploadImagesAndGetURL(lista))
        }catch (e:Exception){
            emit(Resultado.Failure(e))
        }
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
                        surgery.value = result.data.surgery

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