package com.example.last_seen_rescuer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class BluetoothPage : AppCompatActivity() {

    private lateinit var profileInformation : TextView
    private lateinit var itineraryListView : ListView
    private lateinit var bluetoothLogListView : ListView
    private var temporary_dummy_data = 0

    private lateinit var itineraryAdapter : ArrayAdapter<ItineraryItem>
    private lateinit var itineraryArrayList : ArrayList<ItineraryItem>

    private lateinit var bluetoothLogAdapter : ArrayAdapter<String>
    private lateinit var bluetoothLogArrayList : ArrayList<String>

    private lateinit var getDataButton : Button

    private lateinit var userIdentifier : String
    private lateinit var macAddress : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_page)

        initializeViewsAndAdapters()
        pullProfileInformationFromExtras()

        getDataButton.setOnClickListener {
            bluetoothLogArrayList.add("DUMMY LOG DATA " + temporary_dummy_data)
            bluetoothLogAdapter.notifyDataSetChanged()

            temporary_dummy_data++

            // TODO : GET DATA FROM BLUETOOTH CHECKPOINTS FOR THE GIVEN MAC ADDRESS
        }
    }

    private fun initializeViewsAndAdapters() {
        itineraryListView = findViewById(R.id.itinerary_list_view)
        itineraryArrayList = ArrayList()
        itineraryAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itineraryArrayList)

        itineraryListView.adapter = itineraryAdapter

        bluetoothLogListView = findViewById(R.id.bluetooth_checkpoint_data_list_view)
        bluetoothLogArrayList = ArrayList()
        bluetoothLogAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, bluetoothLogArrayList)

        bluetoothLogListView.adapter = bluetoothLogAdapter

        getDataButton = findViewById(R.id.get_data_button)

        profileInformation = findViewById(R.id.profile_information_text_view)
    }

    @SuppressLint("SetTextI18n")
    private fun pullProfileInformationFromExtras() {
        profileInformation.text = "NAME: " + intent.getStringExtra("firstName") + " " +
                intent.getStringExtra("lastName") + "\n" +
                "Date of Birth: " +intent.getStringExtra("dateOfBirth")

        userIdentifier = intent.getStringExtra("userIdentifier")!!
        macAddress = intent.getStringExtra("macAddress")!!

        val queue = Volley.newRequestQueue(this)
        val urlString = getString(R.string.server_url) + getString(R.string.server_itinerary) +
                userIdentifier

        val getItineraryRequest = JsonArrayRequest(
            Request.Method.GET, urlString, null,
            Response.Listener { response ->

                for (i in 0 until response.length()) {
                    val itineraryItemJSON = response.getJSONObject(i)
                    val itineraryItem = ItineraryItem(itineraryItemJSON)

                    itineraryArrayList.add(itineraryItem)
                }

                itineraryAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "COULD NOT CONTACT SERVER", Toast.LENGTH_SHORT).show()
            })

        queue.add(getItineraryRequest)
    }
}