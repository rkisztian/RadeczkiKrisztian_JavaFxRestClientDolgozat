module com.example.radeczkikrisztian_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.radeczkikrisztian_javafxrestclientdolgozat to javafx.fxml;
    exports com.example.radeczkikrisztian_javafxrestclientdolgozat;
}