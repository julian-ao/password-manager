module ui {
    requires core;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens ui to javafx.graphics, javafx.fxml;
    exports ui;
}
