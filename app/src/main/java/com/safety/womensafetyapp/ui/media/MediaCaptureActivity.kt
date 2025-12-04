package com.safety.womensafetyapp.ui.media

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.safety.womensafetyapp.R

class MediaCaptureActivity : AppCompatActivity() {

    private var photoUri: Uri? = null
    private var videoUri: Uri? = null

    // Permissions we may need at runtime
    private val neededPermissions: Array<String> by lazy {
        val perms = mutableListOf(Manifest.permission.CAMERA)
        // Microphone is required for video and audio recording
        perms += Manifest.permission.RECORD_AUDIO
        // For Android < 29, writing to external storage requires this
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            perms += Manifest.permission.WRITE_EXTERNAL_STORAGE
        }
        perms.toTypedArray()
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            val allGranted = results.values.all { it }
            if (!allGranted) {
                Toast.makeText(this, "Permissions are required to capture media", Toast.LENGTH_LONG).show()
            }
        }

    private val imageCaptureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && photoUri != null) {
                Toast.makeText(this, "Photo saved to gallery", Toast.LENGTH_SHORT).show()
            } else {
                // Clean up empty entry if capture cancelled
                photoUri?.let { contentResolver.delete(it, null, null) }
                photoUri = null
                Toast.makeText(this, "Photo capture cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private val videoCaptureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && videoUri != null) {
                Toast.makeText(this, "Video saved to gallery", Toast.LENGTH_SHORT).show()
            } else {
                videoUri?.let { contentResolver.delete(it, null, null) }
                videoUri = null
                Toast.makeText(this, "Video capture cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private val audioRecordLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this, "Audio saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Audio recording cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_capture)

        // Ask for runtime permissions if needed
        requestMissingPermissions()

        findViewById<MaterialButton>(R.id.btnCapturePhoto).setOnClickListener {
            if (ensurePermissions()) capturePhoto()
        }
        findViewById<MaterialButton>(R.id.btnRecordVideo).setOnClickListener {
            if (ensurePermissions()) recordVideo()
        }
        findViewById<MaterialButton>(R.id.btnRecordAudio).setOnClickListener {
            if (ensurePermissions()) recordAudio()
        }
    }

    private fun requestMissingPermissions() {
        val missing = neededPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing.isNotEmpty()) {
            permissionLauncher.launch(missing.toTypedArray())
        }
    }

    private fun ensurePermissions(): Boolean {
        val missing = neededPermissions.firstOrNull {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing != null) {
            requestMissingPermissions()
            return false
        }
        return true
    }

    private fun capturePhoto() {
        val filename = "IMG_${System.currentTimeMillis()}"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$filename.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 0)
            }
        }
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri == null) {
            Toast.makeText(this, "Unable to create image entry", Toast.LENGTH_SHORT).show()
            return
        }
        photoUri = uri
        val intent = android.content.Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }
        imageCaptureLauncher.launch(intent)
    }

    private fun recordVideo() {
        val filename = "VID_${System.currentTimeMillis()}"
        val values = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, "$filename.mp4")
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Video.Media.IS_PENDING, 0)
            }
        }
        val uri = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
        if (uri == null) {
            Toast.makeText(this, "Unable to create video entry", Toast.LENGTH_SHORT).show()
            return
        }
        videoUri = uri
        val intent = android.content.Intent(MediaStore.ACTION_VIDEO_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, uri)
            // Optional: limit duration/quality
            putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30) // seconds
            putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)   // high quality
        }
        videoCaptureLauncher.launch(intent)
    }

    private fun recordAudio() {
        // Use system recorder; most devices save into MediaStore and return a content Uri
        val intent = android.content.Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
        audioRecordLauncher.launch(intent)
    }
}
