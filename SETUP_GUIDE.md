# Setup and Troubleshooting Guide

## Development Environment Setup

### Required Tools

1. **Android Studio**
   - Version: Arctic Fox (2021.1.1) or newer
   - Download from: https://developer.android.com/studio

2. **Java Development Kit (JDK)**
   - Version: JDK 17
   - Usually bundled with Android Studio

3. **Android SDK**
   - Minimum SDK: API 24 (Android 7.0)
   - Target SDK: API 34 (Android 14)

### Step-by-Step Setup

1. **Install Android Studio**
   ```bash
   # Download from official website
   # Install following the wizard instructions
   ```

2. **Clone the Project**
   ```bash
   git clone <repository-url>
   cd Debridify
   ```

3. **Open in Android Studio**
   - File → Open
   - Navigate to project directory
   - Click "OK"

4. **Gradle Sync**
   - Android Studio will automatically start syncing
   - If not, click "Sync Project with Gradle Files" in the toolbar
   - Wait for sync to complete

5. **SDK Setup**
   - Tools → SDK Manager
   - Ensure Android 14.0 (API 34) is installed
   - Install any missing components

6. **Build the Project**
   - Build → Make Project
   - Wait for build to complete

7. **Run on Device/Emulator**
   - Click the "Run" button (green triangle)
   - Select your device or create an emulator
   - App will install and launch

## Common Issues and Solutions

### Issue 1: Gradle Sync Failed

**Symptoms:**
- Error message: "Gradle sync failed"
- Red text in the build output

**Solutions:**

1. **Check Internet Connection**
   - Gradle needs to download dependencies
   - Ensure stable internet connection

2. **Invalidate Caches**
   - File → Invalidate Caches / Restart
   - Select "Invalidate and Restart"

3. **Check Gradle Wrapper**
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

4. **Update Gradle Plugin**
   - Update version in `build.gradle.kts` if needed
   - Sync again

### Issue 2: Compilation Errors

**Symptoms:**
- Red errors in code
- Build fails with compilation errors

**Solutions:**

1. **Check Kotlin Version**
   - Ensure Kotlin plugin is up to date
   - File → Settings → Plugins → Update Kotlin

2. **Clean and Rebuild**
   - Build → Clean Project
   - Build → Rebuild Project

3. **Check Dependencies**
   - Verify all dependencies in `build.gradle.kts` are correct
   - Check for version conflicts

### Issue 3: App Crashes on Launch

**Symptoms:**
- App installs but crashes immediately
- "App has stopped" message

**Solutions:**

1. **Check Logcat**
   - View → Tool Windows → Logcat
   - Look for exception stack traces
   - Filter by app package name

2. **Verify AndroidManifest.xml**
   - Ensure all activities are declared
   - Check permissions are correct

3. **Check Hilt Setup**
   - Verify `@HiltAndroidApp` on Application class
   - Ensure all ViewModels have `@HiltViewModel`

### Issue 4: Network Requests Failing

**Symptoms:**
- Login fails
- Cannot fetch torrents
- API errors

**Solutions:**

1. **Check API Keys**
   - Ensure API keys are valid
   - Test API key in browser or Postman

2. **Verify Network Permissions**
   - Check `INTERNET` permission in manifest
   - Ensure device has internet connection

3. **Check API URLs**
   - Verify base URLs in `DebridProvider.kt`
   - Test endpoints manually

4. **Inspect Network Traffic**
   - Use OkHttp logging interceptor
   - Check request/response in Logcat

### Issue 5: Build Takes Too Long

**Solutions:**

1. **Enable Gradle Daemon**
   - Add to `gradle.properties`:
   ```
   org.gradle.daemon=true
   org.gradle.parallel=true
   org.gradle.configureondemand=true
   ```

2. **Increase Heap Size**
   - Increase memory in `gradle.properties`:
   ```
   org.gradle.jvmargs=-Xmx4096m
   ```

3. **Use Build Cache**
   - Add to `gradle.properties`:
   ```
   android.enableBuildCache=true
   ```

### Issue 6: Compose Preview Not Working

**Symptoms:**
- Preview pane shows errors
- Cannot see UI previews

**Solutions:**

1. **Invalidate Caches**
   - File → Invalidate Caches / Restart

2. **Update Android Studio**
   - Help → Check for Updates
   - Install latest version

3. **Add Preview Annotation**
   ```kotlin
   @Preview
   @Composable
   fun PreviewName() {
       DebridifyTheme {
           YourComposable()
       }
   }
   ```

## Testing the App

### Unit Testing

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "ClassNameTest"
```

### Instrumentation Testing

```bash
# Run instrumentation tests
./gradlew connectedAndroidTest
```

### Manual Testing Checklist

- [ ] Provider selection works
- [ ] Login with valid API key succeeds
- [ ] User information displays correctly
- [ ] Torrents list loads
- [ ] Add magnet link works
- [ ] Delete torrent works
- [ ] Logout and switch provider works
- [ ] App handles magnet links from other apps
- [ ] Dark/light theme works
- [ ] App survives configuration changes

## Debugging Tips

### Enable Debug Logging

In `NetworkModule.kt`, ensure logging interceptor level is set:

```kotlin
loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
```

### Use Logcat Filters

```
# Filter by package
package:com.unchained.multi

# Filter by tag
tag:MainViewModel

# Filter by log level
level:ERROR
```

### Debug Network Issues

1. Use Postman or curl to test API endpoints
2. Check response codes and bodies
3. Verify authentication headers
4. Test with different providers

### Memory Leaks

Use Android Profiler:
1. View → Tool Windows → Profiler
2. Select your app
3. Monitor memory usage
4. Look for growing memory patterns

## Performance Optimization

### Tips for Better Performance

1. **Use ProGuard/R8**
   - Enable minification in release builds
   - Remove unused code

2. **Optimize Images**
   - Use WebP format
   - Provide different resolutions

3. **Reduce APK Size**
   - Enable APK splitting
   - Remove unused resources

4. **Lazy Loading**
   - Load data on demand
   - Use pagination for large lists

## Build Variants

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Install on Device
```bash
./gradlew installDebug
./gradlew installRelease
```

## Continuous Integration

### GitHub Actions Example

```yaml
name: Android CI

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
        distribution: 'adopt'
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    - name: Build with Gradle
      run: ./gradlew build
    - name: Run tests
      run: ./gradlew test
```

## Getting Help

### Resources

1. **Android Developers**
   - https://developer.android.com

2. **Kotlin Documentation**
   - https://kotlinlang.org/docs

3. **Jetpack Compose**
   - https://developer.android.com/jetpack/compose

4. **Stack Overflow**
   - Tag: [android], [kotlin], [jetpack-compose]

### Community

- Open an issue on GitHub
- Check existing issues for solutions
- Contribute fixes via pull requests

## Additional Notes

### API Rate Limiting

All providers have rate limits:
- **Real-Debrid**: Be mindful of request frequency
- **TorBox**: Check their API documentation
- **AllDebrid**: Implement proper rate limiting

### Best Practices

1. Always test with all three providers
2. Handle errors gracefully
3. Provide user feedback
4. Log important events
5. Keep dependencies updated
6. Write tests for critical functionality
7. Follow Material Design guidelines
8. Respect user privacy

### Security Considerations

1. **API Keys**
   - Never commit API keys to version control
   - Use DataStore for secure storage
   - Clear keys on logout

2. **Network Security**
   - Use HTTPS only
   - Validate SSL certificates
   - Implement certificate pinning if needed

3. **Code Obfuscation**
   - Enable ProGuard/R8 for release builds
   - Keep mapping files for crash reports

---

**Need more help?** Open an issue on GitHub with:
- Device model and Android version
- Steps to reproduce the problem
- Logcat output
- Screenshots if applicable
