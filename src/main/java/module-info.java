module nsk.javafx.cataloguebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens nsk.javafx.cataloguebook.controller to javafx.fxml;
    //opens nsk.javafx.cataloguebook.objects to javafx.base;


    exports nsk.javafx.cataloguebook;
  //  exports nsk.javafx.cataloguebook.controller;
}