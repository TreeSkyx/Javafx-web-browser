# [OOP Project] - Javafx Web Browser
This project is the part of Object Oriented Programming class - CE, KMITL

## Information
- This project is developed using Java SE & JDK 17 / JavaFx 17
- Web browser-based on Java WebView & WebEngine

## Features
- Search bar (opening URL)
- Go next/back (web history)
- Zoom in / out 
- View the source page (HTML)
- Web loading status
- Change the title for the specified webpage

## Suggestion
You might have some errors when running code with Javafx

You need to add vmArgs configuration 
```
"vmArgs": "--module-path <path-to-javafx-lib> --add-modules=javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web --add-reads javafx.graphics=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.charts=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio.common=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED",
```
or
```
"vmArgs": "--module-path <path-to-javafx-lib> --add-modules=javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web",
```
