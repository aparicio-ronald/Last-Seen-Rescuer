package com.example.last_seen_rescuer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlin.collections.ArrayList

class SelectionPage : AppCompatActivity() {

    private lateinit var searchFieldsTextView : TextView
    private lateinit var profileListView : ListView
    private lateinit var profileAdapter: ArrayAdapter<Profile>
    private lateinit var profileArrayList: ArrayList<Profile>

    private lateinit var firstName : String
    private lateinit var lastName : String
    private lateinit var dateOfBirth : String
    private lateinit var streetAddress : String
    private lateinit var city : String
    private lateinit var state : String
    private lateinit var zipCode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_page)

        initializeViewsAndAdapters()
        getSearchCriteria()

        profileListView.setOnItemClickListener {parent, view, position, id ->
            val profile = profileAdapter.getItem(position)!!

            Toast.makeText(applicationContext, profile.toString(), Toast.LENGTH_SHORT).show()

            val intent = Intent(this, BluetoothPage::class.java)

            intent.putExtra("userIdentifier", profile.getUserIdentifier())
            intent.putExtra("firstName", profile.getFirstName())
            intent.putExtra("lastName", profile.getLastName())
            intent.putExtra("dateOfBirth", profile.getDateOfBirth())
            intent.putExtra("macAddress", profile.getMacAddress())

            startActivity(intent)
        }

        updateProfileList()
    }

    private fun initializeViewsAndAdapters() {
        searchFieldsTextView = findViewById(R.id.search_fields_text_view)
        profileListView = findViewById(R.id.profile_list_view)
        profileArrayList = ArrayList()
        profileAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, profileArrayList)

        profileListView.adapter = profileAdapter
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
    }

    private fun updateProfileList() {
        val queue = Volley.newRequestQueue(this)
        val urlString = getString(R.string.server_url) + getString(R.string.server_profiles) +
                firstName + "/" + lastName

        val getProfilesRequest = JsonArrayRequest(
            Request.Method.GET, urlString, null,
            Response.Listener { response ->

                for (i in 0 until response.length()) {
                    val profileItemJson = response.getJSONObject(i)
                    val profile = Profile(profileItemJson)

                    //FILTER OUT OPTIONALS

                    profileArrayList.add(profile)
                }

                profileAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "COULD NOT CONTACT SERVER", Toast.LENGTH_SHORT).show()
            })

        queue.add(getProfilesRequest)
    }
}
