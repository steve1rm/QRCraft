# 📱 QRCraft

**QRCraft** is a modern, powerful QR code scanner and generator built with Kotlin Multiplatform and Jetpack Compose. Create custom QR codes, scan instantly, and manage your QR history - all in a beautiful, native mobile experience for Android and iOS.

---

## ✨ Features

- **📸 Lightning-Fast Scanning**
    - Instant QR code recognition with live camera preview
    - Automatic content type detection (URLs, contacts, WiFi, and more)
    - Visual scanning feedback with corner highlights
    
- **🎨 Powerful QR Generation**
    - Create QR codes for multiple content types:
        - 📝 Plain text
        - 🔗 URLs and web links
        - 👤 Contact cards (vCard)
        - 📞 Phone numbers
        - 📍 GPS locations
        - 📡 WiFi credentials
    - Customizable QR code preview
    - One-tap sharing to any app

- **📚 Smart History**
    - Automatic scan history tracking
    - Quick access to previously scanned codes
    - Search and filter your QR collection

- **🎯 Beautiful Design**
    - Material Design 3 with dynamic theming
    - Smooth animations and transitions
    - Dark mode support
    - Intuitive bottom navigation

---

## 🖼️ Screenshots

| ![Home Screen](screenshots/home_screen.png) | ![QR Result](screenshots/qr_result_screen.png) |
|:-------------------------------------------:|:----------------------------------------------:|
|                *Home Screen*                |              *Scan Result View*                |

| ![Create QR](screenshots/screen_new_qr_screen.png) | ![Link QR](screenshots/create_new_link_qr_screen.png) |
|:--------------------------------------------------:|:-----------------------------------------------------:|
|              *Create New QR Code*                  |                *Generate Link QR*                     |

| ![History](screenshots/history_screen.png) |
|:------------------------------------------:|
|            *Scan History*                  |

> _Find all screenshots in the [`screenshots/`](screenshots) folder._

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest stable version)
- Java 17 or higher
- For iOS builds: Xcode on macOS

### Installation

1. **Clone the repository:**
```bash
   git clone https://github.com/steve1rm/QRCraft.git
   cd QRCraft
```

2. **Open in Android Studio**
   - Open the project folder
   - Wait for Gradle sync to complete

3. **Run on Android:**
```bash
   ./gradlew :composeApp:installDebug
```

4. **Run on iOS:** (macOS only)
   - Open `iosApp/iosApp.xcodeproj` in Xcode
   - Select your target device/simulator
   - Build and run

---

## 🎯 Supported QR Types

QRCraft intelligently detects and handles:

- 📄 **Text** - Plain text content
- 🌐 **URLs** - Web links and deep links
- 👥 **Contacts** - vCard format with name, phone, email
- ☎️ **Phone Numbers** - Direct dial support
- 🗺️ **Locations** - GPS coordinates (latitude, longitude)
- 📶 **WiFi** - Network credentials (SSID, password, encryption)

---

## 🛠️ Built With

- **Kotlin Multiplatform** - Share code across Android & iOS
- **Jetpack Compose Multiplatform** - Modern declarative UI
- **Material Design 3** - Beautiful, adaptive theming
- **CameraX** - Advanced camera functionality
- **ZXing** - Industry-standard QR processing
- **Koin** - Dependency injection
- **Clean Architecture** - Scalable, maintainable codebase

---

## 🤝 Contributing

Contributions are welcome! Feel free to:
- Report bugs
- Suggest new features
- Submit pull requests

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

⭐ **Found this useful? Give it a star!**
