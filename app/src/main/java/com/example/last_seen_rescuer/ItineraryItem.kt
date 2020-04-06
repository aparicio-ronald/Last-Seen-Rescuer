package com.example.last_seen_rescuer

import org.json.JSONObject

class ItineraryItem (itineraryItemJSON : JSONObject) {
    private val trail = itineraryItemJSON.getString("trail")
    private val date = itineraryItemJSON.getString("date")
    private val hour = itineraryItemJSON.getString("hour")
    private val minute = itineraryItemJSON.getString("minute")
    private val ampm = itineraryItemJSON.getString("ampm")

    fun getTrail() : String {
        return trail
    }

    fun getDate() : String {
        return date
    }

    fun getHour() : String {
        return hour
    }

    fun getMinute() : String {
        return minute
    }

    fun getAmPm() : String {
        return ampm
    }

    override fun toString(): String {
        return "Trail: " + trail + "\nDate: " + date + "\nTime: " + hour + ":" + minute +  " " +
                ampm
    }
}