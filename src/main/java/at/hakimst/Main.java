package at.hakimst;

import at.hakimst.dataaccess.MysqlDatabaseConnection;
import at.hakimst.ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Cli myCli = new Cli();
        myCli.start();


    }
}