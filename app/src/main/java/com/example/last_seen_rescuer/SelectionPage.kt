package com.example.last_seen_rescuer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

/*
 * ALL VARIABLES AND FUNCTIONS WITH THE WORD TEMPORARY ONLY HERE FOR DEMO PURPOSES
 * UNTIL WE CONNECT THE MOBILE APP TO THE BACKEND
 */

class SelectionPage : AppCompatActivity() {

    private lateinit var profileListView : ListView
    private lateinit var temporary_generateProfileButton : Button
    private var temporary_id = 0

    private lateinit var profileAdapter: ArrayAdapter<String>
    private lateinit var profileArrayList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_page)

        initializeViewsAndAdapters()

        // TODO: PULL EXTRAS FROM THE INTENT AND POPULATE THE SCROLLVIEW

        temporary_generateProfileButton.setOnClickListener {
            profileArrayList.add("PROFILE NUMBER " + temporary_id)
            profileAdapter.notifyDataSetChanged()

            temporary_id++;
        }

        profileListView.setOnItemClickListener {parent, view, position, id ->
            val profile = profileAdapter.getItem(position)

            Toast.makeText(applicationContext, profile, Toast.LENGTH_SHORT).show()

            // TODO: START CHECKPOINT PAGE WITH INTENT CONTAINING PROFILE INFORMATION
            val intent = Intent(this, BluetoothPage::class.java)
            startActivity(intent)
        }
    }

    private fun initializeViewsAndAdapters() {
        profileListView = findViewById(R.id.profile_list_view)
        profileArrayList = ArrayList()
        profileAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, profileArrayList)

        profileListView.adapter = profileAdapter

        temporary_generateProfileButton = findViewById(R.id.temporary_add_profile_button)
    }
}