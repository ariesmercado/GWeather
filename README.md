# 🌤️ GWeather

GWeather is a modern Android application that displays real-time weather data using the **OpenWeather API**.  
It provides **current weather** and **weather history** fetched each time the app is opened — built with **Clean Architecture + MVVM** and **Jetpack Compose** for a smooth, reactive experience.

---

## 📋 Coding Challenge Overview

This project was developed as part of an **Android Developer Coding Challenge**.

The app connects to the **OpenWeather API** and fulfills the following requirements:

- Two main tabs:
  1. **Current Weather** — displays city, country, temperature in °C, sunrise/sunset, and weather icons (☀️/🌙).
  2. **Weather History** — lists previously fetched weather data each time the app opens.
- **User Authentication** with Registration and Sign-In.
- **Security Measures** for API key and Firebase files.
- **Unit Tests** implemented.
- Developed using **modern Android architecture and Jetpack components**.

---

## 🏗️ Architecture

The app follows the **Clean Architecture + MVVM (Model-View-ViewModel)** pattern for scalability, testability, and maintainability.

### 🧩 Layers
- **UI Layer (Jetpack Compose)** — declarative, reactive interface.
- **ViewModel** — holds and manages UI-related data.
- **Repository Pattern** — abstracts access to network and database sources.
- **Use Cases** — encapsulate business logic for better testability.

---

## 🧰 Tech Stack

| Category | Tools / Libraries |
|-----------|------------------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose |
| **Architecture** | Clean Architecture + MVVM |
| **Asynchronous** | Kotlin Coroutines + Flows |
| **Local Storage** | Room Database |
| **Networking** | Retrofit + OkHttp |
| **Dependency Injection** | Dagger Hilt |
| **Authentication** | FirebaseAuth |
| **Unit Testing** | JUnit |
| **API** | OpenWeather API |
| **IDE** | Android Studio Ladybug (2024.2.1 Patch 2) |
| **Java Runtime** | JetBrains Runtime 21.0.3 |

---

## 🚀 Installation & Setup

Follow these steps to set up and run the project locally:

### 1️⃣ Clone the Repository
- git clone https://github.com/ariesmercado/GWeather.git
- cd GWeather

### 2️⃣ Add google-services.json
Firebase is used for authentication, so you must include the google-services.json file in your project.

**Steps:**

1. Download from my provided Google Drive link. -> [google-services.json](https://drive.google.com/file/d/1FncQuNSv47rV3FmEbGKs6ukwKPtyHrRy/view?usp=sharing)
2. Place it inside: app/google-services.json
3. Ensure this file is listed in .gitignore.

### 3️⃣ Add secret.properties
This file stores sensitive information such as your API key and database name securely.

**Steps:**
1. Create a file named secret.properties in the project root
or download it from my provided Google Drive link. -> [secret.properties](https://drive.google.com/file/d/1rm-75v7yPoBVBEjw8zAHXeyTgRrYkDBU/view?usp=sharing)

2. Add the following contents:
- API_KEY=your_openweather_api_key_here
- BASE_URL=https://api.openweathermap.org/
- DB=GWeather

This file is ignored in .gitignore for security.

### 4️⃣ Build the Project

1. Open Android Studio
2. Sync Gradle
3. Connect a device or open an emulator
4. Click Run ▶️

### 🔒 API Key Management
- Your OpenWeather API key must be stored in the secret.properties file for security.
---
- API_KEY=your_openweather_api_key_here
- BASE_URL=https://api.openweathermap.org/
- DB=GWeather

In code, these values are safely read through Gradle configuration.
⚠️ Never hardcode your API key directly in Kotlin files.

### 📱 Screenshots

| Splash                            | Location Permission                  | Login                           | Registration                          |
| --------------------------------- | ------------------------------------ | ------------------------------- | ------------------------------------- |
| ![splash](https://github.com/user-attachments/assets/d22fb6e8-9728-401b-b983-d906e1801c42) | ![location permision](https://github.com/user-attachments/assets/2756e6e7-605d-4afd-9e4f-c29c060fc77a) | ![login](https://github.com/user-attachments/assets/691abee2-ae5c-4286-b9fd-d50652da5bbe) | ![registration](https://github.com/user-attachments/assets/56f441e3-799d-4b62-8490-c518ee478a44) |
| Current Weather                   | Weather History                      |
| --------------------------------- | ------------------------------------ |
| ![current weather](https://github.com/user-attachments/assets/d2b546a1-cd12-422e-b98c-31880d17bd20) | ![history](https://github.com/user-attachments/assets/60fdc9da-8b20-4e93-99c5-fa83ecc9df8d) |

### 🧑‍💻 Developer Notes

- Implements Firebase Authentication for user login and registration.

- Uses Room for weather history persistence.

- Displays dynamic weather icons (☀️ for day / 🌙 for night).

- Stores sensitive keys securely via secret.properties.

- Written in Kotlin with testable, clean, and maintainable code.

### 🪪 License
This project was just developed as part of an Android Developer Coding Challenge.

### 💬 Contact
- 📧 mercadoaries92@gmail.com
- 📱 +639308315576
- 💼 **LinkedIn:** [Linkedin](https://www.linkedin.com/in/aries-mercado)

For clarifications or review feedback, feel free to reach out.
