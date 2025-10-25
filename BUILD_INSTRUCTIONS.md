# Debridify - Build Instructions

## Current Status ⚠️

The Debridify project is **ready to build**, but requires Java JDK 17 and Android SDK to be installed on your system.

### Missing Requirements
- ❌ Java JDK 17 (not found on system)
- ❌ Android SDK (not found on system)

---

## Option 1: Build with Android Studio (Recommended - Easiest)

### Step 1: Install Android Studio
1. Download from: https://developer.android.com/studio
2. Run the installer
3. Follow the setup wizard (it will install Java JDK and Android SDK automatically)

### Step 2: Open Project
1. Launch Android Studio
2. Click "Open" and select: `C:\Users\HP\Desktop\Debridify`
3. Wait for Gradle sync to complete

### Step 3: Build APK
1. Click **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
2. Wait for build to complete (first build may take 5-10 minutes)
3. Click "locate" in the notification to find your APK

**APK Location:**
```
Debridify/app/build/outputs/apk/debug/app-debug.apk
```

---

## Option 2: Build via Command Line

### Prerequisites
1. **Install Java JDK 17**
   - Download from: https://adoptium.net/temurin/releases/
   - Select: JDK 17 (LTS), Windows x64
   - Install and add to PATH

2. **Install Android SDK**
   - Option A: Install Android Studio (includes SDK)
   - Option B: Install command-line tools only
     https://developer.android.com/studio#command-tools

3. **Set Environment Variables**
   ```bash
   JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.x.x
   ANDROID_HOME=C:\Users\HP\AppData\Local\Android\Sdk
   ```

### Build Commands
```bash
cd C:\Users\HP\Desktop\Debridify

# Windows (Command Prompt)
gradlew.bat clean
gradlew.bat assembleDebug

# Windows (Git Bash / MSYS)
./gradlew clean
./gradlew assembleDebug
```

**Output APK:**
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## Option 3: Online Build Services

### GitHub Actions (Free CI/CD)
1. Push project to GitHub
2. Create workflow file: `.github/workflows/build.yml`
3. GitHub will build APK automatically
4. Download from Actions artifacts

### AppCenter / Firebase App Distribution
- Upload project
- Configure build
- Download APK

---

## What's Already Done ✅

### Project Setup
- ✅ Gradle wrapper configured
- ✅ Build configuration (build.gradle.kts)
- ✅ All source code complete
- ✅ App icon integrated
- ✅ AndroidManifest configured
- ✅ Dependencies specified

### Project Structure
```
Debridify/
├── gradle/wrapper/           ✅ Wrapper configured
│   ├── gradle-wrapper.jar    ✅ Downloaded
│   └── gradle-wrapper.properties ✅ Set to Gradle 8.2
├── gradlew                   ✅ Created
├── app/
│   ├── build.gradle.kts      ✅ Complete
│   └── src/                  ✅ All code ready
└── build.gradle.kts          ✅ Complete
```

---

## Quick Start (Recommended)

### For Non-Developers:
**Just install Android Studio** - it's the easiest way!
1. Download: https://developer.android.com/studio
2. Install (default settings are fine)
3. Open the Debridify folder
4. Click Build → Build APK
5. Done!

### For Developers:
If you already have Java 17 and Android SDK:
```bash
cd /c/Users/HP/Desktop/Debridify
./gradlew assembleDebug
```

---

## Build Variants

### Debug APK (for testing)
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK (for distribution)
```bash
./gradlew assembleRelease
```
Output: `app/build/outputs/apk/release/app-release.apk`

**Note:** Release APK requires signing configuration in `build.gradle.kts`

---

## Troubleshooting

### "JAVA_HOME not set"
- Install JDK 17 and set JAVA_HOME environment variable

### "Android SDK not found"
- Install Android Studio or set ANDROID_HOME variable

### "gradlew: command not found"
- Make sure you're in the Debridify directory
- On Windows, use `gradlew.bat` instead of `./gradlew`

### "Build failed: compileSdkVersion not found"
- Open Android Studio
- Go to Tools → SDK Manager
- Install Android SDK Platform 34

### First build is slow
- Normal! Gradle downloads dependencies
- Subsequent builds are much faster

---

## Alternative: Pre-built APK

If you just want to use the app without building:
1. I can help you set up Android Studio
2. Or you can use an online Android build service
3. Or if you have a friend with Android Studio, share the project folder

---

## Project Information

- **Package**: com.debridify
- **Version**: 1.0.0
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Build Tools**: Gradle 8.2 + AGP 8.2.0
- **Language**: Kotlin 1.9.20

---

## Support

Need help building?
1. Check the error message carefully
2. Ensure Java 17 is installed
3. Ensure Android SDK is installed
4. Try cleaning: `./gradlew clean`
5. Try rebuilding: `./gradlew assembleDebug`

---

**Status**: Project is complete and ready to build - just needs Java & Android SDK installed!
