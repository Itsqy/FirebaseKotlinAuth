package com.syatria.auth_firebase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.syatria.auth_firebase.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()




        binding.btnRegister.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()


            if (email.isEmpty()) {
                binding.edtEmail.error = "Email harus di isi!"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.error = "Email harus dengan format yang benar!"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.edtPassword.error = "Password harus di isi!"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.edtPassword.error = "Password Minila 6 karakter!"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }

            daftarUserFirebase(email, password)


        }
        binding.tvToLogin.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }

    private fun daftarUserFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {

                    Toast.makeText(this, "$email Register successful Guyss !!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, " ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

    }
}