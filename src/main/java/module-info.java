module com.example.tuanq {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.tuanq to javafx.fxml;
    exports com.example.tuanq;
}