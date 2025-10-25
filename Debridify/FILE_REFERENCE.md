# Debridify - Complete File Reference

## 📁 Project Structure

```
Debridify/
│
├── 📄 Documentation (5 files)
│   ├── README.md ...................... Main project documentation
│   ├── QUICKSTART.md .................. 5-minute getting started guide
│   ├── SETUP_GUIDE.md ................. Detailed setup and troubleshooting
│   ├── PROJECT_SUMMARY.md ............. Complete project overview
│   └── LICENSE ........................ MIT License
│
├── 🔧 Build Configuration (3 files)
│   ├── build.gradle.kts ............... Root project configuration
│   ├── settings.gradle.kts ............ Project settings
│   └── gradle.properties .............. Gradle properties
│
└── 📱 app/
    │
    ├── 🔧 App Configuration (2 files)
    │   ├── build.gradle.kts ........... App dependencies and config
    │   └── proguard-rules.pro ......... ProGuard rules for release
    │
    └── src/main/
        │
        ├── 📄 AndroidManifest.xml ..... App manifest with permissions
        │
        ├── 💻 java/com/debridify/app/ (20 Kotlin files)
        │   │
        │   ├── 🚀 Application Layer (2 files)
        │   │   ├── DebridifyApp.kt ....... Application class
        │   │   └── MainActivity.kt ........ Main activity
        │   │
        │   ├── 📊 Data Layer (8 files)
        │   │   │
        │   │   ├── api/ (3 files)
        │   │   │   ├── RealDebridApi.kt ... Real-Debrid endpoints
        │   │   │   ├── TorBoxApi.kt ....... TorBox endpoints
        │   │   │   └── AllDebridApi.kt .... AllDebrid endpoints
        │   │   │
        │   │   ├── model/ (3 files)
        │   │   │   ├── DebridProvider.kt .. Provider enum
        │   │   │   ├── User.kt ............ User models
        │   │   │   └── Torrent.kt ......... Torrent models
        │   │   │
        │   │   ├── preferences/ (1 file)
        │   │   │   └── PreferencesManager.kt  DataStore manager
        │   │   │
        │   │   └── repository/ (1 file)
        │   │       └── DebridRepository.kt ... Unified repository
        │   │
        │   ├── 💉 Dependency Injection (1 file)
        │   │   └── di/
        │   │       └── NetworkModule.kt ..... Hilt network module
        │   │
        │   └── 🎨 UI Layer (9 files)
        │       │
        │       ├── UnchainedMultiApp.kt ..... Navigation setup
        │       │
        │       ├── screens/ (4 files)
        │       │   ├── SplashScreen.kt ...... Loading screen
        │       │   ├── ProviderSelectionScreen.kt  Provider picker
        │       │   ├── LoginScreen.kt ....... API key input
        │       │   └── HomeScreen.kt ........ Main torrents list
        │       │
        │       ├── viewmodel/ (1 file)
        │       │   └── MainViewModel.kt ..... App state management
        │       │
        │       └── theme/ (3 files)
        │           ├── Theme.kt ............. Material theme
        │           ├── Color.kt ............. Color definitions
        │           └── Type.kt .............. Typography
        │
        └── 📦 res/ (6 files)
            │
            ├── values/ (2 files)
            │   ├── strings.xml .............. String resources
            │   └── themes.xml ............... Theme definitions
            │
            └── xml/ (2 files)
                ├── data_extraction_rules.xml  Backup rules (Android 12+)
                └── backup_rules.xml ......... Legacy backup rules
```

## 📝 File Descriptions

### Documentation Files

| File | Purpose | Lines | Key Content |
|------|---------|-------|-------------|
| README.md | Main documentation | ~400 | Features, installation, usage, API details |
| QUICKSTART.md | Quick start guide | ~250 | 5-minute setup, test cases |
| SETUP_GUIDE.md | Detailed setup | ~450 | Environment setup, troubleshooting |
| PROJECT_SUMMARY.md | Project overview | ~550 | Architecture, statistics, features |
| LICENSE | MIT License | ~20 | License terms |

### Configuration Files

| File | Purpose | Technology |
|------|---------|------------|
| build.gradle.kts (root) | Project config | Gradle Kotlin DSL |
| settings.gradle.kts | Module settings | Gradle Kotlin DSL |
| gradle.properties | Build properties | Properties |
| app/build.gradle.kts | App dependencies | Gradle Kotlin DSL |
| proguard-rules.pro | Code optimization | ProGuard |

### Application Layer

| File | Lines | Purpose | Key Components |
|------|-------|---------|----------------|
| DebridifyApp.kt | ~10 | App initialization | @HiltAndroidApp |
| MainActivity.kt | ~50 | Entry point | Magnet link handling |

### Data Layer - API

| File | Lines | Purpose | Endpoints |
|------|-------|---------|-----------|
| RealDebridApi.kt | ~80 | Real-Debrid API | 8 endpoints |
| TorBoxApi.kt | ~100 | TorBox API | 6 endpoints |
| AllDebridApi.kt | ~120 | AllDebrid API | 9 endpoints |

### Data Layer - Models

| File | Lines | Purpose | Models |
|------|-------|---------|--------|
| DebridProvider.kt | ~20 | Provider enum | 3 providers |
| User.kt | ~110 | User models | 4 models + converters |
| Torrent.kt | ~180 | Torrent models | 4 models + converters |

### Data Layer - Other

| File | Lines | Purpose | Technology |
|------|-------|---------|------------|
| PreferencesManager.kt | ~70 | Settings storage | DataStore |
| DebridRepository.kt | ~170 | Data access | Repository pattern |

### Dependency Injection

| File | Lines | Purpose | Components |
|------|-------|---------|------------|
| NetworkModule.kt | ~140 | Network setup | Retrofit, OkHttp, Hilt |

### UI Layer - Screens

| File | Lines | Purpose | Components |
|------|-------|---------|------------|
| SplashScreen.kt | ~30 | Loading screen | CircularProgressIndicator |
| ProviderSelectionScreen.kt | ~90 | Provider picker | LazyColumn, Cards |
| LoginScreen.kt | ~100 | Login form | TextField, Button |
| HomeScreen.kt | ~340 | Main screen | LazyColumn, FAB, Dialogs |

### UI Layer - Other

| File | Lines | Purpose | Technology |
|------|-------|---------|------------|
| UnchainedMultiApp.kt | ~70 | Navigation | Navigation Compose |
| MainViewModel.kt | ~140 | State management | ViewModel, Flow |

### UI Layer - Theme

| File | Lines | Purpose | Content |
|------|-------|---------|---------|
| Theme.kt | ~70 | Theme setup | Material 3 theme |
| Color.kt | ~60 | Colors | Light/dark palettes |
| Type.kt | ~30 | Typography | Font styles |

### Resources

| File | Purpose | Content |
|------|---------|---------|
| strings.xml | App strings | App name |
| themes.xml | App theme | Material theme |
| data_extraction_rules.xml | Backup rules | SharedPreferences |
| backup_rules.xml | Legacy backup | SharedPreferences |
| AndroidManifest.xml | App manifest | Permissions, activities, intents |

## 📊 Statistics

| Metric | Count |
|--------|-------|
| Total Files | 35 |
| Kotlin Files | 20 |
| Configuration Files | 6 |
| Documentation Files | 5 |
| Resource Files | 6 |
| Total Lines of Code | ~4,500+ |
| API Interfaces | 3 |
| Data Models | 7 |
| Screens | 4 |
| Providers Supported | 3 |
| Dependencies | 25+ |

## 🔑 Key Files to Start With

### For Understanding Architecture
1. **MainViewModel.kt** - See how state is managed
2. **DebridRepository.kt** - See how APIs are unified
3. **NetworkModule.kt** - See how DI is set up

### For UI Customization
1. **HomeScreen.kt** - Main user interface
2. **Theme.kt** - Colors and styling
3. **Color.kt** - Color definitions

### For API Integration
1. **RealDebridApi.kt** - Example API interface
2. **User.kt** - Example data models
3. **Torrent.kt** - Complex model conversion

### For Configuration
1. **app/build.gradle.kts** - Dependencies
2. **AndroidManifest.xml** - Permissions
3. **proguard-rules.pro** - Release optimization

## 🎯 Where to Make Changes

### Add New Provider
- [ ] Update `DebridProvider.kt`
- [ ] Create new API interface in `data/api/`
- [ ] Add models in `data/model/`
- [ ] Update `DebridRepository.kt`
- [ ] Update `NetworkModule.kt`

### Change UI Theme
- [ ] Edit `Color.kt` for colors
- [ ] Edit `Theme.kt` for theme structure
- [ ] Edit `Type.kt` for fonts

### Add New Screen
- [ ] Create composable in `ui/screens/`
- [ ] Add route in `UnchainedMultiApp.kt`
- [ ] Update navigation logic

### Add New Feature
- [ ] Add API endpoint in relevant interface
- [ ] Add method in `DebridRepository.kt`
- [ ] Add action in `MainViewModel.kt`
- [ ] Update UI in relevant screen

## 📚 Import Statements Reference

### Common Imports

```kotlin
// Compose
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*

// ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

// Hilt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Coroutines
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Retrofit
import retrofit2.Response
import retrofit2.http.*
```

## 🔧 Build Commands Quick Reference

```bash
# Sync Gradle
./gradlew sync

# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on device
./gradlew installDebug

# Run tests
./gradlew test

# Run lint
./gradlew lint

# Generate documentation
./gradlew dokka
```

## 📖 Further Reading

- See **README.md** for feature details
- See **SETUP_GUIDE.md** for troubleshooting
- See **PROJECT_SUMMARY.md** for architecture
- See **QUICKSTART.md** for fast setup

---

**Total Project Size:** ~4,500 lines of code across 35 files
**Ready for:** Development, Testing, Production (after QA)
**License:** MIT - Free to use, modify, distribute
