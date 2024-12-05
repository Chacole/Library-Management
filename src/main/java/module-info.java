module com.example.tuanq {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.dlsc.formsfx;
    requires org.json;
    requires java.desktop;

    opens com.example.tuanq to javafx.fxml;
    exports com.example.tuanq;
}