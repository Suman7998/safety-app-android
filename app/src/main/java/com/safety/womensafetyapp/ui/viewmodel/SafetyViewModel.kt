package com.safety.womensafetyapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.safety.womensafetyapp.data.model.*
import com.safety.womensafetyapp.data.repository.SafetyRepository
import kotlinx.coroutines.launch

class SafetyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SafetyRepository.getInstance(application)
    
    // Current user ID (would typically come from authentication)
    private val _currentUserId = MutableLiveData<String?>()
    val currentUserId: LiveData<String?> = _currentUserId
    
    // Current user data
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser
    
    // Emergency contacts
    private val _emergencyContacts = MutableLiveData<List<EmergencyContact>>()
    val emergencyContacts: LiveData<List<EmergencyContact>> = _emergencyContacts
    
    // Safe zones in current area
    private val _safeZones = MutableLiveData<List<SafeZone>>()
    val safeZones: LiveData<List<SafeZone>> = _safeZones
    
    // Unsafe zones in current area
    private val _unsafeZones = MutableLiveData<List<UnsafeZone>>()
    val unsafeZones: LiveData<List<UnsafeZone>> = _unsafeZones
    
    // Message history
    private val _messageHistory = MutableLiveData<List<MessageLog>>()
    val messageHistory: LiveData<List<MessageLog>> = _messageHistory
    
    // Loading state
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    
    // Error messages
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    // Set the current user ID and load user data
    fun setCurrentUserId(userId: String) {
        _currentUserId.value = userId
        loadUserData(userId)
        loadEmergencyContacts(userId)
        loadMessageHistory(userId)
    }
    
    // Load user data
    private fun loadUserData(userId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val user = repository.getUserById(userId)
                _currentUser.value = user
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load user data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    // Load emergency contacts
    private fun loadEmergencyContacts(userId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getEmergencyContacts(userId).collect { contacts ->
                    _emergencyContacts.value = contacts
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load emergency contacts: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    // Load message history
    private fun loadMessageHistory(userId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getMessageHistory(userId).collect { messages ->
                    _messageHistory.value = messages
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load message history: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    // Update user's location sharing preference
    fun setLocationSharingEnabled(enabled: Boolean) {
        val userId = _currentUserId.value ?: return
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getUserById(userId)?.let { user ->
                    val updatedUser = user.copy(isLocationSharingEnabled = enabled)
                    repository.updateUser(updatedUser)
                    _currentUser.value = updatedUser
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update location sharing: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    // Report an unsafe zone
    fun reportUnsafeZone(
        latitude: Double,
        longitude: Double,
        address: String,
        description: String? = null,
        dangerLevel: UnsafeZone.DangerLevel = UnsafeZone.DangerLevel.MEDIUM
    ) {
        val userId = _currentUserId.value ?: return
        
        val unsafeZone = UnsafeZone(
            latitude = latitude,
            longitude = longitude,
            address = address,
            reportedBy = userId,
            description = description,
            dangerLevel = dangerLevel
        )
        
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.reportUnsafeZone(unsafeZone)
                // Refresh unsafe zones in the area
                // Note: You would need to pass the current map bounds
            } catch (e: Exception) {
                _errorMessage.value = "Failed to report unsafe zone: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    // Send an SOS message
    fun sendSOS(
        message: String,
        latitude: Double?,
        longitude: Double?,
        locationName: String? = null
    ) {
        val userId = _currentUserId.value ?: return
        
        val messageLog = MessageLog(
            userId = userId,
            messageType = MessageLog.MessageType.SOS,
            content = message,
            recipients = _emergencyContacts.value?.map { it.phone } ?: emptyList(),
            status = MessageLog.MessageStatus.PENDING,
            latitude = latitude,
            longitude = longitude,
            locationName = locationName
        )
        
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val messageId = repository.logMessage(messageLog)
                
                // Create guardian acknowledgments
                _emergencyContacts.value?.forEach { contact ->
                    val ack = GuardianAck(
                        messageId = messageId,
                        guardianId = contact.id,
                        status = GuardianAck.AckStatus.RECEIVED
                    )
                    repository.logGuardianAck(ack)
                }
                
                // Refresh message history
                loadMessageHistory(userId)
                
            } catch (e: Exception) {
                _errorMessage.value = "Failed to send SOS: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    // Clear error message
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
