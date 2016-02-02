# Test
### Setup
```bash
# Setting the TERM this way keeps the Gradle output sane for Codeship
export TERM=dumb
# ${HOME}/cache is Codeship's persistent cache, dependencies will be kept across builds
export GRADLE_USER_HOME="${HOME}/cache/gradle/"
# Set the JDK version, Codeship specific
jdk_switcher use oraclejdk8
```
### Test pipeline 1
```bash
./gradlew --no-search-upward --info check
```
