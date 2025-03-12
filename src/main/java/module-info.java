module com.demir.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.xml.crypto;


    opens com.demir.javafx to javafx.fxml;
    exports com.demir.javafx;
}