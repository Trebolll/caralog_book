package nsk.javafx.cataloguebook.controller;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nsk.javafx.cataloguebook.Main;
import nsk.javafx.cataloguebook.db.SQLiteConnection;
import nsk.javafx.cataloguebook.interfaces.impls.SQLiteAddressBook;
import nsk.javafx.cataloguebook.objects.Person;
import nsk.javafx.cataloguebook.utils.DialogManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final SQLiteAddressBook catalogueBookImpl = new SQLiteAddressBook();
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView tableCatalogueBook;
    @FXML
    private TableColumn<Person, String> columnFIO;
    @FXML
    private TableColumn<Person, String> columnPhone;
    @FXML
    private Label labelCount;

    private Parent fxmlEdit;

    private EditDialogController editDialogController;

    private Stage editDialogStage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillData();
        initListeners();
        initLoaders();

    }

    private void fillData() {
        fillTable();
        updateCountLabel();
    }

    private void fillTable() {
        columnFIO.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
        tableCatalogueBook.setItems(catalogueBookImpl.getPersonList());
    }

    private void initListeners() {
        catalogueBookImpl.getPersonList().addListener((ListChangeListener<Person>) c -> updateCountLabel());

        tableCatalogueBook.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                btnEdit.fire();
            }
        });

    }

    private void updateCountLabel() {
        labelCount.setText("Кол-во: " + catalogueBookImpl.getPersonList().size());
    }

    private void initLoaders() {
        try {
            FXMLLoader editFxmlLoader = new FXMLLoader(Main.class.getResource("edit.fxml"));
            fxmlEdit = editFxmlLoader.load();
            editDialogController = editFxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonActionPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button clickedButton)) {
            return;
        }

        Person selectedPerson = (Person) tableCatalogueBook.getSelectionModel().getSelectedCells();

        boolean research = false;

        switch (clickedButton.getId()) {
            case "btnAdd":
                editDialogController.setPerson(new Person());
                showDialog();

                if (editDialogController.isSaveClicked()) {
                    catalogueBookImpl.add(editDialogController.getPerson());
                    research = true;
                }
                break;
            case "btnEdit":

                if (!personIsSelected(selectedPerson)) {
                    return;
                }
                editDialogController.setPerson(selectedPerson);
                showDialog();
                if (editDialogController.isSaveClicked()) {
                    catalogueBookImpl.update(selectedPerson);
                    research = true;
                }
                break;

            case "btnDelete":

                if (!personIsSelected(selectedPerson) || !(confirmDelete())) {
                    return;
                }
                research = true;
                catalogueBookImpl.delete(selectedPerson);
                break;
        }
        if(research){
            actionSearch(actionEvent);
        }
    }


    private boolean personIsSelected(Person selectedPerson){
        if(selectedPerson == null){
            DialogManager.showInfoDialog("Ошибка ", "Выберите значение");
            return false;
        }
        return true;
    }

    private void showDialog(){
        if(editDialogStage == null){
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактировать");
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
        }

        editDialogStage.showAndWait();
    }

    public void actionSearch(ActionEvent actionEvent){
        if(txtSearch.getText().trim().length()==0){
            catalogueBookImpl.findAll();
        }else {
            catalogueBookImpl.find(txtSearch.getText());
        }
    }


    private boolean confirmDelete(){
        if( DialogManager.showConfirmInfoDialog("Потдверждение", "Удалить").get() == ButtonType.OK){
            return true;
        }else{
            return false;
        }
    }


}