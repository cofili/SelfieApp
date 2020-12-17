package com.example.selfieapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.selfieapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edittext_email
import kotlinx.android.synthetic.main.activity_login.edittext_password
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {

        text_view_new_user.setOnClickListener{
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        button_login.setOnClickListener {
            var email = edittext_email.text.toString()
            var password = edittext_password.text.toString()

            var auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, object: OnCompleteListener<AuthResult>{
                    override fun onComplete(task: Task<AuthResult>) {
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext, "Logged in successfully!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        }else{
                            Toast.makeText(applicationContext, "Login failed: Username or password is incorrect", Toast.LENGTH_LONG).show()
                        }
                    }

                } )
        }
    }
}