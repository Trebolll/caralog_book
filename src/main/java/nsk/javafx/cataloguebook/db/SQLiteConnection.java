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

        try {
            if (connection == null || connection.isClosed()) {
                try {
                    String username = "postgres";
                    String password = "rootroot";
                    String url = "jdbc:sqlite:D:\\Backend\\catalogue-book\\db" + File.separator + "addressbook.db";
                    connection = DriverManager.getConnection(url,username,password);

                } catch (SQLException ex) {
                    Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}
