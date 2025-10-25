# Project Renamed to Debridify

## Changes Made

The entire project has been renamed from "Unchained Multi" to **Debridify**.

### Updated Elements

✅ **App Name**: Now displays as "Debridify"  
✅ **Package Name**: Changed from `com.unchained.multi` to `com.debridify`  
✅ **Project Name**: Folder and root project renamed to "Debridify"  
✅ **Class Names**: All classes updated (DebridifyApp, DebridifyTheme, etc.)  
✅ **File Names**: App class files renamed to match  
✅ **Documentation**: All references updated in README and guides  
✅ **Resources**: strings.xml and themes.xml updated  
✅ **Build Files**: All Gradle files updated with new package  

### Files Changed

- `settings.gradle.kts` - Root project name
- `app/build.gradle.kts` - Application ID
- `app/src/main/res/values/strings.xml` - App display name
- `app/src/main/res/values/themes.xml` - Theme name
- `AndroidManifest.xml` - Package and application class
- All 20 Kotlin source files - Package declarations
- All documentation files - References updated
- Directory structure - Renamed to com/debridify

### Package Structure

```
Old: com/unchained/multi/
New: com/debridify/
```

### Application Class

```kotlin
Old: class UnchainedMultiApp : Application()
New: class DebridifyApp : Application()
```

### Theme Function

```kotlin
Old: @Composable fun UnchainedMultiTheme(...)
New: @Composable fun DebridifyTheme(...)
```

## Build Instructions

The project is ready to build with the new name:

```bash
cd Debridify
./gradlew clean
./gradlew build
```

All functionality remains the same - only the branding has changed!

---

**App Name**: Debridify  
**Package**: com.debridify  
**Ready to Use**: ✅ Yes
