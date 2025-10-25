# Debridify - Alternative Build Methods

## ⚠️ Android Studio Not Supported on Your Device

No problem! Here are **3 easy alternatives** to build your Debridify APK:

---

## Option 1: Docker Build (FASTEST - Recommended!) ✅

**You have Docker installed!** This is the easiest way to build.

### Step-by-Step:

1. **Open Terminal/Command Prompt**
   - Press `Win + R`
   - Type `cmd` and press Enter

2. **Navigate to project**
   ```cmd
   cd C:\Users\HP\Desktop\Debridify
   ```

3. **Run the build script**
   ```cmd
   build-with-docker.bat
   ```

4. **Wait for build**
   - First time: ~10-15 minutes (downloads Android SDK)
   - After that: ~3-5 minutes

5. **Get your APK!**
   ```
   Location: Debridify\app\build\outputs\apk\debug\app-debug.apk
   ```

### Manual Docker Commands (if script doesn't work):

```bash
# Build the Docker image
docker build -t debridify-builder .

# Run the build
docker run --rm -v "%cd%/app/build:/project/app/build" debridify-builder

# Get APK from:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## Option 2: GitHub Actions (100% Free, Cloud-Based) ✅

Build your APK automatically in the cloud - no local installation needed!

### Step-by-Step:

1. **Create GitHub Account** (if you don't have one)
   - Go to: https://github.com/signup
   - Sign up (it's free!)

2. **Create New Repository**
   - Click the `+` icon → New repository
   - Name: `debridify`
   - Make it **Public** (for free Actions)
   - Click "Create repository"

3. **Upload Your Project**
   
   **Option A: Using Git (if installed)**
   ```bash
   cd C:\Users\HP\Desktop\Debridify
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/YOUR_USERNAME/debridify.git
   git push -u origin main
   ```

   **Option B: Using GitHub Website**
   - On your repository page, click "uploading an existing file"
   - Drag and drop the entire Debridify folder
   - Click "Commit changes"

4. **GitHub Automatically Builds!**
   - Go to "Actions" tab in your repository
   - Watch the build progress
   - Takes ~5-10 minutes

5. **Download Your APK**
   - When build completes, click on it
   - Scroll to "Artifacts"
   - Download `debridify-debug`
   - Extract the ZIP to get your APK!

### Advantages:
- ✅ No software installation needed
- ✅ Builds in the cloud
- ✅ Completely free
- ✅ Can share project easily
- ✅ Automatic builds on every update

---

## Option 3: Online Build Services

### A. AppVeyor (Free)
1. Sign up at: https://www.appveyor.com/
2. Connect your GitHub repository
3. Add appveyor.yml configuration
4. Build automatically

### B. CircleCI (Free tier)
1. Sign up at: https://circleci.com/
2. Connect repository
3. Configure .circleci/config.yml
4. Download built APK

### C. Replit (Browser-based IDE)
1. Go to: https://replit.com/
2. Import GitHub repository
3. Configure Android environment
4. Build in browser

---

## Option 4: Ask a Friend

If you know someone with:
- Android Studio installed
- A Mac/Linux/Windows with Java 17 + Android SDK
- Docker installed

Just share the `Debridify` folder with them and ask them to build it!

---

## Comparison of Methods

| Method | Time | Difficulty | Cost | Best For |
|--------|------|-----------|------|----------|
| **Docker** | 10-15 min | Easy | Free | You (Docker already installed!) |
| **GitHub Actions** | 5-10 min | Medium | Free | Automatic builds, sharing |
| **Online Services** | 10-20 min | Medium | Free | No local setup |
| **Friend** | Varies | Easiest | Free | Quick one-time build |

---

## Recommended Order:

### 1st Choice: Docker (You have it!)
Just run `build-with-docker.bat` - done in 10 minutes!

### 2nd Choice: GitHub Actions
Best if you want to:
- Share your project
- Have automatic builds
- Learn Git/GitHub

### 3rd Choice: Online Services
If Docker doesn't work and you don't want to use GitHub

---

## Docker Build - Detailed Instructions

Since you have Docker installed, this is your best option!

### First Time Build:

1. Open Command Prompt
   ```cmd
   cd C:\Users\HP\Desktop\Debridify
   ```

2. Make sure Docker Desktop is running
   - Check system tray for Docker icon
   - Should show "Docker Desktop is running"

3. Run build
   ```cmd
   build-with-docker.bat
   ```

4. What happens:
   - Downloads Android build image (~3-4 GB)
   - Copies your project files
   - Installs dependencies
   - Builds the APK
   - Saves it to your computer

5. Find your APK:
   ```
   C:\Users\HP\Desktop\Debridify\app\build\outputs\apk\debug\app-debug.apk
   ```

### Subsequent Builds:

Much faster! (~3-5 minutes)
- Image already downloaded
- Just rebuilds the code

### Troubleshooting Docker:

**"Docker daemon not running"**
- Open Docker Desktop application
- Wait for it to start
- Try again

**"Cannot connect to Docker"**
- Restart Docker Desktop
- Or restart your computer

**"Out of disk space"**
- Docker images are large (~4-5 GB)
- Free up space and try again

---

## GitHub Actions - Detailed Instructions

### Setup:

1. **Install Git** (if not installed)
   - Download: https://git-scm.com/download/win
   - Install with default options

2. **Initialize Git in your project**
   ```bash
   cd C:\Users\HP\Desktop\Debridify
   git init
   git add .
   git commit -m "Initial Debridify commit"
   ```

3. **Create GitHub repository**
   - Go to https://github.com/new
   - Repository name: `debridify`
   - Public (required for free Actions)
   - Don't initialize with README
   - Click "Create repository"

4. **Push to GitHub**
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/debridify.git
   git branch -M main
   git push -u origin main
   ```

5. **Wait for build**
   - GitHub Actions will start automatically
   - Go to: https://github.com/YOUR_USERNAME/debridify/actions
   - Click on the running workflow
   - Wait 5-10 minutes

6. **Download APK**
   - When complete, click the workflow
   - Scroll to "Artifacts"
   - Download `debridify-debug.zip`
   - Extract to get `app-debug.apk`

### Updating and Rebuilding:

```bash
# Make changes to your code
git add .
git commit -m "Updated app"
git push

# GitHub automatically builds new APK!
```

---

## What I've Set Up For You:

✅ **Dockerfile** - Ready to build with Docker
✅ **build-with-docker.bat** - One-click Docker build
✅ **build-with-docker.sh** - For Git Bash
✅ **.github/workflows/build-apk.yml** - GitHub Actions config
✅ **.gitignore** - Git configuration
✅ **Gradle wrapper** - Build system ready

**Everything is configured - just choose your method!**

---

## Quick Decision Guide:

**Want it NOW?**
→ Use Docker (`build-with-docker.bat`)

**Want automatic builds?**
→ Use GitHub Actions

**Don't want to install anything?**
→ Use GitHub Actions (cloud-based)

**Have a friend with Android Studio?**
→ Send them the folder

---

## Support

### Docker Issues:
- Ensure Docker Desktop is running
- Check you have ~5 GB free disk space
- Try restarting Docker

### GitHub Issues:
- Make sure repository is Public (for free Actions)
- Check the Actions tab for error messages
- Ensure .github/workflows/build-apk.yml exists

### General Help:
- All files are ready in: `C:\Users\HP\Desktop\Debridify`
- Build logs will show any errors
- Most common issue: Not enough disk space

---

## Summary

🎉 **You have 3 working options to build your APK!**

**Recommended: Docker** (you already have it installed!)

Just run:
```cmd
cd C:\Users\HP\Desktop\Debridify
build-with-docker.bat
```

Wait 10-15 minutes, and your APK will be ready!

---

**Files Ready For You:**
- `build-with-docker.bat` ← Run this for Docker build
- `.github/workflows/build-apk.yml` ← GitHub Actions config
- `Dockerfile` ← Docker configuration

**Choose your method and let's build your app!** 🚀
