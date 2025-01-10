# Building Standalone MacOS Executable (For use without Java)

⚠️ **WARNING: This branch is for MacOS builds only**

## Requirements

- MacOS Operating System
- [Maven](https://maven.apache.org/install.html) - For Java dependency management
- [GraalVM](https://www.graalvm.org/downloads/) - Native Image capability
- [Liberica JDK](https://bell-sw.com/pages/downloads/) - For AWT/Swing support

## Technologies Used

- Maven: Compiles and packages source code with its dependencies into executable JAR file
- GraalVM: Generates MacOS native executable
- Liberica JDK: Provides AWT and Swing support for native image

## Build Instructions

1. Build the JAR:
```bash
mvn clean package
```

2. Create native executable:
```bash
bash build-image.sh
```

## Product

The generated executable is completely standalone and can be shared with other MacOS users. Recipients do not need:
- Java Runtime Environment (JRE)
- Java Development Kit (JDK)
- Any additional dependencies

Simply share the executable file and it will run natively on any MacOS system.