# Use official Android SDK image
FROM mingc/android-build-box:latest

# Set working directory
WORKDIR /project

# Copy project files
COPY . .

# Grant execute permission to gradlew
RUN chmod +x gradlew

# Build the APK
RUN ./gradlew assembleDebug --no-daemon --stacktrace

# The APK will be in app/build/outputs/apk/debug/app-debug.apk
