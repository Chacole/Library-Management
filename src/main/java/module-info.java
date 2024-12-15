module com.example.tuanq {
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.dlsc.formsfx;
    requires org.json;
    requires java.desktop;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires javafx.base;

    opens com.example.tuanq to javafx.fxml;
    opens com.example.tuanq.admin to javafx.fxml;
    opens com.example.tuanq.customer to javafx.fxml;
    opens com.example.tuanq.designpattern.Command to javafx.fxml;

    exports com.example.tuanq;
    exports com.example.tuanq.admin;
    exports com.example.tuanq.customer;
    exports com.example.tuanq.designpattern.Command;
}