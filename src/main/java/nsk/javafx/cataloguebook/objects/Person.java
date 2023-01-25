package nsk.javafx.cataloguebook.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getFio() {
        return fio.get();
    }

    public SimpleStringProperty fioProperty() {
        return fio;
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setFio(String fio) {
        this.fio.set(fio);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty fio = new SimpleStringProperty("");
    private SimpleStringProperty phone = new SimpleStringProperty("");

    public Person(){
    }

    public Person(SimpleIntegerProperty id, SimpleStringProperty fio, SimpleStringProperty phone) {
        this.id = id;
        this.fio = fio;
        this.phone = phone;
    }

    public Person(SimpleStringProperty fio, SimpleStringProperty phone) {
        this.fio = fio;
        this.phone = phone;
    }
}
