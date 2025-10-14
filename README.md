# 🛠️ QRCraft

QRCraft is a modern, multiplatform QR code scanning and generation app built with Compose Multiplatform
for Android and iOS. Designed with clean architecture and the MVI pattern, QRCraft
delivers a fast, intuitive experience for both scanning and creating QR codes—making it your
all-in-one QR toolkit.

---

## 🚀 Features

- **📷 Live QR Code Scanner**
    - Real-time camera view with instant QR code detection.
    - Seamless camera permission handling.
    - Result screen with options to copy or share scanned data.

- **✏️ QR Code Generator**
    - Create custom QR codes for links, text, contacts, and more.
    - Simple, guided flow: select type, input data, generate, and share instantly.
    - Fully adaptive UI for all screen sizes.

- **🕒 Scan & Generate History**
    - Unified history screen for all scanned and created QR codes.
    - Revisit, copy, share, or re-open previous results.
    - Dynamic sync with user actions, responsive layout across devices.

- **⭐ Advanced QR Utilities**
    - Scan QR codes from images on your device.
    - Mark codes as favorites for quick access.
    - Save scanned or generated codes to local storage.

---

## 🏗️ Tech Stack & Architecture

- **Kotlin + Jetpack Compose (Multiplatform UI)**
- **MVI Architecture + Clean Architecture**
- **Modular, multi-layered codebase**
- **Unidirectional Data Flow**
- **State management with StateFlow**
- **Manual Dependency Injection (no 3rd party DI)**
- **SOLID, DRY, KISS principles**
- **Responsive/adaptive design across Android, Desktop, tablets, and more**

---

## 🖼️ Screenshots

- Main Scanner Screen
- Scan Result Screen
- QR Code Generator (Type Selection, Data Input, Result)
- History Screen
- QR from Image
- Favorites
- Save to Device

_All screenshots are in the `screenshots/` folder. Each file name matches the screen title._

---

## 🧠 What You'll Learn

- Implementing live camera preview and QR code detection with Compose Multiplatform.
- Building custom QR code generation flows.
- State management and MVI pattern in multiplatform projects.
- Handling permissions and platform-specific features in Compose.
- Modularization and clean project design for maintainability.
- Best practices for adaptive UI across platforms.

---

## 🛠️ Getting Started

Clone the repository:

```bash
git clone https://github.com/steve1rm/QRCraft.git
```

1. Open in Android Studio (Giraffe or newer) or compatible IDE for Compose Multiplatform.
2. Sync Gradle and Run on your preferred device (Android, Desktop, etc.).

---

## 📄 License

This project is open-source and free to use. Attribution is appreciated!  
See [LICENSE](LICENSE) for full details.

---

**QRCraft: Scan, generate, and manage QR codes—anywhere, anytime, on any device.**
