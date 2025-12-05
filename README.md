# Women's Safety App

A comprehensive Android application designed to enhance women's safety through emergency features, location sharing, and safety resources.

## 📸 Application Screenshots

### 🔥 Core & AI Assisted Features
| AI Alerts & Notifications | AI Chat Bot | App Launch Screen | Main Dashboard |
|---|---|---|---|
| <img src="assets/screenshots/ai_alerts.png.jpg" width="200"/> | <img src="assets/screenshots/ai_chatbot.png.jpg" width="200"/> | <img src="assets/screenshots/app_start_symbol.png.jpg" width="200"/> | <img src="assets/screenshots/dashboard.png.jpg" width="200"/> |

### 📂 Safety Navigation & Emergency Panels
| Safety Filters / Data View | Safe Zones List | Emergency Contacts | Emergency SOS Trigger |
|---|---|---|---|
| <img src="assets/screenshots/data_viewer_list_filters.png.jpg" width="200"/> | <img src="assets/screenshots/data_viewer_list_safe_zones.jpg" width="200"/> | <img src="assets/screenshots/emergency_contacts.png.jpg" width="200"/> | <img src="assets/screenshots/emergency_sos.png.jpg" width="200"/> |

### 🔥 Firebase & Cloud Connectivity
| Firebase Authentication | Firebase Cloud Messaging | Firebase Project Config | Firebase Users Panel |
|---|---|---|---|
| <img src="assets/screenshots/firebase_authentication.png" width="200"/> | <img src="assets/screenshots/firebase_cloud_messaging.png" width="200"/> | <img src="assets/screenshots/firebase_project_details.png" width="200"/> | <img src="assets/screenshots/firebase_users.png" width="200"/> |

### 📍 Location & Tracking
| Live Location Sharing | Login Screen | Maps Navigation | ML BERT Detection |
|---|---|---|---|
| <img src="assets/screenshots/location_sharing.png.jpg" width="200"/> | <img src="assets/screenshots/login.png.jpg" width="200"/> | <img src="assets/screenshots/maps_location.png.jpg" width="200"/> | <img src="assets/screenshots/ml_bert_model.png.jpg" width="200"/> |

### 🎥 Media, Guidance & Safety Education
| Multimedia Access | Alert Notifications | Online Safety Tips | Personal Protection Tips |
|---|---|---|---|
| <img src="assets/screenshots/multimedia.png.jpg" width="200"/> | <img src="assets/screenshots/notifications.png.jpg" width="200"/> | <img src="assets/screenshots/online_safety_measures.png.jpg" width="200"/> | <img src="assets/screenshots/personal_safety_measures.png.jpg" width="200"/> |

### 🧾 Registration + Travel Guidance + UI
| Sign Up Screen | Safety Guidelines | Travel Tips | App UI Design |
|---|---|---|---|
| <img src="assets/screenshots/registration.png.jpg" width="200"/> | <img src="assets/screenshots/safety_measures.png.jpg" width="200"/> | <img src="assets/screenshots/travel_safety.png.jpg" width="200"/> | <img src="assets/screenshots/ui_interface.png.jpg" width="200"/> |


## Features

### Emergency SOS
- Panic Button: Long-press emergency button with 3-second countdown
- Automatic Emergency Calls: Direct calling to emergency services
- Emergency SMS: Automatic SMS alerts to emergency contacts
- Location Sharing: Instant location sharing during emergencies

### 📞 Emergency Contacts Management
- Add/Edit Contacts: Manage trusted emergency contacts
- Quick Call: One-tap calling to emergency contacts
- Contact Validation: Phone number validation and formatting
- Sample Contacts: Pre-loaded emergency service numbers

### 📍 Location Sharing
- Real-time Location: Get and share current location
- Live Tracking: Share live location for specified durations
- Emergency Location Share: Automatic location sharing during SOS
- Google Maps Integration: Location sharing via Google Maps links

### 💡 Safety Tips & Resources
- Personal Safety: Tips for staying safe in public spaces
- Online Safety: Protection from cyber threats and harassment
- Travel Safety: Guidelines for safe travel and transportation
- Workplace Safety: Handling workplace harassment and boundaries
- Emergency Numbers: Quick access to important emergency contacts

### ⚙️ Settings & Profile Management
- User Profile: Manage personal information and emergency message
- Notification Settings: Control app notifications and alerts
- Privacy Controls: Manage data collection and crash reporting
- App Information: Version info, privacy policy, and support

## Technical Specifications

### Requirements
- Minimum SDK: Android 7.0 (API level 24)
- Target SDK: Android 14 (API level 34)
- Language: Kotlin
- Architecture: MVVM with Material Design 3

### Permissions Required
- `ACCESS_FINE_LOCATION` - For location services
- `ACCESS_COARSE_LOCATION` - For approximate location
- `CALL_PHONE` - For emergency calling
- `SEND_SMS` - For emergency messaging
- `READ_CONTACTS` - For contact integration
- `INTERNET` - For emergency services
- `VIBRATE` - For alert notifications
- `CAMERA` - For emergency recording (future feature)
- `RECORD_AUDIO` - For emergency recording (future feature)

### Dependencies
- AndroidX Core: 1.10.1
- Material Design: 1.9.0
- ConstraintLayout: 2.1.4
- Navigation Components: 2.6.0
- Lifecycle Components: 2.6.1
- RecyclerView: 1.3.0
- Google Play Services Location: 21.0.1
- Google Play Services Maps: 18.1.0

## Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK with API level 24+
- Google Play Services

### Setup Instructions

1. Clone the Repository
   ```bash
   git clone https://github.com/Suman7998/women-safety-app.git
   cd women-safety-app
   ```

2. Open in Android Studio
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and select it

3. Sync Project
   - Android Studio will automatically sync Gradle files
   - Wait for the sync to complete

4. Build and Run
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Ctrl+R (Cmd+R on Mac)

### Configuration

1. Google Services (Optional for enhanced location features)
   - Add your `google-services.json` file to the `app/` directory
   - Enable Google Maps API in Google Cloud Console

2. Emergency Numbers
   - Update emergency numbers in `SafetyTipsActivity.kt` for your region
   - Modify default emergency contacts in `EmergencyContactsActivity.kt`

## Usage Guide

### Getting Started
1. First Launch: Grant necessary permissions when prompted
2. Setup Profile: Go to Settings and add your name and emergency message
3. Add Contacts: Add trusted emergency contacts via the Emergency Contacts screen
4. Test Features: Familiarize yourself with the SOS button and location sharing

### Emergency Usage
1. SOS Activation: Long-press the red emergency button for 3 seconds
2. Cancel if Needed: Tap "Cancel" in the dialog if activated accidentally
3. Location Sharing: Use "Emergency Share" for immediate location alerts

### Safety Tips
- Regularly update your emergency contacts
- Test the app features periodically
- Keep your phone charged and location services enabled
- Share your safety plan with trusted friends and family

## Security & Privacy

### Data Protection
- Local Storage: User data stored locally using SharedPreferences
- No Cloud Sync: Emergency contacts remain on device only
- Minimal Permissions: Only essential permissions requested
- Privacy Controls: User control over data collection and crash reporting

### Security Features
- Permission Validation: Runtime permission checks
- Input Validation: Phone number and contact validation
- Secure Communication: HTTPS for any external communications
- Code Obfuscation: ProGuard rules for release builds

## Contributing

### Development Guidelines
1. Follow Kotlin coding standards
2. Use Material Design 3 components
3. Implement proper error handling
4. Add comments for complex logic
5. Test on multiple device sizes and Android versions

### Pull Request Process
1. Fork the repository
2. Create a feature branch
3. Make your changes with proper testing
4. Submit a pull request with detailed description

## Testing

### Manual Testing Checklist
- [ ] App launches without crashes
- [ ] All navigation works correctly
- [ ] Permissions are requested appropriately
- [ ] SOS button functions properly
- [ ] Emergency contacts can be added/edited/deleted
- [ ] Location sharing works
- [ ] Settings save and load correctly
- [ ] All safety tips display properly

### Device Testing
- Test on various screen sizes (phone, tablet)
- Test on different Android versions (API 24+)
- Test with and without location services
- Test with and without internet connection

## Troubleshooting

### Common Issues

App crashes on startup
- Check if all required permissions are granted
- Verify Google Play Services is installed and updated

Location not working
- Ensure location permissions are granted
- Check if location services are enabled in device settings
- Verify GPS is turned on

Emergency calls not working
- Confirm phone permission is granted
- Check if device has cellular service
- Verify emergency numbers are correct for your region

Contacts not saving
- Check available storage space
- Restart the app and try again
- Verify input validation requirements are met

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, bug reports, or feature requests:
- Email: support@womensafetyapp.com
- Issues: Create an issue on GitHub
- Documentation: Check this README and code comments

## Disclaimer

This app is designed to assist in emergency situations but should not be considered a replacement for professional emergency services or personal safety training. Users should always follow local emergency procedures and contact appropriate authorities when needed.

## Acknowledgments

- Material Design team for UI components
- Google for location services and maps integration
- Android development community for best practices
- Safety organizations for guidance on women's safety features

---

Version: 1.0.0  
Last Updated: December 2025 
Minimum Android Version: 7.0 (API 24)  
Target Android Version: 14 (API 34)
