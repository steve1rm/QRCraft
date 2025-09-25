# QRCraft - QR Code Scanning and Generation App

QRCraft is a Kotlin Multiplatform (KMP) mobile application using Jetpack Compose for QR code scanning and generation. It targets Android and iOS platforms with a shared codebase and follows clean architecture principles.

**ALWAYS reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.**

## Working Effectively

### Prerequisites and Environment Setup
- **Java Version**: Requires Java 17+ (OpenJDK 17 recommended)
  - Check with: `java -version`
  - Install if needed: `sudo apt-get update && sudo apt-get install openjdk-17-jdk`
- **Android SDK**: Required for Android builds
  - Set `ANDROID_HOME` and `ANDROID_SDK_ROOT` to Android SDK location
  - Add `$ANDROID_HOME/cmdline-tools/latest/bin` and `$ANDROID_HOME/platform-tools` to PATH
  - Install Android SDK Build Tools 34.0.0+ and Platform Tools
- **Network Access**: **CRITICAL** - Requires unrestricted access to Google Maven repository (dl.google.com) and Maven Central for dependency resolution
  - **KNOWN LIMITATION**: Build will fail in network-restricted environments due to inability to download Android Gradle Plugin and dependencies

### Initial Setup and Build
- **NEVER CANCEL** build commands - they may take 10-45 minutes. Set timeout to 60+ minutes.
- Bootstrap and build the project:
  ```bash
  # Clean any previous build state
  ./gradlew clean
  
  # Build the project - NEVER CANCEL: Takes 15-45 minutes
  ./gradlew build
  
  # For Android-specific builds
  ./gradlew :composeApp:assembleDebug
  ```

### Running Tests
- **NEVER CANCEL** test commands - they may take 5-15 minutes. Set timeout to 30+ minutes.
- Run all tests:
  ```bash
  # Run common tests - takes 5-10 minutes
  ./gradlew test
  
  # Run Android-specific tests
  ./gradlew :composeApp:testDebugUnitTest
  ```

### Running the Application
- **Android**:
  ```bash
  # Install on connected device/emulator
  ./gradlew :composeApp:installDebug
  
  # Or build APK for manual installation
  ./gradlew :composeApp:assembleDebug
  # APK location: composeApp/build/outputs/apk/debug/composeApp-debug.apk
  ```
- **iOS**: 
  ```bash
  # Build iOS framework (requires macOS with Xcode)
  ./gradlew :composeApp:assembleXCFramework
  
  # Then open iosApp/iosApp.xcodeproj in Xcode to build/run
  ```

## Validation

### Manual Testing Requirements
After making changes, **ALWAYS** test the following complete user scenarios:
1. **QR Code Scanning Flow**:
   - Launch app → Navigate to Scan tab
   - Grant camera permission when prompted
   - Point camera at QR code and verify successful scan
   - Check scan result screen displays correctly with parsed content
   - Test back navigation to scan screen
2. **QR Code Generation Flow**:
   - Navigate to Create QR tab
   - Select a QR type (text, URL, contact, etc.)
   - Enter content and generate QR code
   - Verify QR preview displays correctly
   - Test sharing functionality

### Build Validation
- Always run before committing:
  ```bash
  # Lint check
  ./gradlew lint
  
  # Full build verification - NEVER CANCEL: Takes 15-45 minutes
  ./gradlew clean build
  ```

### Known Build Issues
- **Network Dependencies**: Build requires access to Google Maven repository. In network-restricted environments, you may see:
  ```
  Plugin [id: 'com.android.application', version: 'X.X.X'] was not found
  ```
  This indicates network access to dl.google.com is blocked.
- **Android Gradle Plugin Version**: Currently configured for AGP 7.4.2. If build fails, try updating to latest stable version in `gradle/libs.versions.toml`

## Project Structure and Navigation

### Key Directories
- **`composeApp/`**: Main application module
  - **`src/commonMain/`**: Shared Kotlin/Compose code for all platforms
  - **`src/androidMain/`**: Android-specific implementations
  - **`src/iosMain/`**: iOS-specific implementations
  - **`src/commonTest/`**: Shared test code
- **`iosApp/`**: iOS native wrapper project
- **`gradle/`**: Build configuration
  - **`libs.versions.toml`**: Version catalog for all dependencies

### Important Source Files
- **`composeApp/src/commonMain/kotlin/me/androidbox/qrcraft/`**:
  - **`App.kt`**: Main app composable with navigation scaffold and bottom bar
  - **`MainActivity.kt`** (Android): Android activity entry point with splash screen
  - **`features/`**: Feature modules organized by domain:
    - **`scan_result/`**: QR scan result processing and display (Clean Architecture: data/, domain/, presentation/)
    - **`create_qr/choose_type/`**: QR code generation with type selection
  - **`scanning/`**: QR camera scanning functionality with CameraX integration
  - **`navigation/`**: Type-safe navigation with sealed class routes (`QrCraftNavGraph.kt`)
  - **`core/`**: Shared utilities, Koin DI setup, Material3 design system components
  - **`permissions/`**: Camera permission handling for multiplatform

### QR Code Types Supported
The app supports scanning and generating these QR code types:
- **TEXT**: Plain text content
- **LINK**: URLs and web links  
- **CONTACT**: vCard contact information (name, email, phone)
- **PHONE_NUMBER**: Phone numbers with tel: prefix
- **GEOLOCATION**: GPS coordinates (latitude, longitude)
- **WIFI**: WiFi network credentials (SSID, password, encryption type)

### Dependencies and Tech Stack
- **UI**: Jetpack Compose Multiplatform 1.8.2, Material Design 3
- **Architecture**: Clean Architecture with MVI pattern, feature-based modules
- **DI**: Koin 4.1.0 dependency injection with ViewModels
- **Navigation**: Navigation Compose 2.8.0-alpha13 with type-safe Serializable routes
- **Camera**: CameraX 1.5.0-beta02 (Android), CameraK 0.0.12, Accompanist Permissions 0.37.3
- **QR Processing**: ZXing core 3.5.3, QR Scanner Plugin 0.0.8, QR Kit 3.0.7
- **Logging**: Kermit 2.0.6 multiplatform logging
- **Storage**: DataStore 1.1.1 for preferences
- **Images**: Coil 3.3.0 with SVG support for icons
- **Layout**: ConstraintLayout Compose Multiplatform 0.6.0
- **Permissions**: Moko Permissions 0.18.0 for multiplatform camera access

## Common Development Tasks

### Adding New Features
1. Create feature module under `composeApp/src/commonMain/kotlin/me/androidbox/qrcraft/features/`
2. Follow existing clean architecture pattern: `data/`, `domain/`, `presentation/`
3. Add navigation route to `navigation/QrCraftNavGraph.kt` as `@Serializable` sealed interface
4. Update DI modules in `core/di/Modules.kt` for ViewModels and dependencies
5. **IMPORTANT**: Always check and update these files when adding features:
   - `App.kt`: Update bottom bar visibility logic and navigation handling
   - `QrCraftNavigation.kt`: Add new route handling
   - Feature-specific ViewModels in Koin modules

### Adding New QR Types
1. Add new type to `QRContentType` enum in `features/scan_result/domain/QRContentType.kt`
2. Update `CreateQRDetail` sealed class in `features/create_qr/choose_type/model/CreateQRDetail.kt`
3. Add corresponding `toQRCodeString()` formatting logic
4. Add display name and SVG icon resources
5. Update QR content detection logic in `QRContentTypeDetector.kt`

### Dependency Management
- All versions defined in `gradle/libs.versions.toml`
- Add new dependencies to version catalog first, then reference in build files
- **Common libraries**:
  - UI: `implementation(compose.material3)`
  - Navigation: `implementation(libs.navigation.compose)`
  - DI: `implementation(libs.koin.compose)`

### Debugging Common Issues
- **Camera Permission**: 
  - Android: Check `AndroidManifest.xml` has `CAMERA` permission and `camera` hardware feature
  - Runtime: Verify Accompanist/Moko permissions are properly requested in scanning screen
- **QR Scanning Issues**: 
  - Verify `CameraX` preview is working with `ScanningSurfaceWithCorners.kt`
  - Check QR detection via ZXing core and QR scanner plugin integration
  - Test different QR code formats and ensure `QRContentTypeDetector.kt` handles them
- **Navigation Issues**: 
  - Check route definitions in `QrCraftNavGraph.kt` use proper `@Serializable` annotations
  - Ensure `QRContentType` and scan data are properly serialized for navigation
  - Verify back stack handling in main `App.kt` scaffold
- **Build Issues**: 
  - Clean build and check network connectivity to Google Maven and Maven Central
  - Verify Android Gradle Plugin version compatibility with Kotlin 2.2.0 and Compose 1.8.2
- **DI Issues**: 
  - Check Koin modules in `core/di/Modules.kt` are properly configured
  - Verify ViewModels are injected correctly with proper parameters (e.g., `QRContentType`)

### Performance Considerations
- **Build Time**: Clean builds take 15-45 minutes due to multiplatform setup and dependency resolution
- **APK Size**: Monitor size with dependency changes, especially camera and QR libraries
- **Camera Performance**: Test on various devices for camera preview responsiveness

## Time Expectations
- **Clean Build**: 15-45 minutes (NEVER CANCEL - set 60+ minute timeout)
- **Incremental Build**: 2-10 minutes
- **Test Suite**: 5-15 minutes (NEVER CANCEL - set 30+ minute timeout)
- **Dependency Download**: 5-20 minutes on first build
- **APK Generation**: 5-15 minutes

Remember: **NEVER CANCEL long-running builds or tests**. This is a complex multiplatform project with many dependencies that require time to download and compile.

## Common Command Outputs

The following are outputs from frequently run commands. Reference them instead of viewing, searching, or running bash commands to save time.

### Repository Root Structure
```
ls -la [repo-root]
.git/
.gitignore
LICENSE
README.md
build.gradle.kts
composeApp/
gradle/
gradle.properties
gradlew
gradlew.bat
iosApp/
settings.gradle.kts
```

### Key Configuration Files

#### gradle/libs.versions.toml (version catalog)
```toml
[versions]
agp = "7.4.2"
android-compileSdk = "36"
android-minSdk = "24" 
android-targetSdk = "35"
composeMultiplatform = "1.8.2"
kotlin = "2.2.0"
koin = "4.1.0"
# ... additional versions
```

#### composeApp/build.gradle.kts (main targets)
```kotlin
kotlin {
    androidTarget { /* Android configuration */ }
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { /* iOS targets */ }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(libs.koin.compose)
            implementation(libs.navigation.compose)
            // ... QR and camera dependencies
        }
    }
}
```

### Project Structure Overview
```
composeApp/src/
├── commonMain/kotlin/me/androidbox/qrcraft/
│   ├── App.kt                    # Main app scaffold
│   ├── Platform.kt               # Platform detection
│   ├── bottom_bar/               # Custom bottom navigation
│   ├── core/
│   │   ├── di/Modules.kt        # Koin dependency injection
│   │   ├── presentation/         # Design system components
│   │   └── utils/               # Shared utilities
│   ├── features/
│   │   ├── scan_result/         # QR scan result processing
│   │   │   ├── data/
│   │   │   ├── domain/          # QRContentType definitions
│   │   │   └── presentation/
│   │   └── create_qr/          # QR code generation
│   │       └── choose_type/    # QR type selection
│   ├── navigation/              # Type-safe navigation
│   ├── permissions/             # Camera permission handling
│   └── scanning/               # QR scanning with camera
├── androidMain/kotlin/me/androidbox/qrcraft/
│   └── MainActivity.kt         # Android entry point
├── iosMain/kotlin/me/androidbox/qrcraft/
└── commonTest/kotlin/me/androidbox/qrcraft/
    └── ComposeAppCommonTest.kt  # Sample test
```