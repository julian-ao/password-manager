module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens app to javafx.fxml;
    opens app.database to com.fasterxml.jackson.databind;
    exports app.database to com.fasterxml.jackson.databind;
    exports app;
}