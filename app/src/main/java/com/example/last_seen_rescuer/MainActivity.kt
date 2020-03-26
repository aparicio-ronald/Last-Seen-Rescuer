package com.example.last_seen_rescuer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var submitButton : Button
    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var dateOfBirth  : EditText
    private lateinit var streetAddress : EditText
    private lateinit var city : EditText
    private lateinit var state : EditText
    private lateinit var zipCode : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton = findViewById(R.id.button)

        submitButton.setOnClickListener {
            val intent = Intent(this, SelectionPage::class.java)
            startActivity(intent)


        }

    }

    private fun initializeViewsAndAdapters() {
        firstName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        dateOfBirth = findViewById(R.id.dateOfBirth)
        streetAddress = findViewById(R.id.streetAddress)
        city = findViewById(R.id.city)
        state = findViewById(R.id.state)
        zipCode = findViewById(R.id.zipCode)



    }

    private fun requiredFieldsNotEmpty(): Boolean {
        if (TextUtils.isEmpty(firstName.text)
            || TextUtils.isEmpty(lastName.text)
            || TextUtils.isEmpty(dateOfBirth.text)
            || TextUtils.isEmpty(streetAddress.text)
            || TextUtils.isEmpty(city.text)
            || TextUtils.isEmpty(state.text)
            || TextUtils.isEmpty(zipCode.text))

        {
            Toast.makeText(applicationContext, "REQUIRED FIELDS CANNOT BE EMPTY", Toast.LENGTH_SHORT).show()

            return false
        }
        return true
    }


}


