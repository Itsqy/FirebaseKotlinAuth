package com.syatria.auth_firebase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.syatria.auth_firebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val splash = AnimationUtils.loadAnimation(this, R.anim.splash)
        val fade = AnimationUtils.loadAnimation(this, R.anim.fade)
        val bawah = AnimationUtils.loadAnimation(this, R.anim.bottom)
        val kiri = AnimationUtils.loadAnimation(this, R.anim.fade_rit)
        val kanan = AnimationUtils.loadAnimation(this, R.anim.fade_lef)
        val dalem = AnimationUtils.loadAnimation(this, R.anim.`in`)
//        LottieAnimationView water findViewById(R.id.water)
        binding.tvJudul.startAnimation(splash)

//       val card = binding.card.startAnimation(splash)
//        if (card != null){}
        binding.tvJudul.startAnimation(fade)
        binding.lupaPass.startAnimation(fade)
        binding.edtEmail.startAnimation(kanan)
        binding.edtPassword.startAnimation(kiri)
        binding.btnLogin.startAnimation(splash)
        binding.tvToRegis.startAnimation(bawah)


        binding.tvToRegis.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }


        binding.tvJudul.setOnClickListener {
            binding.tvJudul.startAnimation(fade)
        }
        binding.edtEmail.setOnClickListener {
            binding.edtEmail.startAnimation(dalem)
        }
        binding.edtPassword.setOnClickListener {
//            water.playAnimation()
        }

        auth = FirebaseAuth.getInstance()


        binding.btnLogin.setOnClickListener {
            binding.btnLogin.startAnimation(splash)
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()
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

            if (pass.isEmpty()) {
                binding.edtPassword.error = "Password harus di isi!"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }

//            if (pass.length < 6) {
//                binding.edtPassword.error = "Password Minimal 6 karakter!"
//                binding.edtPassword.requestFocus()
//                return@setOnClickListener
//            }

            loginUsertoFirebase(email, pass)

        }


    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this, HomeActivity::class.java).also { intent ->
                Toast.makeText(this, "Welcome ${FirebaseAuth.getInstance().currentUser!!.email}", Toast.LENGTH_SHORT).show()
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)


            }
        }
    }
    private fun loginUsertoFirebase(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {



                        val i = Intent(this, HomeActivity::class.java)
                        Toast.makeText(this, "Welcome $email !! ", Toast.LENGTH_SHORT).show()
                        startActivity(i)
                        finish()


                } else {

                    Toast.makeText(this, " ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }

            }

    }


}