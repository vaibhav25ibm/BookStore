# 📚 E-BookStore — Android Book Store App

A modern Android bookstore app built with **Kotlin**, **Jetpack Compose**, and **Navigation Component**. Bookora lets users browse books, manage a cart, and complete purchases through a full checkout flow — all with a clean, themeable UI driven by a single design token system.

---

## 📱 Screenshots Overview

| Onboarding | Login | Home | Categories |
|:---:|:---:|:---:|:---:|
| 3-page carousel | Email/Password + Social | Search, chips, book rows | 2-column grid |

| Cart | Checkout | Payment | Order Success |
|:---:|:---:|:---:|:---:|
| Qty stepper + summary | Address + items + summary | Method selector + card form | Confetti + tracking |

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI Framework | Jetpack Compose (Material 3) |
| Navigation | Jetpack Navigation Compose |
| Image Loading | Coil |
| Architecture | Single-activity, screen-level composables |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 35 (Android 15) |
| Build System | Gradle with Version Catalog (`libs.versions.toml`) |

---

## 📁 Project Structure

```
Bookora/
├── gradle/
│   ├── libs.versions.toml          # Centralised dependency versions
│   └── wrapper/
│       └── gradle-wrapper.properties
├── gradle.properties               # AndroidX, Jetifier flags
├── settings.gradle.kts
├── build.gradle.kts                # Root build config
└── app/
    ├── build.gradle.kts            # App module config
    └── src/main/
        ├── AndroidManifest.xml
        ├── res/
        │   ├── values/strings.xml
        │   ├── values/themes.xml
        │   ├── values/colors.xml
        │   ├── drawable/ic_launcher_foreground.xml
        │   └── mipmap-anydpi-v26/ic_launcher.xml
        └── java/com/bookora/app/
            ├── MainActivity.kt
            ├── data/
            │   ├── model/
            │   │   └── Book.kt             # Book + Category data classes
            │   └── repository/
            │       └── BookRepository.kt   # Hardcoded books (8 titles)
            ├── navigation/
            │   ├── Screen.kt               # Sealed class route definitions
            │   └── BookoraNavGraph.kt      # NavHost with all composable routes
            └── ui/
                ├── theme/
                │   ├── Color.kt            # Raw colour palette
                │   ├── Type.kt             # Material3 Typography
                │   ├── Theme.kt            # BookoraTheme — accepts AppColors + AppTypography
                │   └── AppTokens.kt        # AppColors, AppTypography, AppTheme accessor
                ├── components/             # ← All reusable components live here
                │   ├── BookoraTopBar.kt
                │   ├── BookoraBottomNav.kt
                │   ├── BookoraSearchBar.kt
                │   ├── BookoraButtons.kt
                │   ├── SectionHeader.kt
                │   ├── OrderSummaryCard.kt
                │   ├── StepIndicator.kt
                │   ├── RadioSelector.kt
                │   ├── AppBadges.kt
                │   └── BookCard.kt
                └── screens/
                    ├── OnboardingScreen.kt
                    ├── LoginScreen.kt
                    ├── HomeScreen.kt
                    ├── CategoriesScreen.kt
                    ├── CartScreen.kt
                    ├── CheckoutScreen.kt
                    ├── PaymentScreen.kt
                    ├── BookDetailScreen.kt
                    └── OrderSuccessScreen.kt
```

---

## 🗺️ Navigation Flow

```
Onboarding (3 pages)
    └──► Login
              └──► Home ◄──────────────────────────────┐
                    ├── BookDetail (tap any book)        │
                    ├── Categories (bottom nav / chip)   │
                    └── Cart (bottom nav / top bar)      │
                              └──► Checkout              │
                                        └──► Payment     │
                                                  └──► OrderSuccess ──► (Continue Shopping)
```

All screens support back navigation via the ← back arrow.

---

## 🎨 Design System

### Theming — change the whole app in one place

Open `MainActivity.kt` and pass custom tokens to `BookoraTheme`:

```kotlin
BookoraTheme(
    appColors = AppColors(
        primary    = Color(0xFF6200EE),  // purple brand
        accent     = Color(0xFFFF6D00),  // deep-orange CTA
        background = Color(0xFFFAFAFA)
    ),
    appTypography = AppTypography(
        fontFamily = myCustomFontFamily  // swap entire app font here
    )
) {
    val navController = rememberNavController()
    BookoraNavGraph(navController = navController)
}
```

### AppColors tokens

| Token | Default | Used for |
|-------|---------|----------|
| `primary` | `#2D35C0` | Buttons, links, selected states |
| `secondary` | `#3D47E0` | Secondary actions |
| `accent` | `#E87722` | Prices, CTAs, cart badge |
| `background` | `#FFFFFF` | Screen backgrounds |
| `surface` | `#F7F8FA` | Card surfaces, image placeholders |
| `border` | `#E5E7EB` | Dividers, outlined fields |
| `textPrimary` | `#1F2328` | Headings, body text |
| `textMuted` | `#57606A` | Subtitles, placeholders |
| `success` | `#22A94B` | In-stock, order success |
| `error` | `#DC2626` | Discount values, errors |

### AppTypography tokens

| Token | Size | Weight | Used for |
|-------|------|--------|----------|
| `displayLarge` | 38sp | ExtraBold | Onboarding title, emoji |
| `titleXL` | 28sp | ExtraBold | Login welcome heading |
| `titleLarge` | 22sp | ExtraBold | Screen titles, book title |
| `titleMedium` | 20sp | Bold | Top bar title |
| `titleSmall` | 17sp | Bold | Section headings |
| `sectionTitle` | 16sp | Bold | Card section labels |
| `bodyLarge` | 15sp | Normal | Content body |
| `bodyMedium` | 14sp | Normal | Standard body |
| `bodySmall` | 13sp | Normal | Subtitles, hints |
| `labelMedium` | 13sp | SemiBold | Form labels, links |
| `labelSmall` | 12sp | Medium | Captions, tags |
| `caption` | 11sp | Normal | Bottom nav, badges |
| `price` | 24sp | ExtraBold | Large price display |
| `priceSmall` | 14sp | Bold | Inline price |
| `button` | 16sp | Bold | Primary/Orange button text |
| `buttonSmall` | 13sp | SemiBold | Secondary button text |

---

## 🧩 Common Components

Every reusable component reads colours and typography from `AppTheme.colors` / `AppTheme.typography`, so they all respond to theme changes automatically.

| Component | File | Usage |
|-----------|------|-------|
| `BookoraTopBar` | `BookoraTopBar.kt` | All screens — configure back/menu, cart badge, notification |
| `BookoraBottomNav` | `BookoraBottomNav.kt` | Home, OrderSuccess |
| `BookoraSearchBar` | `BookoraSearchBar.kt` | Home, Categories |
| `SectionHeader` | `SectionHeader.kt` | Home (Top Picks, Best Sellers, Categories) |
| `PrimaryButton` | `BookoraButtons.kt` | Login, BookDetail (Buy Now) |
| `OrangeButton` | `BookoraButtons.kt` | Cart, Checkout, Payment CTAs |
| `OutlineButton` | `BookoraButtons.kt` | BookDetail (Add to Cart), OrderSuccess |
| `OrderSummaryCard` | `OrderSummaryCard.kt` | Cart, Checkout, Payment |
| `StepIndicator` | `StepIndicator.kt` | Checkout (step 2), Payment (step 3) |
| `RadioSelector` | `RadioSelector.kt` | Checkout address cards, Payment methods |
| `DiscountBadge` | `AppBadges.kt` | BookCard, CartItem, BookDetail |
| `InStockBadge` | `AppBadges.kt` | CartItem |
| `OriginalPrice` | `AppBadges.kt` | BookCard, CartItem, BookDetail |
| `AppCheckbox` | `AppBadges.kt` | Payment card form (save card) |
| `BookCard` | `BookCard.kt` | Home (Top Picks, Best Sellers) |

---

## 📦 Data

All data is hardcoded in `BookRepository.kt`:

- **8 books** across Self-Help, Fiction, Biography, Non-Fiction
- **14 categories** (Fiction, Non-Fiction, Self-Help, Business, Biography, Children, Science, History, Art & Photography, Literature, Romance, Mystery & Thriller, Fantasy, Young Adult)
- Book covers are loaded via **Open Library Covers API** (`covers.openlibrary.org`)

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog** (2023.1.1) or later
- JDK 11+
- Android device or emulator running API 26+

### Steps

1. **Clone / open** the project:
   ```bash
   # If working from the playground copy
   cp -r /Users/vaibhavmalviya/.bob/playground/Bookora /Users/vaibhavmalviya/Bob-project/BookStore
   ```

2. **Open in Android Studio**
   - File → Open → select the `BookStore` folder
   - Wait for Gradle sync to complete

3. **Run the app**
   - Select a device/emulator
   - Click ▶ Run (or `Shift + F10`)

### Required `gradle.properties`

Make sure `BookStore/gradle.properties` contains:

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
android.enableJetifier=true
kotlin.code.style=official
org.gradle.configuration-cache=false
```

### Internet Permission

The app loads book covers from the internet. The `INTERNET` permission is already declared in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## 📋 Screens Summary

| Screen | Route | Key Features |
|--------|-------|-------------|
| **Onboarding** | `onboarding` | 3-page carousel, dot indicator, stacked books illustration |
| **Login** | `login` | Email + password fields, show/hide password, forgot password, social login buttons (Google / Facebook / Apple), sign-up link |
| **Home** | `home` | Search bar, category chips, Top Picks horizontal scroll, Best Sellers horizontal scroll, promo banner (20% OFF), bottom navigation |
| **Categories** | `categories` | Search filter, 2-column grid of 14 categories with icons |
| **Book Detail** | `book_detail/{id}` | Cover image, title, author, rating badge, category chip, discount %, description, Add to Cart + Buy Now |
| **Cart** | `cart` | Free delivery banner, item rows with quantity stepper + delete, promo code row, order summary |
| **Checkout** | `checkout` | 4-step indicator, 2 delivery addresses with radio select, order items list, order summary |
| **Payment** | `payment` | 4-step indicator, 5 payment methods (Card / UPI / Net Banking / Wallets / COD), card details form (number, name, expiry, CVV, save card), order summary |
| **Order Success** | `order_success` | Confetti animation, green checkmark, order ID + date, total paid, 4-step delivery tracker, order details with stacked book covers, email confirmation banner |

---

## 🔗 Dependencies

```toml
# gradle/libs.versions.toml
agp                 = "8.5.2"
kotlin              = "2.0.0"
coreKtx             = "1.13.1"
lifecycleRuntimeKtx = "2.8.3"
activityCompose     = "1.9.0"
composeBom          = "2024.08.00"
navigationCompose   = "2.7.7"
coil                = "2.7.0"
```

---

## 📄 License

This project is created for demonstration and learning purposes using Bob.

---

*Built with ❤️ using Kotlin + Jetpack Compose*
