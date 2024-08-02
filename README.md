# KOM Hunter

KOM Hunter is an Android app designed to help cyclists get more KOMs by predicting the best hour and day with the best wind on their chosen route.

## Table of Contents

- [Description](#description)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Description

KOM Hunter leverages weather data to analyze and predict the optimal times for cyclists to ride specific routes, taking wind conditions into account. The app allows users to upload GPX files, display routes on a map, and receive notifications about the best riding times.

## Features

- Upload and parse GPX files to select cycling routes.
- Display routes on a map using Google Maps SDK.
- Fetch real-time and forecasted weather data.
- Calculate wind impact along the route.
- Predict optimal riding times based on weather conditions.
- User profile management and performance tracking.
- Notifications about the best times to ride.

## Installation

### Prerequisites

- Android Studio
- Android SDK
- Git

### Steps

1. **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/kom-hunter.git
    ```

2. **Open the project in Android Studio:**
    - Open Android Studio.
    - Click on `File -> Open` and select the cloned repository.

3. **Set up dependencies:**
    - Sync the project with Gradle files.
    - Ensure all required dependencies are installed.

4. **Configure API keys:**
    - Obtain an API key from [OpenWeatherMap](https://openweathermap.org/api).
    - Obtain an API key from [Google Maps Platform](https://developers.google.com/maps/documentation/android-sdk/get-api-key).
    - Add the API keys to your `local.properties` file:
      ```properties
      WEATHER_API_KEY=your_openweathermap_api_key
      MAPS_API_KEY=your_google_maps_api_key
      ```

## Usage

1. **Upload GPX Files:**
    - Users can upload GPX files to select and save routes.

2. **View Route on Map:**
    - The selected route is displayed on a map.

3. **Fetch Weather Data:**
    - The app fetches real-time and forecasted weather data for the route.

4. **Predict Optimal Riding Times:**
    - The app calculates and displays the best times to ride based on wind conditions.

## Contributing

Contributions are welcome! Please read the [contributing guidelines](CONTRIBUTING.md) first.

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -am 'Add some feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Create a new Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

- **Name:** Your Name
- **Email:** your.email@example.com
- **GitHub:** [yourusername](https://github.com/yourusername)

