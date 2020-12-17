package com.example.selfieapp.activities

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selfieapp.R
import com.example.selfieapp.adapters.AdapterUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var adapter: AdapterUser
    lateinit var mStorageRef: StorageReference
    var filePath: Uri? = null

    private val REQUEST_CAMERA_CODE = 100
    private val REQUEST_GALLERY_CODE = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        mStorageRef = FirebaseStorage.getInstance().getReference()

    }

    private fun init() {
//        adapter = AdapterUser(this)
//        adapter.setData()
//        recycler_view.layoutManager = LinearLayoutManager(this)
//        recycler_view.adapter = adapter
        button_camera.setOnClickListener(this)
        button_camera_roll.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_camera -> checkCameraPermission()
            R.id.button_camera_roll -> checkGalleryPermission()
        }
    }

//     Check Permissions for Camera and Gallery

    private fun checkGalleryPermission() {
        var permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestGalleryPermission()
        } else {
            openGallery()
        }
    }

    private fun checkCameraPermission() {
        var permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else {
            openCamera()
        }
    }

//     Open applications
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA_CODE)
        Log.d("abc", "message")
    }


//     Permission Requests for Camera and Gallery
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_CODE
        )
    }

    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_GALLERY_CODE
        )
    }

//    Permission Results for Camera and Gallery
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                    openCamera()
                }
            }

            REQUEST_GALLERY_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                    openGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_CODE){
            image_view.setImageURI(data?.data)
            filePath = data?.getData()
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA_CODE){
            image_view.setImageURI(data?.data)
            filePath = data?.getData()
        }
        uploadImage()
    }

    private fun uploadImage() {
        if (filePath != null)
        {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref = mStorageRef.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot> {
                    override fun onSuccess(taskSnapshot:UploadTask.TaskSnapshot) {
                        progressDialog.dismiss()
                        Toast.makeText(this@MainActivity, "Uploaded", Toast.LENGTH_SHORT).show()
                    }
                })
                .addOnFailureListener(object: OnFailureListener{
                    override fun onFailure(@NonNull e:Exception) {
                        progressDialog.dismiss()
                        Toast.makeText(this@MainActivity, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                    }
                })
                .addOnProgressListener(object: OnProgressListener<UploadTask.TaskSnapshot>{
                    override fun onProgress(taskSnapshot:UploadTask.TaskSnapshot) {
                        val progress = ((100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                            .getTotalByteCount()))
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    }
                })
        }
    }
}
