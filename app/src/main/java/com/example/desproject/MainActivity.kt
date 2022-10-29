package com.example.desproject


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.desproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var db=Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        setData()
        binding.button.setOnClickListener {
            val name=binding.name1.text.toString().trim()
            val address=binding.address1.text.toString().trim()
            val phone=binding.phNo1.text.toString().trim()
            val direction=binding.direction1.text.toString().trim()
            val service=binding.service1.text.toString().trim()
            val latitude=binding.latitude1.text.toString().trim()
            val longitude=binding.longitude1.text.toString().trim()

            val updateMap=mapOf(
              "name" to name,
                "address" to address,
                "phone" to phone,
                "direction" to direction,
                "service" to service,
                "latitude" to latitude,
                "longitude" to longitude
            )
            val userId=firebaseAuth.currentUser!!.uid
        db.collection("user").document(userId).update(updateMap)
            Toast.makeText(this, "Successfully Edited!!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


    }
    private fun setData(){
        val userId=firebaseAuth.currentUser!!.uid
        val ref=db.collection("user").document(userId)
        ref.get().addOnSuccessListener {
            if(it!=null){
                val Name=it.data?.get("name")?.toString()
                val Address=it.data?.get("address")?.toString()
                val Phone=it.data?.get("phone")?.toString()
                val Direction=it.data?.get("direction")?.toString()
                val Service=it.data?.get("service")?.toString()
                val Latitude=it.data?.get("latitude")?.toString()
                val Longitude=it.data?.get("longitude")?.toString()


                binding.name1.setText(Name)
                binding.address1.setText(Address)
                binding.phNo1.setText(Phone)
                binding.direction1.setText(Direction)
                binding.service1.setText(Service)
                binding.latitude1.setText(Latitude)
                binding.longitude1.setText(Longitude)

            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!!", Toast.LENGTH_SHORT).show()
            }
    }
}