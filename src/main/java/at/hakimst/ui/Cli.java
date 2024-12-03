package at.hakimst.ui;

import at.hakimst.dataaccess.MyCourseRepository;
import at.hakimst.dataaccess.MySqlCourseRepository;

import java.util.Scanner;

public class Cli {

    Scanner scan;
    MySqlCourseRepository repo;

    public Cli(MyCourseRepository repo) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start() {
        String input = "-";
        while(!input.equals("x")){
            showMenue();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Kurseingabe");
                    break;
                case "2":
                    System.out.println("Alle Kurse anzeigen");
                    break;
                case "x":
                    System.out.println("Auf Wiedersehen");
                    break;
                default:
                    inputError();
            }
        }
        scan.close();
    }

    private void showMenue() {
        System.out.println("--------------------KURSMANAGEMENT--------------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t");
        System.out.println("(x)");
    }

    private void inputError() {
        System.out.println("Bitte nur die Zahlen nur der Menüauswahl eingeben!");
    }
}
