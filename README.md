# MoAnimals

A **multiloader Minecraft mod project** that brings new animals and related content into the game.  
The codebase is structured to support both **Fabric** and **NeoForge** through a shared common layer.

## Requirements

- Java Development Kit (JDK) 21  
- Gradle wrapper is included, no separate installation required  

## Project Structure

- `common` – Shared code used by all platforms  
- `fabric` – Fabric-specific implementations  
- `neoforge` – NeoForge-specific implementations  
- `build.gradle`, `settings.gradle`, `gradle.properties` – Gradle configuration  

## Building from Source

1. Clone the repository:  
   ```bash
   git clone <repo-url>
   cd <project-folder>
   ```

2. Run a build:

    * Unix/macOS: `./gradlew clean build`
    * Windows: `gradlew.bat clean build`
3. Build outputs:

    * Fabric JAR: `fabric/build/libs/`
    * NeoForge JAR: `neoforge/build/libs/`

## Development Setup

* Import the project as a Gradle project in your IDE
* Ensure **JDK 21** is selected
* Typical Gradle tasks are available, e.g. `runClient` / `runServer` per subproject (Fabric/NeoForge)
* Recommended: delegate build/test tasks to Gradle for consistent environments

## Contributing

Contributions are welcome! Please:

* Open an issue for bugs or suggestions
* Submit a pull request with a clear description and motivation
* Follow a consistent code style
* Use small, focused commits with meaningful messages

## License

This project is licensed under the MIT license. See the [LICENSE](./LICENSE) file for details.

## Support

* For problems or questions, open an issue in the tracker
* When reporting bugs, please attach logs and specify the Minecraft + loader version

```
