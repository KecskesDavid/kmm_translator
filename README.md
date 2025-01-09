<div align="center">

<a href="https://github.com/KecskesDavid/kmm_translator"/>
<h3 align="center">KMM Translator</h3>

<p align="center">
    A cross-platform project for translating text using Kotlin Multiplatform Mobile.  
</p>
</div>

## Table of Contents
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#tech-corner">Tech Corner</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#set-up-the-environment">Set up the environment</a></li>
        <li><a href="#check-your-environment">Check your environment</a></li>
      </ul>
    </li>
    <li>
      <a href="#examine-the-project-structure">Examine the project structure</a>
      <ul>
        <li><a href="#shared">shared</a></li>
        <li><a href="#androidApp">androidApp</a></li>
        <li><a href="#iosapp">iosApp</a></li>
      </ul>
    </li>
    <li><a href="#screenshots">Screenshots/Demo</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## About The Project
<img src="screenshots/ios_08.png" width="46%" /> <img src="screenshots/android_08.png" width="46%" />

This repository was creating for learning purposes. It's a cross-platform application for translating text using Kotlin Multiplatform Mobile.
It allows users to translate text, from one language to another, either via text or via voice recording. It also keeps track of the translation history with a built in caching logic.  
For the design and the project was inspired by <a href="https://github.com/philipplackner">@philipplackner</a>. Specifically I followed the next course: <a href="https://pl-coding.com/kmp">https://pl-coding.com/kmp</a>

### Tech Corner
* **[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform-ios-android-template)**
* **[Hilt](https://dagger.dev/)**
* **[Ktor](https://ktor.io/)**
* **[SqlDelight](https://sqldelight.github.io/sqldelight/2.0.2/)**
* **[Compose - Navigation](https://developer.android.com/jetpack/androidx/releases/navigation)**
* **[JUnit](https://junit.org/)**


## Getting Started

### Set up the environment

> **Warning**
> You need a Mac with macOS to write and run iOS-specific code on simulated or real devices.
> This is an Apple requirement.
To work with this project, you need the following:

* A machine running a recent version of macOS
* [Xcode](https://apps.apple.com/us/app/xcode/id497799835)
* [Android Studio](https://developer.android.com/studio)
* The [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
* The [CocoaPods dependency manager](https://kotlinlang.org/docs/native-cocoapods.html)

### Check your environment

Before you start, use the [KDoctor](https://github.com/Kotlin/kdoctor) tool to ensure that your development environment is configured correctly:

1. Install KDoctor with [Homebrew](https://brew.sh/):

    ```text
    brew install kdoctor
    ```

2. Run KDoctor in your terminal:

    ```text
    kdoctor
    ```

   If everything is set up correctly, you'll see valid output:

   ```text
   Environment diagnose (to see all details, use -v option):
   [✓] Operation System
   [✓] Java
   [✓] Android Studio
   [✓] Xcode
   [✓] Cocoapods
   
   Conclusion:
     ✓ Your system is ready for Kotlin Multiplatform Mobile development!
   ```

Otherwise, KDoctor will highlight which parts of your setup still need to be configured and will suggest a way to fix them.


## Examine the project structure

Open the project in Android Studio and switch the view from **Android** to **Project** to see all the files and targets belonging to the project:

The App includes 3 main modules:

### shared

This is a Kotlin module that contains the logic common for both Android and iOS applications,

Under the `kmm_translator/shared:` you can find the following modules:
- `commonMain:`
    This is a Kotlin module that contains the logic common for both Android and iOS applications, that is, the shared code & entry point between platforms.
    \
    In `commonMain/kotlin/`, you can find the logic for the data layer which consists of the: the clients for the networking layer as well as the necessary setup for the databse: dao's, mappers, drivers.
    \
    It uses Gradle as the build system. You can add dependencies and change settings in `shared/build.gradle.kts`.
    The `commonMain` module builds into an Android library and an iOS framework.
  
- `androidMain:`, `iosMain:`:
    This is where the _actual_ implementation happens of the previously defined interfaces or contracts for the specific classes or components.

### androidApp

This is a Kotlin module that builds into an Android application. It uses Gradle as the build system.
The `androidApp` module depends on and uses the `kmm_translator/shared` module as a regular Android library.

### iosApp

This is an Xcode project that builds into an iOS application.
The `iosApp` depends on and uses the `kmm_translator/shared` module as a CocoaPods dependency.


## Screenshots/Demo

## Contact

Name - Kecskés Dávid

LinkedIn - https://www.linkedin.com/in/kecskes-david/

