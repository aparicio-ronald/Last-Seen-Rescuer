package com.example.last_seen_rescuer

import org.json.JSONObject

class ItineraryItem (itineraryItemJSON : JSONObject) {
    private val trail = itineraryItemJSON.getString("trail")
    private val date = itineraryItemJSON.getString("date")
    private val hour = itineraryItemJSON.getString("hour")
    private val minute = itineraryItemJSON.getString("minute")
    private val ampm = itineraryItemJSON.getString("ampm")

    override fun toString(): String {
        return "Trail: " + trail + "\n" +
                "Date: " + date + "\n" +
                "Time: " + hour + ":" + minute +  " " + ampm
    }
}