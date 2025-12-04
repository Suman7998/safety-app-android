package com.safety.womensafetyapp

data class EmergencyContact(
    val id: Long = 0,
    val name: String,
    val phoneNumber: String,
    val relationship: String = ""
) {
    fun getInitial(): String {
        return if (name.isNotEmpty()) {
            name.first().uppercaseChar().toString()
        } else {
            "?"
        }
    }
    
    fun getFormattedPhone(): String {
        // Simple phone formatting - you can enhance this based on your needs
        return if (phoneNumber.length >= 10) {
            val cleaned = phoneNumber.replace(Regex("[^\\d]"), "")
            when {
                cleaned.length == 10 -> {
                    "${cleaned.substring(0, 3)}-${cleaned.substring(3, 6)}-${cleaned.substring(6)}"
                }
                cleaned.length == 11 && cleaned.startsWith("1") -> {
                    "+1 ${cleaned.substring(1, 4)}-${cleaned.substring(4, 7)}-${cleaned.substring(7)}"
                }
                else -> phoneNumber
            }
        } else {
            phoneNumber
        }
    }
}
