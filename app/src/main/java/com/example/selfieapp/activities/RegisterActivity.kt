package com.example.selfieapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.selfieapp.R
import com.example.selfieapp.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
    }

    private fun init() {
        button_register.setOnClickListener {
            var email = edittext_email.text.toString()
            var password = edittext_password.text.toString()

            var auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, object: OnCompleteListener<AuthResult>{
                    override fun onComplete(task: Task<AuthResult>) {
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext, "Registered successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, LoginActivity::class.java))
                        }else{
                            Toast.makeText(applicationContext, "Registration failed", Toast.LENGTH_SHORT).show()
                            Log.d("abc", "failure")
                        }
                    }

                } )

            //Real time DB

            var user = User(email, password)
            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.getReference("users")

            // insert blank record to generate unique id and save it in local variable
            var userId = databaseReference.push().key

            // find collection, then in collection find id and save data under id
            databaseReference.child(userId!!).setValue(user)
            Log.d("abc", "success")
            Toast.makeText(applicationContext, "Record Inserted", Toast.LENGTH_SHORT).show()


        }
    }
}