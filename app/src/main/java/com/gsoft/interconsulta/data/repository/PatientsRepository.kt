package com.gsoft.interconsulta.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gsoft.interconsulta.data.models.Patient
import com.gsoft.interconsulta.utils.Resultado
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class PatientsRepository @Inject constructor(
    private val firestore : FirebaseFirestore
) {


    fun insert (patient: Patient)  {
        val new =  HashMap<String,Any>()
        new["dni"] = patient.dni
        new["nombre"] = patient.nombre
        new["surgery"] = patient.surgery
        new["studies"] = patient.studies as List<String>
        new["laboratory"] = patient.laboratory as List<String>
        new["notes"] = patient.notes as List<String>

        firestore.collection("pacientes").document(patient.dni).set(new)
            .addOnSuccessListener {
                Log.d("REPOCREADO", " REPO Paciente Creado ${patient.nombre}")
            }
            .addOnFailureListener {
                Log.d("REPOCREADO", "ERROR creando paciente!")
            }
    }

    fun delete(dni: String) {
        val db = firestore
        db.collection("pacientes").document(dni!!)
            .delete()
            .addOnSuccessListener {
                Log.d("REPOELIMINAR", "Paciente rajado")
            }
            .addOnFailureListener{
                Log.d("REPOELIMINAR", "Error al eliminar paciente")
            }
    }


   fun  update(paciente: Patient) {
        delete(paciente.dni)
        insert(paciente)
    }


    suspend fun getPaciente(dni: String): Resultado<Patient?> {
        val querySnapshot = firestore.collection("pacientes").document(dni).get().await()
        val documento = querySnapshot.toObject<Patient>(Patient::class.java)
        return Resultado.Success(documento)
    }

    suspend fun uploadImagesAndGetURL(imgList: MutableList<Uri>) : Resultado<MutableList<String>>{
        var imgListUrl : MutableList<String> = mutableListOf()
        for (imagen in imgList) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/imagenes/$filename")
            ref.putFile(imagen).await()
            ref.downloadUrl.addOnCompleteListener {
                imgListUrl.add(it.result.toString())
                Log.d("DataSource->", "imgURL = $imgListUrl")
            }


        }
        return Resultado.Success(imgListUrl)
    }

    suspend fun uploadImagesAndGetURL2(imgList: MutableList<Uri>) : MutableList<String>{
        var imgListUrl : MutableList<String> = mutableListOf()
        for (imagen in imgList) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/imagenes/$filename")
            ref.putFile(imagen).await()
            ref.downloadUrl.addOnCompleteListener {
                imgListUrl.add(it.result.toString())
                Log.d("DataSource->", "imgURL = $imgListUrl")
            }


        }
        return imgListUrl
    }

}