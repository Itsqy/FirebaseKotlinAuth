package com.syatria.auth_firebase.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.syatria.auth_firebase.LoginActivity
import com.syatria.auth_firebase.databinding.FragmentHomeBinding
import com.syatria.auth_firebase.ui.dashboard.DashboardViewModel
import java.io.ByteArrayOutputStream

class HomeFragment : Fragment() {


    lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var imgUri: Uri

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cviUser.setOnClickListener {
            goToCamera()
        }
        binding.btnLogout.setOnClickListener {
            keluar()
        }

    }

    private fun goToCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->

            activity?.packageManager?.let {
                intent?.resolveActivity(it).also {
                    startActivityForResult(intent, REQ_CAM)

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAM && resultCode == RESULT_OK) {

            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImageToFirebase(imgBitmap)

        }
    }

    private fun uploadImageToFirebase(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()

        val ref =
            FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.email}")


        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val img = baos.toByteArray()
        ref.putBytes(img)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener { Task ->
                        Task.result?.let { Uri ->
                            imgUri = Uri
                            binding.cviUser.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }


    private fun keluar() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val i = Intent(context, LoginActivity::class.java)
        startActivity(i)
        activity?.finish()
    }

    companion object {
        const val REQ_CAM = 100
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}