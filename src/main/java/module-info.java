module nsk.javafx.cataloguebook {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens nsk.javafx.cataloguebook to javafx.fxml;
    exports nsk.javafx.cataloguebook;
}