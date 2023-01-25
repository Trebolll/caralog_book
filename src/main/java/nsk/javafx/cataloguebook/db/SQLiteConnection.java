package nsk.javafx.cataloguebook.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteConnection {

    private static Connection connection;


    public static Connection getConnection() {

        if (connection == null) {
            try {
                String url = "jdbc:sqlite:db" + File.separator + "addressbook.db";
               connection = DriverManager.getConnection(url);
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }
}
