package com.example.last_seen_rescuer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var profileInformation : TextView

    private lateinit var bluetoothLogListView : ListView
    private var temporary_dummy_data = 0

    private lateinit var bluetoothLogAdapter : ArrayAdapter<String>
    private lateinit var bluetoothLogArrayList : ArrayList<String>

    private lateinit var getDataButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViewsAndAdapters()

        // TODO : PULL EXTRAS FROM INTENT AND POPULATE PROFILE INFORMATION

        getDataButton.setOnClickListener {
            bluetoothLogArrayList.add("TEMPORARY LOG DATA " + temporary_dummy_data)
            bluetoothLogAdapter.notifyDataSetChanged()

            temporary_dummy_data++

            // TODO : GET DATA FROM BLUETOOTH CHECKPOINTS FOR THE GIVEN MAC ADDRESS
        }
    }

    private fun initializeViewsAndAdapters() {
        bluetoothLogListView = findViewById(R.id.bluetooth_checkpoint_data_list_view)
        bluetoothLogArrayList = ArrayList()
        bluetoothLogAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, bluetoothLogArrayList)

        bluetoothLogListView.adapter = bluetoothLogAdapter

        getDataButton = findViewById(R.id.get_data_button)

        profileInformation = findViewById(R.id.profile_information_text_view)
    }
}
