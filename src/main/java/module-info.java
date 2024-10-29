module com.example.f1p1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.f1p1 to javafx.fxml;
    exports com.example.f1p1;
}