package com.example.desproject


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.desproject.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class details : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var firebaseAuth: FirebaseAuth


    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            val Name = binding.name1.text.toString().trim()
            val Address = binding.address1.text.toString().trim()
            val Phone = binding.phNo1.text.toString().trim()
            val Direction = binding.direction1.text.toString().trim()
            val Service = binding.service1.text.toString().trim()
            val Latitude=binding.latitude1.text.toString().trim()
            val Longitude=binding.longitude1.text.toString().trim()

            val userMap = hashMapOf(
                "name" to Name,
                "address" to Address,
                "phone" to Phone,
                "direction" to Direction,
                "service" to Service,
                "latitude" to Latitude,
                "longitude" to Longitude
            )
            val userId = firebaseAuth.currentUser!!.uid
            db.collection("user").document(userId).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Added!!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed!!", Toast.LENGTH_SHORT).show()
                }
        }
    }
}









