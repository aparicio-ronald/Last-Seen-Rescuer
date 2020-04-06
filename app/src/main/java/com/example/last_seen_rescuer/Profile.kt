package com.example.last_seen_rescuer

import org.json.JSONObject

class Profile(profileItemJson: JSONObject) {

    private val userIdentifier = profileItemJson.getString("userIdentifier")
    private val firstName = profileItemJson.getString("userFirstName")
    private val lastName = profileItemJson.getString("userLastName")
    private val dateOfBirth = profileItemJson.getString("userDateOfBirth")
    private val streetAddress = profileItemJson.getString("userStreetAddress")
    private val city = profileItemJson.getString("userCity")
    private val state = profileItemJson.getString("userState")
    private val zipCode = profileItemJson.getString("userZipCode")
    private val macAddress = profileItemJson.getString("userMacAddress")

    fun getUserIdentifier() : String {
        return userIdentifier
    }

    fun getFirstName() : String {
        return firstName
    }

    fun getLastName() : String {
        return lastName
    }

    fun getDateOfBirth() : String {
        return dateOfBirth
    }

    fun getStreetAddress() : String {
        return streetAddress
    }

    fun getCity() : String {
        return city
    }

    fun getState() : String {
        return state
    }

    fun getZipcode() : String {
        return zipCode
    }

    fun getMacAddress() : String {
        return macAddress
    }

    override fun toString(): String {
        return "First Name : " + firstName + "\n" +
                "Last Name : " + lastName + "\n" +
                "Date of Birth : " + dateOfBirth + "\n" +
                "Street Address : " + streetAddress + "\n" +
                "City : " + city + "\n" +
                "State : " + state + "\n" +
                "Zip Code : " + zipCode + "\n"
    }
}