#!/bin/bash

echo "========================================"
echo "  Debridify - Docker Build Script"
echo "========================================"
echo ""

# Build Docker image
echo "Step 1: Building Docker image..."
docker build -t debridify-builder .

if [ $? -ne 0 ]; then
    echo "❌ Docker build failed!"
    exit 1
fi

echo ""
echo "Step 2: Running build container..."

# Run container and build APK
docker run --rm -v "$(pwd)/app/build:/project/app/build" debridify-builder

if [ $? -ne 0 ]; then
    echo "❌ APK build failed!"
    exit 1
fi

echo ""
echo "✅ Build successful!"
echo ""
echo "📱 APK Location:"
echo "   $(pwd)/app/build/outputs/apk/debug/app-debug.apk"
echo ""
