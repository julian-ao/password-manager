module com.example.gruppeprosjekt1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gruppeprosjekt1 to javafx.fxml;
    exports com.example.gruppeprosjekt1;
}