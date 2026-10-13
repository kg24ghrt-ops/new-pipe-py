# pip - Native Android School Notebook

A native Android school-notebook application built with Kotlin, Jetpack Compose, and Material 3. Features realistic ruled paper rendering with density-aware scaling.

## Features

- **Realistic Notebook Paper**: Warm off-white paper with blue horizontal lines and red left margin
- **Density-Aware Rendering**: Consistent line spacing and margins across all screen densities
- **Stable Document Coordinates**: Page geometry calculated in document space, not viewport space
- **Efficient Canvas Rendering**: Mathematical line repetition, no giant bitmaps
- **Dark/Light Theme Support**: Automatic theme adaptation with appropriate contrast
- **Multiple Pages**: Scrollable multi-page notebook view

## Requirements

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK API 36
- Gradle 8.14.4 (via wrapper)

## Getting Started

1. Open the project in Android Studio
2. Sync Gradle files
3. Build and run with Android Studio or `./gradlew assembleDebug`

## Architecture

```
app/
├── src/main/kotlin/com/example/pip/
│   ├── MainActivity.kt           # Entry point
│   ├── notebook/
│   │   ├── NotebookModel.kt      # Data models (Page, Line, Metrics, Theme)
│   │   ├── NotebookPaper.kt      # Compose Canvas paper rendering
│   │   └── NotebookScreen.kt     # Composable notebook UI
│   └── ui/theme/                 # Material 3 theming
```

### Notebook Paper Rendering

The `NotebookPaperSurface` composable uses `Canvas` to draw:
- Paper background with subtle shadow
- Vertical red margin line at consistent document coordinate
- Horizontal blue ruled lines at consistent line spacing
- All dimensions calculated from `NotebookPageMetrics` using density-aware scaling

Key design decisions:
- Line positions computed from stable `NotebookPageMetrics` (document space)
- `lineY(index)` formula: `marginTop + firstLineBaseline + index * lineSpacing`
- Visible line range calculated for clipping optimization
- No per-frame allocations in drawing loop
- Paint objects reused via `remember`

## Testing

### Unit Tests (JVM)
```bash
./gradlew test
```
Tests in `app/src/test/` cover:
- Page metrics calculations (dimensions, line positions, density scaling)
- Visible line range clipping
- Notebook page/model operations
- Theme color values

### Instrumented Tests (Android)
```bash
./gradlew connectedAndroidTest
```
Tests in `app/src/androidTest/` cover:
- Activity launch and Compose rendering
- Notebook screen display

### Static Analysis
```bash
./gradlew lint detekt
```

## CI/CD Pipeline

GitHub Actions workflow (`.github/workflows/ci.yml`):

| Job | Trigger | Purpose |
|-----|---------|---------|
| `fast-checks` | Push/PR | Lint, Unit Tests, Detekt (no Android SDK) |
| `build` | Push/PR | Debug APK build (needs fast-checks) |
| `instrumented-tests` | Push/PR | Emulator tests (needs fast-checks) |
| `security-scan` | Schedule/Dispatch | OWASP Dependency Check |
| `release-build` | Push to main | Release APK + AAB |

### Artifact Retention
- Debug APK: 3 days
- Release APK/AAB: 7 days
- Test/Lint/Detekt reports: 2 days
- Security scan: 7 days

## Gradle Configuration

- **AGP**: 8.7.3
- **Kotlin**: 2.0.21
- **Compose BOM**: 2024.09.03
- **Java**: 17
- **Gradle**: 8.14.4
- **Detekt**: 1.23.6

## License

MIT License