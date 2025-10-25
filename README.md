# Debridify - Universal Debrid Manager

A modern Android application that provides unified access to multiple debrid services including **Real-Debrid**, **TorBox**, and **AllDebrid**. Built with Kotlin, Jetpack Compose, and Material Design 3.

## Features

### 🎯 Multi-Provider Support
- **Real-Debrid** - Full API integration
- **TorBox** - Complete torrent and download management
- **AllDebrid** - Magnet and link unlocking support

### ⚡ Core Functionality
- Add and manage magnet links across all providers
- View active torrents with real-time progress
- Delete torrents from your account
- User account information display
- Automatic magnet link handling from other apps
- Unified interface across all providers

### 🎨 Modern UI
- Material Design 3
- Dynamic color theming (Android 12+)
- Dark and light themes
- Smooth animations and transitions
- Responsive design

### 🏗️ Architecture
- MVVM (Model-View-ViewModel) pattern
- Clean Architecture principles
- Dependency Injection with Hilt
- Kotlin Coroutines and Flow
- Retrofit for networking
- DataStore for preferences

## Screenshots

The app provides a clean, intuitive interface for managing your debrid services:
- Provider selection screen
- Login with API key
- Torrents list with progress indicators
- User account information
- Add magnet dialog

## Installation

### Prerequisites
- Android Studio Arctic Fox (2021.1.1) or newer
- Android SDK 24+ (Android 7.0 Nougat)
- Kotlin 1.9.20+

### Building from Source

1. Clone the repository:
```bash
git clone https://github.com/yourusername/unchained-multi.git
cd unchained-multi
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Build and run on your device or emulator

### APK Installation

Download the latest APK from the [Releases](releases) page and install it on your Android device.

## Usage

### Initial Setup

1. **Launch the app** - You'll be greeted with a provider selection screen
2. **Choose your provider** - Select from Real-Debrid, TorBox, or AllDebrid
3. **Enter API key** - Input your API key from your chosen provider's account settings

### Getting API Keys

#### Real-Debrid
1. Go to https://real-debrid.com
2. Login to your account
3. Navigate to Account Settings > API Token
4. Copy your API key

#### TorBox
1. Go to https://torbox.app
2. Login to your account
3. Navigate to Settings > API
4. Generate and copy your API key

#### AllDebrid
1. Go to https://alldebrid.com
2. Login to your account
3. Navigate to Tools > API Keys
4. Create a new API key and copy it

### Managing Torrents

#### Adding Magnets
- Tap the floating "Add Magnet" button
- Paste your magnet link
- Tap "Add"

Alternatively, share a magnet link from another app and choose "Debridify"

#### Viewing Progress
- All active torrents are displayed on the home screen
- Progress bars show download status
- Download speeds are displayed for active downloads

#### Deleting Torrents
- Tap the delete icon on any torrent card
- Confirm deletion in the dialog

### Switching Providers

1. Tap the menu icon (three dots) in the top right
2. Select "Logout"
3. Choose a different provider
4. Enter the API key for the new provider

## API Integration Details

### Real-Debrid API
- Base URL: `https://api.real-debrid.com/rest/1.0/`
- Authentication: Bearer token
- Endpoints: User info, torrents list, add magnet, delete torrent

### TorBox API
- Base URL: `https://api.torbox.app/v1/api/`
- Authentication: API key as query parameter
- Endpoints: User info, torrents list, create torrent, control torrent

### AllDebrid API
- Base URL: `https://api.alldebrid.com/v4/`
- Authentication: Bearer token
- Endpoints: User info, magnets status, upload magnet, delete magnet

## Project Structure

```
app/
├── src/main/java/com/unchained/multi/
│   ├── data/
│   │   ├── api/           # API interfaces
│   │   │   ├── RealDebridApi.kt
│   │   │   ├── TorBoxApi.kt
│   │   │   └── AllDebridApi.kt
│   │   ├── model/         # Data models
│   │   │   ├── DebridProvider.kt
│   │   │   ├── User.kt
│   │   │   └── Torrent.kt
│   │   ├── preferences/   # DataStore
│   │   │   └── PreferencesManager.kt
│   │   └── repository/    # Data layer
│   │       └── DebridRepository.kt
│   ├── di/                # Dependency injection
│   │   └── NetworkModule.kt
│   ├── ui/
│   │   ├── screens/       # Composable screens
│   │   │   ├── SplashScreen.kt
│   │   │   ├── ProviderSelectionScreen.kt
│   │   │   ├── LoginScreen.kt
│   │   │   └── HomeScreen.kt
│   │   ├── theme/         # Material theme
│   │   │   ├── Color.kt
│   │   │   ├── Theme.kt
│   │   │   └── Type.kt
│   │   ├── viewmodel/     # ViewModels
│   │   │   └── MainViewModel.kt
│   │   └── DebridifyApp.kt
│   ├── MainActivity.kt
│   └── DebridifyApp.kt
```

## Technologies Used

### Android
- **Kotlin** - Programming language
- **Jetpack Compose** - Modern UI toolkit
- **Material Design 3** - Design system
- **Navigation Compose** - Navigation framework

### Architecture Components
- **ViewModel** - UI state management
- **LiveData/Flow** - Observable data holders
- **DataStore** - Preferences storage
- **Hilt** - Dependency injection

### Networking
- **Retrofit** - HTTP client
- **OkHttp** - HTTP/2 support
- **Gson** - JSON serialization

### Coroutines
- **Kotlinx Coroutines** - Asynchronous programming
- **Flow** - Reactive streams

## Permissions

The app requires the following permissions:
- `INTERNET` - To communicate with debrid service APIs
- `ACCESS_NETWORK_STATE` - To check network connectivity

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Roadmap

### Planned Features
- [ ] Torrent file upload support
- [ ] File selection for multi-file torrents
- [ ] Download history
- [ ] Link unrestricting/unlocking
- [ ] Streaming support
- [ ] Remote player integration (VLC, Kodi)
- [ ] Search plugins integration
- [ ] Notifications for torrent completion
- [ ] Widget support
- [ ] Multiple account support per provider
- [ ] Torrent details screen with file list
- [ ] Statistics and usage tracking
- [ ] Export/import settings

## Known Issues

- Some TorBox API responses may vary from documentation
- AllDebrid cached torrent checking not yet implemented
- Real-Debrid file selection for multi-file torrents pending

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Inspired by [Unchained for Android](https://github.com/LivingWithHippos/unchained-android)
- Real-Debrid API documentation
- TorBox API documentation  
- AllDebrid API documentation
- Material Design team for the design system
- JetBrains for Kotlin and Android Studio

## Support

For support, please open an issue on GitHub or contact the maintainers.

## Disclaimer

This app is not affiliated with Real-Debrid, TorBox, or AllDebrid. All trademarks and service marks are the property of their respective owners. Users must have valid subscriptions to use these services.

---

**Made with ❤️ for the debrid community**
