# Quick Start Guide - Debridify

## 🚀 Getting Started in 5 Minutes

### Step 1: Open in Android Studio

1. Download/extract the project folder
2. Open Android Studio
3. Click "Open" and select the `Debridify` folder
4. Wait for Gradle sync to complete (2-3 minutes first time)

### Step 2: Run the App

1. Click the green "Run" button (▶️) in the toolbar
2. Select a device:
   - Physical device (enable USB debugging)
   - Or create an emulator (recommended: Pixel 5, API 34)
3. App will install and launch automatically

### Step 3: Test the App

1. **Select Provider** - Choose Real-Debrid, TorBox, or AllDebrid
2. **Login** - Enter your API key
3. **Add Magnet** - Tap the floating button and paste a magnet link
4. **View Progress** - Watch your torrent download

## 📱 Quick Test Without Building

If you want to test without setting up the development environment:

1. Build APK: `./gradlew assembleDebug`
2. Find APK in: `app/build/outputs/apk/debug/`
3. Install on your Android device
4. Grant permissions and test

## 🔑 Getting API Keys

### Real-Debrid
1. Visit: https://real-debrid.com
2. Go to: My Account → API Token
3. Copy your key

### TorBox  
1. Visit: https://torbox.app
2. Go to: Settings → API
3. Generate and copy key

### AllDebrid
1. Visit: https://alldebrid.com
2. Go to: Tools → API Keys
3. Create and copy key

## 🎯 Test Magnet Links

Here are some legal test magnets:

**Ubuntu ISO (Always available):**
```
magnet:?xt=urn:btih:3b245504cf5f11bbdbe1201cea6a6bf45aee1bc0
```

**Debian ISO:**
```
magnet:?xt=urn:btih:9f9165d9a281a9b8e782cd5176bbcc8256fd1871
```

## 📂 Project Structure

```
Debridify/
├── app/
│   ├── build.gradle.kts          # App dependencies
│   ├── src/main/
│   │   ├── AndroidManifest.xml   # App configuration
│   │   ├── java/com/debridify/app/
│   │   │   ├── data/             # Data layer
│   │   │   ├── di/               # Dependency injection
│   │   │   ├── ui/               # UI layer
│   │   │   └── MainActivity.kt   # Entry point
│   │   └── res/                  # Resources
├── build.gradle.kts              # Project config
├── README.md                     # Full documentation
├── SETUP_GUIDE.md               # Detailed setup
└── PROJECT_SUMMARY.md           # Project overview
```

## 🛠️ Common Issues

### "Gradle sync failed"
**Fix:** File → Invalidate Caches / Restart

### "SDK not found"
**Fix:** Tools → SDK Manager → Install Android 14.0 (API 34)

### "Build failed"
**Fix:** 
```bash
./gradlew clean
./gradlew build
```

### App crashes on device
**Fix:** Check Logcat (View → Tool Windows → Logcat)

## 🎨 Customization

### Change App Name
Edit: `app/src/main/res/values/strings.xml`
```xml
<string name="app_name">Your Name</string>
```

### Change Colors
Edit: `app/src/main/java/com/unchained/multi/ui/theme/Color.kt`

### Add New Provider
1. Add to `DebridProvider` enum
2. Create API interface
3. Add models
4. Update repository

## 📚 Learn More

- **Full README:** See `README.md`
- **Setup Guide:** See `SETUP_GUIDE.md`  
- **Project Summary:** See `PROJECT_SUMMARY.md`

## 🐛 Troubleshooting

### Issue: Cannot login
- ✅ Check API key is correct
- ✅ Check internet connection
- ✅ Check Logcat for errors

### Issue: No torrents showing
- ✅ Add a magnet link first
- ✅ Pull down to refresh
- ✅ Check provider website

### Issue: App is slow
- ✅ Enable ProGuard for release build
- ✅ Reduce logging level
- ✅ Check device storage

## 💡 Pro Tips

1. **Use Debug Build** for development (includes logging)
2. **Use Release Build** for testing performance
3. **Enable Logcat** to see API requests
4. **Test with multiple accounts** across providers
5. **Check the Wiki** for advanced features

## 🤝 Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature-name`
3. Make your changes
4. Test thoroughly
5. Submit pull request

## 📝 Development Checklist

Before submitting changes:
- [ ] Code compiles without errors
- [ ] Tested with all three providers
- [ ] No hardcoded API keys
- [ ] Follows Kotlin coding conventions
- [ ] UI looks good in dark/light themes
- [ ] No memory leaks
- [ ] Added comments for complex logic

## 🎓 Learning Resources

- [Android Developers](https://developer.android.com)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs)
- [Material Design 3](https://m3.material.io)

## 📞 Support

- **GitHub Issues:** For bugs and features
- **Discussions:** For questions and ideas
- **Email:** For security issues

## ⚡ Quick Commands

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test

# Install on device
./gradlew installDebug

# Check dependencies
./gradlew dependencies
```

## 🎉 Success!

If the app launches and you can login, you're all set! 

Start adding magnet links and managing your debrid services from one unified app.

---

**Happy Coding! 🚀**

For detailed information, see the full documentation in `README.md`
