module ui {
  requires core;
  requires client;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;
  requires transitive org.json;

  opens ui to javafx.graphics, javafx.fxml;
}
