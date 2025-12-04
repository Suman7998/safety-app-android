package com.safety.womensafetyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class EmergencyContactsAdapter(
    private val contacts: List<EmergencyContact>,
    private val onCallClick: (EmergencyContact) -> Unit,
    private val onEditClick: (EmergencyContact) -> Unit,
    private val onDeleteClick: (EmergencyContact) -> Unit
) : RecyclerView.Adapter<EmergencyContactsAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactInitial: TextView = itemView.findViewById(R.id.contactInitial)
        val contactName: TextView = itemView.findViewById(R.id.contactName)
        val contactPhone: TextView = itemView.findViewById(R.id.contactPhone)
        val callButton: MaterialButton = itemView.findViewById(R.id.callButton)
        val editButton: MaterialButton = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emergency_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        
        holder.contactInitial.text = contact.getInitial()
        holder.contactName.text = contact.name
        holder.contactPhone.text = contact.getFormattedPhone()
        
        holder.callButton.setOnClickListener {
            onCallClick(contact)
        }
        
        holder.editButton.setOnClickListener {
            onEditClick(contact)
        }
        
        holder.editButton.setOnLongClickListener {
            onDeleteClick(contact)
            true
        }
    }

    override fun getItemCount(): Int = contacts.size
}
