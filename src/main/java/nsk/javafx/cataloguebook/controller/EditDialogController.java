package nsk.javafx.cataloguebook.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nsk.javafx.cataloguebook.objects.Person;
import nsk.javafx.cataloguebook.utils.DialogManager;

import org.w3c.dom.Text;

public class EditDialogController {
    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtFIO;

    @FXML
    private TextField txtPhone;

    private Person person;

    private boolean saveClicked = false;
    public boolean isSaveClicked() {
        return saveClicked;
    }

    public void setPerson(Person person) {
        if (person == null) {
            return;
        }
        saveClicked = false;
        this.person = person;
        txtFIO.setText(person.getFio());
        txtPhone.setText(person.getPhone());
    }


    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();// скрываем внешний контейнер диалогового окна (где находится Scene с UI)
    }

    public Person getPerson() {
        return person;
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()){
            return;
        }
        person.setFio(txtFIO.getText());
        person.setPhone(txtPhone.getText());
        saveClicked = true;
        actionClose(actionEvent);
    }

    private boolean checkValues(){
        if(txtFIO.getText().trim().length()==0||txtPhone.getText().trim().length()==0){
            DialogManager.showInfoDialog("Ошибка", "Заполните поле");
            return false;
        }
        return true;
    }

}
