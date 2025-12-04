package com.safety.womensafetyapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EmergencyContactsActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var fabAddContact: FloatingActionButton
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var emptyStateLayout: View
    
    private lateinit var contactsAdapter: EmergencyContactsAdapter
    private val contactsList = mutableListOf<EmergencyContact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)
        
        initializeViews()
        setupRecyclerView()
        setupClickListeners()
        loadContacts()
        updateEmptyState()
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar)
        fabAddContact = findViewById(R.id.fabAddContact)
        contactsRecyclerView = findViewById(R.id.contactsRecyclerView)
        emptyStateLayout = findViewById(R.id.emptyStateLayout)
        
        // Setup toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        contactsAdapter = EmergencyContactsAdapter(
            contacts = contactsList,
            onCallClick = { contact -> makeCall(contact) },
            onEditClick = { contact -> showAddEditContactDialog(contact) },
            onDeleteClick = { contact -> showDeleteConfirmation(contact) }
        )
        
        contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@EmergencyContactsActivity)
            adapter = contactsAdapter
        }
    }

    private fun setupClickListeners() {
        fabAddContact.setOnClickListener {
            showAddEditContactDialog()
        }
    }

    private fun showAddEditContactDialog(contact: EmergencyContact? = null) {
        val isEditing = contact != null
        val title = if (isEditing) "Edit Contact" else "Add Emergency Contact"
        
        // Create dialog layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_contact, null)
        val nameInput = dialogView.findViewById<TextInputEditText>(R.id.nameInput)
        val phoneInput = dialogView.findViewById<TextInputEditText>(R.id.phoneInput)
        val relationshipInput = dialogView.findViewById<TextInputEditText>(R.id.relationshipInput)
        
        // Pre-fill if editing
        contact?.let {
            nameInput.setText(it.name)
            phoneInput.setText(it.phoneNumber)
            relationshipInput.setText(it.relationship)
        }
        
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = nameInput.text.toString().trim()
                val phone = phoneInput.text.toString().trim()
                val relationship = relationshipInput.text.toString().trim()
                
                if (validateInput(name, phone)) {
                    if (isEditing) {
                        updateContact(contact!!, name, phone, relationship)
                    } else {
                        addContact(name, phone, relationship)
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .apply {
                if (isEditing) {
                    setNeutralButton("Delete") { _, _ ->
                        showDeleteConfirmation(contact!!)
                    }
                }
            }
            .show()
    }

    private fun validateInput(name: String, phone: String): Boolean {
        return when {
            name.isEmpty() -> {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
                false
            }
            phone.isEmpty() -> {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
                false
            }
            phone.length < 10 -> {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun addContact(name: String, phone: String, relationship: String) {
        val newContact = EmergencyContact(
            id = System.currentTimeMillis(),
            name = name,
            phoneNumber = phone,
            relationship = relationship
        )
        
        contactsList.add(newContact)
        contactsAdapter.notifyItemInserted(contactsList.size - 1)
        updateEmptyState()
        
        Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show()
    }

    private fun updateContact(contact: EmergencyContact, name: String, phone: String, relationship: String) {
        val index = contactsList.indexOfFirst { it.id == contact.id }
        if (index != -1) {
            contactsList[index] = contact.copy(
                name = name,
                phoneNumber = phone,
                relationship = relationship
            )
            contactsAdapter.notifyItemChanged(index)
            Toast.makeText(this, "Contact updated successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmation(contact: EmergencyContact) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Contact")
            .setMessage("Are you sure you want to delete ${contact.name}?")
            .setPositiveButton("Delete") { _, _ ->
                deleteContact(contact)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteContact(contact: EmergencyContact) {
        val index = contactsList.indexOfFirst { it.id == contact.id }
        if (index != -1) {
            contactsList.removeAt(index)
            contactsAdapter.notifyItemRemoved(index)
            updateEmptyState()
            Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeCall(contact: EmergencyContact) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) 
            == PackageManager.PERMISSION_GRANTED) {
            
            try {
                val intent = Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse("tel:${contact.phoneNumber}")
                }
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Unable to make call", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Phone permission required to make calls", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadContacts() {
        // In a real app, you would load from database or shared preferences
        // For demo purposes, we'll add some sample contacts
        if (contactsList.isEmpty()) {
            contactsList.addAll(getSampleContacts())
            contactsAdapter.notifyDataSetChanged()
        }
    }

    private fun getSampleContacts(): List<EmergencyContact> {
        return listOf(
            EmergencyContact(1, "Emergency Services", "911", "Emergency"),
            EmergencyContact(2, "Mom", "555-0123", "Family"),
            EmergencyContact(3, "Best Friend", "555-0456", "Friend")
        )
    }

    private fun updateEmptyState() {
        if (contactsList.isEmpty()) {
            emptyStateLayout.visibility = View.VISIBLE
            contactsRecyclerView.visibility = View.GONE
        } else {
            emptyStateLayout.visibility = View.GONE
            contactsRecyclerView.visibility = View.VISIBLE
        }
    }
}
