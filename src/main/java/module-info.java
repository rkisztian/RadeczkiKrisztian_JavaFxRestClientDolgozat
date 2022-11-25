module com.example.radeczkikrisztian_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.radeczkikrisztian_javafxrestclientdolgozat to javafx.fxml, com.google.gson;
    exports com.example.radeczkikrisztian_javafxrestclientdolgozat;
}