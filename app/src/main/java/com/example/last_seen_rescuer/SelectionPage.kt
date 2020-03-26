package com.example.last_seen_rescuer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

/*
 * ALL VARIABLES AND FUNCTIONS WITH THE WORD TEMPORARY ONLY HERE FOR DEMO PURPOSES
 * UNTIL WE CONNECT THE MOBILE APP TO THE BACKEND
 */

class SelectionPage : AppCompatActivity() {

    private lateinit var searchFieldsTextView : TextView
    private lateinit var profileListView : ListView
    private lateinit var profileAdapter: ArrayAdapter<String>
    private lateinit var profileArrayList: ArrayList<String>

    private lateinit var firstName : String
    private lateinit var lastName : String
    private lateinit var dateOfBirth : String
    private lateinit var streetAddress : String
    private lateinit var city : String
    private lateinit var state : String
    private lateinit var zipCode : String

    private lateinit var temporary_generateProfileButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_page)

        initializeViewsAndAdapters()
        getSearchCriteria()

        temporary_generateProfileButton.setOnClickListener {
            profileArrayList.add("PROFILE NUMBER " + UUID.randomUUID())
            profileAdapter.notifyDataSetChanged()
        }

        profileListView.setOnItemClickListener {parent, view, position, id ->
            val profile = profileAdapter.getItem(position)

            Toast.makeText(applicationContext, profile, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, BluetoothPage::class.java)

            intent.putExtra("profile", profile)

            startActivity(intent)
        }
    }

    private fun initializeViewsAndAdapters() {
        searchFieldsTextView = findViewById(R.id.search_fields_text_view)
        profileListView = findViewById(R.id.profile_list_view)
        profileArrayList = ArrayList()
        profileAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, profileArrayList)

        profileListView.adapter = profileAdapter

        temporary_generateProfileButton = findViewById(R.id.temporary_add_profile_button)
    }

    @SuppressLint("SetTextI18n")
    private fun getSearchCriteria() {
        firstName = intent.getStringExtra("firstName")!!
        lastName = intent.getStringExtra("lastName")!!
        dateOfBirth = intent.getStringExtra("dateOfBirth")!!
        streetAddress = intent.getStringExtra("streetAddress")!!
        city = intent.getStringExtra("city")!!
        state = intent.getStringExtra("state")!!
        zipCode = intent.getStringExtra("zipCode")!!

        searchFieldsTextView.text = "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "Date Of Birth: " + dateOfBirth + "\n" +
                "Address: " + streetAddress + "\n" +
                "City: " + city + "\n" +
                "State: " + state + "\n" +
                "Zip Code: " + zipCode

        // TODO POPULATE SCROLLVIEW WITH PROFILES MATCHING SEARCH CRITERIA FROM DATABASE
        // NEED USER IDENTIFIER TO FIND ITINERARY
    }
}
