module com.example {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive java.sql;
    requires jbcrypt;

    opens com.example to javafx.fxml;
    exports com.example;
}
