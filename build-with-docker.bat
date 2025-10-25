@echo off
echo ========================================
echo   Debridify - Docker Build Script
echo ========================================
echo.

echo Step 1: Building Docker image...
docker build -t debridify-builder .

if %ERRORLEVEL% NEQ 0 (
    echo X Build failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Running build container...

docker run --rm -v "%cd%/app/build:/project/app/build" debridify-builder

if %ERRORLEVEL% NEQ 0 (
    echo X APK build failed!
    pause
    exit /b 1
)

echo.
echo Build successful!
echo.
echo APK Location:
echo   %cd%\app\build\outputs\apk\debug\app-debug.apk
echo.
pause
