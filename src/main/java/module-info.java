module poo.rummikub {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens poo.rummikub to javafx.fxml;
    exports poo.rummikub;
}