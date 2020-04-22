package com.example.last_seen_rescuer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class BluetoothPage : AppCompatActivity() {

    private lateinit var profileInformation : TextView
    private lateinit var itineraryListView : ListView
    private lateinit var bluetoothLogListView : ListView

    private lateinit var itineraryAdapter : ArrayAdapter<ItineraryItem>
    private lateinit var itineraryArrayList : ArrayList<ItineraryItem>

    private lateinit var bluetoothLogAdapter : ArrayAdapter<String>
    private lateinit var bluetoothLogArrayList : ArrayList<String>

    private lateinit var getDataButton : Button

    private lateinit var userIdentifier : String
    private lateinit var macAddress : String

    private lateinit var lastSeenUUID : UUID
    private lateinit var mBlueToothAdapter: BluetoothAdapter

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context : Context, intent : Intent) {
            Log.i("BLUETOOTH", "BroadcastReceiver onReceive()")
            handleBTDevice(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_page)

        initializeViewsAndAdapters()
        pullProfileInformationFromExtras()

        getDataButton.setOnClickListener {
            Log.i("BLUETOOTH", "GET DATA BUTTON CLICKED")

            setUpBroadcastReceiver()
            setUpDiscovery()
        }
    }

    private fun setUpDiscovery() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(mReceiver, filter)

        mBlueToothAdapter!!.startDiscovery()
    }

    private fun setUpBroadcastReceiver() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                ACCESS_FINE_LOCATION)
            Log.i("BLUETOOTH", "GET PERMISSION")
            return
        }
    }

    private fun handleBTDevice(intent : Intent) {
        Log.i("BLUETOOTH", "HANDLE BT DEVICE")
        val action = intent.action

        if (BluetoothDevice.ACTION_FOUND == action) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            val deviceMacAddress = device.address

            Log.i("BLUETOOTH", deviceMacAddress)

            RequestLogDataThread(device, deviceMacAddress).start()
        }
    }

    private inner class RequestLogDataThread(mmDevice: BluetoothDevice, deviceMacAddress: String): Thread() {
        private var mmSocket: BluetoothSocket? = null
        private var targetMacAddress: ByteArray

        init {
            Log.i("BLUETOOTH", "REQUEST LOG DATA THREAD")
            mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(lastSeenUUID)
            targetMacAddress = deviceMacAddress.toByteArray()
        }

        override fun run() {
            mmSocket!!.connect()
            Log.i("BLUETOOTH", "CONNECTION ESTABLISHED")

            val outputStream = mmSocket!!.outputStream
            outputStream.write(targetMacAddress)

            Log.i("BLUETOOTH", "REQUEST SENT")

            val inputStream = mmSocket!!.inputStream
            val logData = ByteArray(255)
            inputStream.read(logData)

            Log.i("DATA", logData.toString(Charsets.UTF_8))

            Log.i("BLUETOOTH", "REPLY RECEIVED")
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

        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter()

        lastSeenUUID = UUID.fromString(UUID_STRING)
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

    companion object {
        private const val ACCESS_FINE_LOCATION = 1
        private const val UUID_STRING = "a3c8734a-0aea-439e-8e06-9d7098dcf3a8"
    }
}