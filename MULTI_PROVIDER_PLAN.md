# Multi-Provider Support - Implementation Plan

## Current Limitation

The app currently only supports **ONE** debrid provider at a time. Users must logout and switch providers to access a different service.

## Requested Feature

**Use ALL debrid services simultaneously** - Users want to:
- Add accounts for Real-Debrid, TorBox, AND AllDebrid
- View torrents from all providers in one place
- Switch between providers easily
- Manage multiple accounts without logging out

---

## Architecture Changes Required

### 1. Data Storage (PreferencesManager)

**Current:**
```kotlin
// Stores ONE provider and ONE API key
currentProvider: DebridProvider?
apiKey: String?
```

**New:**
```kotlin
// Store multiple API keys (one per provider)
realDebridApiKey: String?
torBoxApiKey: String?
allDebridApiKey: String?
activeProviders: Set<DebridProvider> // Which providers are configured
```

### 2. ViewModel (MainViewModel)

**Current:**
```kotlin
private val _currentProvider = MutableStateFlow<DebridProvider?>(null)
private val _torrents = MutableStateFlow<List<UnifiedTorrent>>(emptyList())
```

**New:**
```kotlin
private val _activeProviders = MutableStateFlow<Set<DebridProvider>>(emptySet())
private val _selectedProvider = MutableStateFlow<DebridProvider?>(null) // For filtering
private val _torrentsByProvider = MutableStateFlow<Map<DebridProvider, List<UnifiedTorrent>>>(emptyMap())
private val _allTorrents = MutableStateFlow<List<UnifiedTorrent>>(emptyList()) // Combined view
```

### 3. UI Changes

#### Option A: Bottom Navigation (Recommended)
```
┌────────────────────────────────────┐
│  Debridify                   [≡]   │
├────────────────────────────────────┤
│                                    │
│  [Torrents List]                   │
│                                    │
│                                    │
├────────────────────────────────────┤
│  ALL  │  Real-Debrid  │  TorBox  │ AllDebrid
└────────────────────────────────────┘
```

Features:
- Bottom tabs to switch between providers
- "ALL" tab shows combined torrents from all providers
- Each torrent card shows provider badge
- Filter by provider instantly

#### Option B: Dropdown/Chip Filter
```
┌────────────────────────────────────┐
│  Debridify                   [≡]   │
│  [All Providers ▼]                 │
├────────────────────────────────────┤
│  [Torrents List]                   │
│                                    │
└────────────────────────────────────┘
```

#### Option C: Drawer Navigation
```
┌────────────────────────────────────┐
│  ☰  Debridify              [+]     │
├────────────────────────────────────┤
│  [Torrents List]                   │
│                                    │
└────────────────────────────────────┘

Drawer:
┌────────────────┐
│ All Providers  │
│ ✓ Real-Debrid  │
│ ✓ TorBox       │
│ ✓ AllDebrid    │
│ ─────────      │
│ Add Account    │
│ Settings       │
│ Logout         │
└────────────────┘
```

### 4. Provider Selection Screen Changes

**Current:** Select ONE provider → login → use app

**New Options:**

#### Option 1: Add Multiple Providers Upfront
```
┌────────────────────────────────────┐
│  Select Providers (1 or more)      │
├────────────────────────────────────┤
│  ☑ Real-Debrid                     │
│     Fast and reliable              │
│                                    │
│  ☑ TorBox                          │
│     Unlimited bandwidth            │
│                                    │
│  ☐ AllDebrid                       │
│     Multi-hoster service           │
│                                    │
│           [Continue]               │
└────────────────────────────────────┘
```

Then: Enter API keys for each selected provider

#### Option 2: Start with One, Add More Later
```
Initial setup: Choose ONE provider
Later: Settings → Add Provider → Choose additional
```

---

## Implementation Steps

### Phase 1: Backend Changes

1. **Update DebridProvider.kt**
```kotlin
// Add provider colors for UI badges
val badgeColor: Color
    get() = when (this) {
        REAL_DEBRID -> Color(0xFF00A86B)
        TORBOX -> Color(0xFF4A90E2)
        ALLDEBRID -> Color(0xFFFF6B6B)
    }
```

2. **Update PreferencesManager**
```kotlin
class PreferencesManager {
    // Store multiple API keys
    suspend fun setProviderApiKey(provider: DebridProvider, apiKey: String)
    suspend fun getProviderApiKey(provider: DebridProvider): String?
    suspend fun clearProviderApiKey(provider: DebridProvider)

    // Manage active providers
    suspend fun addActiveProvider(provider: DebridProvider)
    suspend fun removeActiveProvider(provider: DebridProvider)
    suspend fun getActiveProviders(): Set<DebridProvider>
}
```

3. **Update DebridRepository**
```kotlin
class DebridRepository {
    // Load torrents from ALL active providers
    suspend fun getAllTorrents(): Result<Map<DebridProvider, List<UnifiedTorrent>>>

    // Load torrents from specific provider
    suspend fun getTorrents(provider: DebridProvider): Result<List<UnifiedTorrent>>
}
```

4. **Update MainViewModel**
```kotlin
class MainViewModel {
    private val _activeProviders = MutableStateFlow<Set<DebridProvider>>(emptySet())
    private val _selectedProvider = MutableStateFlow<DebridProvider?>(null) // null = ALL

    fun loadAllProviderTorrents() {
        // Load from all active providers
    }

    fun selectProvider(provider: DebridProvider?) {
        // null = show all, otherwise filter by provider
        _selectedProvider.value = provider
    }

    fun addProvider(provider: DebridProvider, apiKey: String) {
        // Add new provider without logging out
    }

    fun removeProvider(provider: DebridProvider) {
        // Remove provider
    }
}
```

### Phase 2: UI Changes

5. **Update HomeScreen.kt**
```kotlin
// Add bottom navigation or tabs
@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(...) },
                    label = { Text("All") }
                )
                // Add tab for each active provider
                activeProviders.forEachIndexed { index, provider ->
                    NavigationBarItem(
                        selected = selectedTab == index + 1,
                        onClick = { selectedTab = index + 1 },
                        icon = { Icon(...) },
                        label = { Text(provider.displayName) }
                    )
                }
            }
        }
    ) {
        // Show filtered torrents based on selectedTab
    }
}
```

6. **Update TorrentCard.kt**
```kotlin
// Add provider badge to each torrent
@Composable
fun TorrentCard(torrent: UnifiedTorrent) {
    Card {
        // ... existing content

        // Provider badge
        Badge(
            text = torrent.provider.displayName,
            color = torrent.provider.badgeColor
        )
    }
}
```

7. **Update ProviderSelectionScreen.kt**
```kotlin
// Allow multi-select
@Composable
fun MultiProviderSelectionScreen() {
    var selectedProviders by remember { mutableStateOf(emptySet<DebridProvider>()) }

    LazyColumn {
        items(DebridProvider.values()) { provider ->
            ProviderCard(
                provider = provider,
                isSelected = provider in selectedProviders,
                onToggle = {
                    selectedProviders = if (provider in selectedProviders) {
                        selectedProviders - provider
                    } else {
                        selectedProviders + provider
                    }
                }
            )
        }
    }

    Button(
        enabled = selectedProviders.isNotEmpty(),
        onClick = { /* Continue to API key entry for each */ }
    ) {
        Text("Continue")
    }
}
```

8. **Create MultiApiKeyScreen.kt**
```kotlin
// Enter API keys for all selected providers
@Composable
fun MultiApiKeyScreen(providers: Set<DebridProvider>) {
    val apiKeys = remember { mutableStateMapOf<DebridProvider, String>() }

    LazyColumn {
        items(providers.toList()) { provider ->
            OutlinedTextField(
                value = apiKeys[provider] ?: "",
                onValueChange = { apiKeys[provider] = it },
                label = { Text("${provider.displayName} API Key") }
            )
        }
    }

    Button(
        onClick = {
            // Save all API keys and navigate to home
        }
    ) {
        Text("Save & Continue")
    }
}
```

### Phase 3: Settings & Management

9. **Create SettingsScreen.kt**
```kotlin
@Composable
fun SettingsScreen() {
    LazyColumn {
        // Active Providers Section
        item {
            Text("Active Providers", style = MaterialTheme.typography.titleLarge)
        }

        items(activeProviders) { provider ->
            ProviderSettingsCard(
                provider = provider,
                onRemove = { viewModel.removeProvider(provider) },
                onUpdateApiKey = { /* Update dialog */ }
            )
        }

        // Add Provider Button
        item {
            OutlinedButton(onClick = { /* Show add provider dialog */ }) {
                Text("Add Another Provider")
            }
        }
    }
}
```

---

## Benefits

### For Users
✅ No need to logout to switch providers
✅ View all torrents in one place
✅ Compare torrents across providers
✅ Use best provider for each torrent
✅ Redundancy if one provider is down

### Technical
✅ Better data organization
✅ Cleaner architecture
✅ Easier to add more features per provider
✅ More flexible and powerful

---

## Estimated Development Time

- **Phase 1 (Backend)**: 3-4 hours
- **Phase 2 (UI)**: 4-5 hours
- **Phase 3 (Settings)**: 2-3 hours
- **Testing**: 2-3 hours

**Total: 11-15 hours**

---

## Recommended Approach

**Option 1: Full Multi-Provider** (Recommended)
- Implement all phases
- Allow users to add all providers at once
- Use bottom navigation tabs
- Best user experience

**Option 2: Gradual Migration**
- Keep current single-provider flow
- Add "Add Provider" option in settings
- Implement multi-view gradually
- Easier migration for existing users

---

## UI Mockup (Bottom Navigation)

```
╔═══════════════════════════════════════════╗
║  Debridify                    [🔄] [⋮]    ║
╠═══════════════════════════════════════════╣
║                                           ║
║  ┌───────────────────────────────────┐   ║
║  │ Real-Debrid Badge                 │   ║
║  │ Movie.Title.2024.mkv              │   ║
║  │ 4.2 GB                            │   ║
║  │ ████████████░░░░░░░░░ 65%         │   ║
║  │ ↓ 5.2 MB/s                        │   ║
║  └───────────────────────────────────┘   ║
║                                           ║
║  ┌───────────────────────────────────┐   ║
║  │ TorBox Badge                      │   ║
║  │ Show.S01E05.mkv                   │   ║
║  │ 1.8 GB                            │   ║
║  │ ████████████████████ 100%         │   ║
║  │ Ready                             │   ║
║  └───────────────────────────────────┘   ║
║                                           ║
║                                   [+]     ║
╠═══════════════════════════════════════════╣
║  ALL  │  Real-Debrid  │  TorBox  │  AllDebrid  ║
╚═══════════════════════════════════════════╝
```

---

**Status**: Plan Created
**Next Step**: Implement Phase 1 (Backend Changes)
