package at.hakimst;

import at.hakimst.dataaccess.MySqlCourseRepository;
import at.hakimst.dataaccess.MysqlDatabaseConnection;
import at.hakimst.ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Cli myCli = null;
        try {
            myCli = new Cli(new MySqlCourseRepository());
            myCli.start();
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage() + "SQL State: " + e.getSQLState());
        } catch (ClassNotFoundException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }



    }
}