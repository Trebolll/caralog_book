package nsk.javafx.cataloguebook.interfaces.impls;
//api к BD

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nsk.javafx.cataloguebook.db.SQLiteConnection;
import nsk.javafx.cataloguebook.interfaces.СatalogueBook;
import nsk.javafx.cataloguebook.objects.Person;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteСatalogueBook implements СatalogueBook {

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public SQLiteСatalogueBook() {
        personList = findAll();
    }

    @Override
    public boolean add(Person person) {

        String query = String.format("insert into person(fio, phone) values (?, ?)");

        try (Connection con = SQLiteConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, person.getFio());
            statement.setString(2, person.getPhone());


            int result = statement.executeUpdate();
            if (result > 0) {
                int id = statement.getGeneratedKeys().getInt(1);
                person.setId(id);
                personList.add(person);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteСatalogueBook.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }


    @Override
    public boolean update(Person person) {
        String query = String.format("update person set fio=?, phone=? where id=?");

        try (Connection con = SQLiteConnection.getConnection();
             PreparedStatement statement = con.
                     prepareStatement(query);) {
            statement.setString(1, person.getFio());
            statement.setString(2, person.getPhone());
            statement.setInt(3, person.getId());

            int result = statement.executeUpdate();
            if (result > 0) {
                return true;
            }
            }catch(SQLException e){
                Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        return false;
        }

    @Override
    public boolean delete(Person person) {

        String query = String.format("delete from person where id=");


        try(Connection con = SQLiteConnection.getConnection();
            Statement statement = con.createStatement();){
            int result = statement.executeUpdate(query + person.getId());

            if(result>0){
                personList.remove(person);
                return true;
            }
        }catch (SQLException ex){
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE,null,ex);
        }
        return false;
    }

    @Override
    public ObservableList<Person> findAll() {

        String query = String.format("select * from person");

        personList.clear();

       try(Connection connection = SQLiteConnection.getConnection();
           Statement statement = connection.createStatement();
           ResultSet rs = statement.executeQuery(query);){

           while (rs.next()){
               Person person = new Person();
               person.setId(rs.getInt("id"));
               person.setFio(rs.getString("fio"));
               person.setPhone(rs.getString("phone"));
               personList.add(person);
           }

       } catch (SQLException ex) {
           Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE,null,ex);
       }
       return personList;

    }

    @Override
    public ObservableList<Person> find(String text) {

        String query = String.format("select * from person where fio like? or phone like?");


        personList.clear();

        try(Connection connection = SQLiteConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
            ;){

            String searchStr = "%" + text + "%";
            statement.setString(1,searchStr);
            statement.setString(2,searchStr);

            ResultSet rs = statement.executeQuery();


            while (rs.next()){
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setFio(rs.getString("fio"));
                person.setPhone(rs.getString("phone"));
                personList.add(person);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE,null,ex);
        }
        return personList;
    }

    public ObservableList<Person> getPersonList(){
        return personList;
    }
}
