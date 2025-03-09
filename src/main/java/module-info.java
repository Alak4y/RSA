module com.lecc.rsa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lecc.rsa to javafx.fxml;
    exports com.lecc.rsa;
}