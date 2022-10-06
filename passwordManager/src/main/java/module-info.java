module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens app to javafx.fxml;
    exports app;
}