package com.example.librohub

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private var currentImageUri: Uri? = null
    private var currentImageResId = R.drawable.baseline_account_circle_24
    
    private lateinit var ivProfileImage: ImageView
    private var ivEditImagePreview: ImageView? = null
    private var tempImageUri: Uri? = null

    // Launcher untuk mengambil gambar dari galeri
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            tempImageUri = it
            ivEditImagePreview?.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        ivProfileImage = view.findViewById(R.id.ivProfileImage)
        val tvName = view.findViewById<TextView>(R.id.tvProfileName)
        val tvEmail = view.findViewById<TextView>(R.id.tvProfileEmail)

        val btnRegisterLibrary = view.findViewById<Button>(R.id.btnRegisterLibrary)
        val btnMyLibrary = view.findViewById<Button>(R.id.btnMyLibrary)
        val btnEditProfile = view.findViewById<Button>(R.id.btnEditProfile)
        val btnSettings = view.findViewById<Button>(R.id.btnSettings)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        btnRegisterLibrary.setOnClickListener {
            val intent = Intent(requireContext(), RegisterLibraryActivity::class.java)
            startActivity(intent)
        }

        // PERBAIKAN: Menggunakan getLibrary(context) sebagai ganti registeredLibrary
        btnMyLibrary.setOnClickListener {
            val library = LibraryManager.getLibrary(requireContext())
            if (library != null) {
                val intent = Intent(requireContext(), LibraryDetailsActivity::class.java)
                intent.putExtra("LIB_NAME", library.name)
                intent.putExtra("LIB_EMAIL", library.email)
                intent.putExtra("LIB_LOCATION", library.location)
                intent.putExtra("LIB_HOURS", library.hours)
                intent.putExtra("LIB_CONTACT", library.contact)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Kamu belum mendaftarkan perpustakaan", Toast.LENGTH_SHORT).show()
            }
        }

        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        btnEditProfile.setOnClickListener {
            showEditDialog(tvName, tvEmail)
        }

        btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }

    private fun showEditDialog(tvName: TextView, tvEmail: TextView) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_profile, null)
        
        ivEditImagePreview = dialogView.findViewById(R.id.ivEditProfileImage)
        val btnChangePhoto = dialogView.findViewById<Button>(R.id.btnChangePhoto)
        val etName = dialogView.findViewById<EditText>(R.id.etEditName)
        val etEmail = dialogView.findViewById<EditText>(R.id.etEditEmail)

        if (currentImageUri != null) {
            ivEditImagePreview?.setImageURI(currentImageUri)
        } else {
            ivEditImagePreview?.setImageResource(currentImageResId)
        }
        
        etName.setText(tvName.text)
        etEmail.setText(tvEmail.text)

        tempImageUri = currentImageUri

        btnChangePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                currentImageUri = tempImageUri
                if (currentImageUri != null) {
                    ivProfileImage.setImageURI(currentImageUri)
                }
                tvName.text = etName.text.toString()
                tvEmail.text = etEmail.text.toString()
            }
            .setNegativeButton("Batal") { _, _ ->
                ivEditImagePreview = null
            }
            .show()
    }
}