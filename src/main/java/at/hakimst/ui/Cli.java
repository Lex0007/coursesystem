package at.hakimst.ui;

import at.hakimst.dataaccess.DatabaseException;
import at.hakimst.dataaccess.MyCourseRepository;
import at.hakimst.dataaccess.MySqlCourseRepository;
import at.hakimst.domain.Course;
import at.hakimst.domain.CourseType;
import at.hakimst.domain.InvalidValueException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.Date;

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
                    addCourse();
                    break;
                case "2":
                    showallCourses();
                    break;
                    case "3":
                    showCourseDetails();
                    break;
                case "4":
                    updateCourseDetails();
                    break;
                case "5":
                    deleteCourse();
                    break;
                case "6":
                    courseSearch();
                    break;
                case "7":
                    runningCourses();
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

    private void runningCourses() {
        System.out.println("Aktuell laufende Kurse: ");
        List<Course> list = new ArrayList<>();
        try {
            list = repo.findAllRunningCourses();
            for (Course course : list) {
                System.out.println(course);
            }
        } catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler bei Kurs-Anzeige für laufende Kurse: " + databaseException.getMessage());
        } catch (Exception exception){
            System.out.println("Unbekannter Fehler bei Kurs-Anzeige für laufende Kurse: " + exception.getMessage());
        }
    }

    private void courseSearch() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Course> courseList;
        try {
            courseList = repo.findAllCoursesByNameOrDescription(searchString);
            for (Course course : courseList) {
                System.out.println(course);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Kurssuche: "+ databaseException.getMessage());
        } catch (Exception exception){
            System.out.println("Unbekannte Fehler bie der Kurssuche: "+ exception.getMessage());
        }
    }

    private void deleteCourse() {
        System.out.println("Welchen Kurs möchten Sie löschen? Bitte ID eingeben:");
        Long courseIdToDelete = Long.parseLong(scan.nextLine());

        try {
            repo.deleteById(courseIdToDelete);

        }catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler beim Löschen: " + databaseException.getMessage());
        }catch (Exception e){
            System.out.println("Unbekannter Fehler beim Löschen: " + e.getMessage());
        }
    }

    private void updateCourseDetails() {
        System.out.println("für welche Kurs-ID möchten Sie die Kursdetails ändern?");
        Long courseId = scan.nextLong();

        try {
            Optional<Course> courseOptional = repo.getById(courseId);
            if (courseOptional.isEmpty()) {
                System.out.println("Kurs mit der gegebenen ID nicht in der Datenbank!");
            } else {
                Course course = courseOptional.get();

                System.out.println("Änderungen für folgenden Kurs: ");
                System.out.println(course);

                String name, description, hours, dateFrom, dateTo, courseType;

                System.out.println("Bitte neue Kursdaten angeben (Enter, falls keine Änderungen gewünscht ist): ");
                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("Beschreibung: ");
                description = scan.nextLine();
                System.out.println("Stundenanzahl: ");
                hours = scan.nextLine();
                System.out.println("Startdatum (YYYY-MM-DD): ");
                dateFrom = scan.nextLine();
                System.out.println("Enddatum (YYYY-MM-DD): ");
                dateTo = scan.nextLine();
                System.out.println("Kurstyp (ZA/BF/FF/OE): ");
                courseType = scan.nextLine();

                Optional<Course> optionalCourseUpdated = repo.update(
                        new Course(
                                course.getId(),
                                name.equals("") ? course.getName() : name,
                                description.equals("") ? course.getDescription() : description,
                                hours.equals("") ? course.getHours():Integer.parseInt(hours),
                                dateFrom.equals("") ? course.getBeginDate():Date.valueOf(dateFrom),
                                dateTo.equals("")? course.getEndDate():Date.valueOf(dateTo),
                                courseType.equals("")? course.getCourseType():CourseType.valueOf(courseType)
                        )
                );

                optionalCourseUpdated.ifPresentOrElse(
                        (c)-> System.out.println("Kurs aktualisiert: " + c),
                        ()-> System.out.println("Kurs konnte nicht aktualisiert werden!")
                );

            }
        } catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException){
            System.out.println("Kursdaten nicht korrekt angegeben: "+ invalidValueException.getMessage());
        } catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler beim Einfügen: "+ databaseException.getMessage());
        } catch (Exception exception)  {
            System.out.println("Fehler beim Einfügen: " + exception.getMessage());
        }

    }

    private void addCourse() {

        String name, description;
        int hours;
        Date dateFrom, dateTo;
        CourseType courseType;

        try
        {
            System.out.println("Bitte alle Kursdaten angeben:");
            System.out.println("Name: ");
            name = scan.nextLine();
            if (name.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Beschreibung: ");
            description = scan.nextLine();
            if (description.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer!");
            System.out.println("Stundenanzahl: ");
            hours = Integer.parseInt(scan.nextLine());
            System.out.println("Startdatum (YYYY-MM-DD): ");
            dateFrom = Date.valueOf(scan.nextLine());
            System.out.println("Enddatum (YYYY-MM-DD): ");
            dateTo = Date.valueOf(scan.nextLine());
            System.out.println("Kurstyp: (ZA/BF/FF/OE): ");
            courseType = CourseType.valueOf(scan.nextLine());

            Optional<Course> optionalCourse = repo.insert(
                    new Course(name, description, hours, dateFrom, dateTo, courseType)
            );

            if (optionalCourse.isPresent()) {
                System.out.println("Kurs angelegt: " + optionalCourse.get());
            } else {
                System.out.println("Kurs konnte nicht angelegt werden!");
            }

        } catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException){
            System.out.println("Kursdaten nicht korrekt angegeben: "+ invalidValueException.getMessage());
        } catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler beim Einfügen: "+ databaseException.getMessage());
        } catch (Exception exception)  {
            System.out.println("Fehler beim Einfügen: " + exception.getMessage());
        }
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
        System.out.println("(4) Kurs ändern \t (5) Kurs löschen \t" + "(6) Kurssuche \t");
        System.out.println("(7) Laufende Kurse \t (-) ----- \t" + "(-) ---- \t");
        System.out.println("(x) ENDE");
    }

    private void inputError() {
        System.out.println("Bitte nur die Zahlen nur der Menüauswahl eingeben!");
    }
}
