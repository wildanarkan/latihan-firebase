package com.wildanarkan.latihanfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.wildanarkan.latihanfirebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvToRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()


            //Validasi Email
            if (email.isEmpty()){
                binding.edtEmailLogin.error = "Email Wajib Di Isi!"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi Email Tidak Sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailLogin.error = "Email Tidak Valid!"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi Password
            if (password.isEmpty()){
                binding.edtPasswordLogin.error = "Password Wajib Di Isi!"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi Panjang Password
            if(password.length < 6){
                binding.edtPasswordLogin.error = "Password Minimal 6 Karakter"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Selamat Datang $email!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}