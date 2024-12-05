package at.hakimst.ui;

import at.hakimst.dataaccess.DatabaseException;
import at.hakimst.dataaccess.MyCourseRepository;
import at.hakimst.dataaccess.MySqlCourseRepository;
import at.hakimst.domain.Course;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Cli {

    Scanner scan;
    MyCourseRepository repo;

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
                    showallCourses();
                    break;
                    case "3":
                        showCourseDetails();
                case "x":
                    System.out.println("Auf Wiedersehen");
                    break;
                default:
                    inputError();
            }
        }
        scan.close();
    }

    private void showCourseDetails() {
        System.out.println("Für welchen Kurs möchten Sie die Kursdetails anzeigen?");
        Long courseId = Long.parseLong(scan.nextLine());
        try{
            Optional<Course> courseOptional = repo.getById(courseId);
            if(courseOptional.isPresent()){
                System.out.println(courseOptional.get());
            } else {
                System.out.println("Kurs mit der ID " + courseId + " nicht gefunden!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Kurs-Detailanzeige: " + databaseException.getMessage());
        } catch (Exception exception){
            System.out.println("Unbekannter Fehler bei Kurs-Detailanzeige: " + exception.getMessage());
        }
    }

    private void showallCourses() {
        List<Course> list = null;

        try {
            list = repo.getAll();
            if (list.size() > 0) {
                for (Course course : list) {
                    System.out.println(course);
                }
            } else {
                System.out.println("Kursliste leer!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Anzeige aller Kurse: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter fehler bei anzeige aller Kurse: " + exception.getMessage());
        }
    }

    private void showMenue() {
        System.out.println("--------------------KURSMANAGEMENT--------------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t" + "(3) Kursdetails anzeigen \t");
        System.out.println("(x)");
    }

    private void inputError() {
        System.out.println("Bitte nur die Zahlen nur der Menüauswahl eingeben!");
    }
}
