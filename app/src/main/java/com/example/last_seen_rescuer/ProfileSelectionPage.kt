package com.example.last_seen_rescuer

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class ProfileSelectionPage : AppCompatActivity() {

    private lateinit var profileListView : ListView
    private lateinit var temporary_generateProfileButton : Button
    private var temporary_id = 0

    private lateinit var profileAdapter: ArrayAdapter<String>
    private lateinit var profileArrayList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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