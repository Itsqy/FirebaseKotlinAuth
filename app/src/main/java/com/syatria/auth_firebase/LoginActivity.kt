package com.syatria.auth_firebase

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.syatria.auth_firebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val splash = AnimationUtils.loadAnimation(this,R.anim.splash)
        val fade = AnimationUtils.loadAnimation(this, R.anim.fade)
        val bawah = AnimationUtils.loadAnimation(this, R.anim.bottom)
        val kiri = AnimationUtils.loadAnimation(this, R.anim.fade_rit)
        val kanan = AnimationUtils.loadAnimation(this, R.anim.fade_lef)
//        binding.tvJudul.startAnimation(splash)

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

        binding.btnLogin.setOnClickListener {
            binding.btnLogin.startAnimation(splash)
        }

    }
}