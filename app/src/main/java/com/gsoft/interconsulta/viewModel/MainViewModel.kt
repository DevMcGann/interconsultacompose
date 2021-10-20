package com.gsoft.interconsulta.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
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

    var isNewPatient = mutableStateOf(false)

    var nombre =  mutableStateOf("")

    var dni = mutableStateOf("")

    var surgery = mutableStateOf("")

    ///////////////////////listas

    private val _studyImagesURL = MutableLiveData<List<String>>(listOf())
    val studyImagesURL: LiveData<List<String>> = _studyImagesURL
    private val _studyImagesURI = MutableLiveData<List<Uri>>(listOf())
    val studyImagesURI: LiveData<List<Uri>> = _studyImagesURI

    private val _laboratoryImagesURL = MutableLiveData<List<String>>(listOf())
    val laboratoryImagesURL: LiveData<List<String>> = _laboratoryImagesURL
    private val _laboratoryImagesURI = MutableLiveData<List<Uri>>(listOf())
    val laboratoryImagesURI: LiveData<List<Uri>> = _laboratoryImagesURI


    var notesList = mutableStateListOf<String>()
        private set

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
        notesList.clear()
        showPatient.value = false
    }


    //repo operations

    fun uploadImagesToStorageAndGetURL(uriList:MutableList<Uri>) = liveData(Dispatchers.IO){
        emit(Resultado.Loading())
        try{
            var listaURI : MutableList<Uri> = mutableListOf()
            for (item in uriList){
                listaURI.add(item!!)
            }
            Log.d("IMAGENESURI", listaURI.toString())
            emit(repo.uploadImagesAndGetURL(listaURI))
        }catch (e:Exception){
            emit(Resultado.Failure(e))
        }
    }

    fun addPatient(patient: Patient){
        repo.insert(patient)
    }

    fun finishInsertNewPatient(){
        var labList : MutableList<String> = mutableListOf()

        /*for (item in laboratoryList){
            labList.add(item)
        }

        var studyList : MutableList<String> = mutableListOf()

        for (item in studiesList){
            studyList.add(item)
        }*/

        var noteList : MutableList<String> = mutableListOf()

        for (item in notesList){
            noteList.add(item)
        }

        val paciente = Patient(
            dni = dni.value,
            nombre = nombre.value,
            surgery= surgery.value,
            laboratory = labList,
            //studies =studyList ,
            notes =noteList
        )

        addPatient(paciente)

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
                                //addItemToLaboratory(lab)
                            }
                        }
                        if( result.data.studies != null){
                            for (stu in result.data.studies){
                               // addItemToStudies(stu)
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