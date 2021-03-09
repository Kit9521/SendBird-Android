# Sendbird Android samples

## Introduction

SendBird-Android forked from [sendbird/SendBird-Android](https://github.com/sendbird/SendBird-SDK-Android) for demo unit and ui automated testing.

### Run unit testing
```
cd basic
./gradlew test
```

### Run instrumentation test on connected device(s)
```
cd basic
./gradlew connectedAndroidTest
```

### Continuous testing

`CICD Tool: Github-actions`

Unit and instrumentation test will be triggered when user push commit on `demo` branch,
workflow details can be found on `.github/workflows` folder