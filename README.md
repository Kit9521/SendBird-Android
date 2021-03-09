# Sendbird Android samples

## Introduction

SendBird-Android forked from [sendbird/SendBird-Android](https://github.com/sendbird/SendBird-SDK-Android) for demo unit and ui automated testing.

### 1. Run unit testing
```
cd basic
./gradlew test
```

### 2. Run instrumentation test on connected device(s)
```
cd basic
./gradlew connectedAndroidTest
```

### 3. Continuous testing

Tools used:
Github Action (CICD), 
[Android-Emulator-Runner](https://github.com/ReactiveCircus/android-emulator-runner), 
Espresso

```
Unit and instrumentation test will be triggered when user push commit on demo branch,
workflow details can be found on .github/workflows folder
```