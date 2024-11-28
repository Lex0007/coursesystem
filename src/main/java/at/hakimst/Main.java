package at.hakimst;

import at.hakimst.dataaccess.MysqlDatabaseConnection;
import at.hakimst.ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Cli myCli = new Cli();
        myCli.start();

        try {
            Connection myConnection = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "");
            System.out.println("Verbindung aufgebaut");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}