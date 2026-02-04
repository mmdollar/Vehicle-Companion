# Vehicle Companion App

A modern Android application for managing vehicle information, built with Jetpack Compose and following clean architecture principles.

## Running the App

1. Clone the repository
2. Create a `local.properties` file in the root directory (if not already present)
3. Add your Mapbox token to `local.properties`:
```
   MAPBOX_TOKEN=your_mapbox_token_here
```
4. Open the project in Android Studio
5. Sync Gradle
6. Run the app on an emulator or physical device (minimum SDK 28)

## Architecture & Design Choices

### Architecture
- **MVI (Model-View-Intent)**: Unidirectional data flow pattern for predictable state management
- **Clean Architecture**: Separation of concerns with distinct layers (UI, Domain, Data)
- **Jetpack Compose**: Modern declarative UI framework
- **Hilt**: Dependency injection for better testability and modularity
- **Room**: Local database for offline-first data persistence
- **Retrofit + Moshi**: Network layer for API communication
- **Navigation Component 3**: Type-safe navigation between screens

### Design
- **Minimal Design**: Clean, card-based UI focusing on content and usability
- **Material 3**: Following Google's latest design guidelines
- **Responsive Layouts**: Adapting to different screen sizes

## How to Run Tests

### Using Android Studio
1. Right-click on the `test` or `androidTest` directory
2. Select "Run Tests"

### Using Gradle (Command Line)
```bash
# Run unit tests
./gradlew test
```

## Error & Empty State Handling

The app implements consistent error handling across all screens:
- **General Error Screen**: Both main screens redirect to a centralized error screen when exceptions occur

## What I'd Build Next

Given more time, I would enhance the app with:

1. **Improved Animations**
    - Smoother bottom sheet transitions
    - Card enter/exit animations
    - Loading state animations

2. **Enhanced Design**
    - Refined color palette and theming
    - Custom illustrations for empty states
    - Improved typography and spacing consistency

3. **Additional Features**
    - Advanced filtering and sorting options
    - Search functionality

4. **Performance Optimizations**
    - Pagination for large datasets