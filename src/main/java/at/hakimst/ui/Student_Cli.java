package at.hakimst.ui;

import at.hakimst.dataaccess.MySqlStudentRepository;
import at.hakimst.domain.Student;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Student_Cli {

    private MySqlStudentRepository studentRepository;

    public Student_Cli(MySqlStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void start() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Student hinzufügen");
            System.out.println("2. Studenten nach Nachnamen suchen");
            System.out.println("3. Beenden");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Eingabe-Puffer leeren

            switch (choice) {
                case 1:
                    System.out.print("Vorname: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Nachname: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Geburtsdatum (YYYY-MM-DD): ");
                    String birthDate = scanner.nextLine();

                    Student student = new Student(firstName, lastName, LocalDate.parse(birthDate));
                    studentRepository.save(student);
                    System.out.println("Student hinzugefügt: " + student);
                    break;

                case 2:
                    System.out.print("Nachname: ");
                    String nameSearch = scanner.nextLine();
                    List<Student> students = studentRepository.findByLastName(nameSearch);
                    System.out.println("Gefundene Studenten: " + students);
                    break;

                case 3:
                    System.out.println("Programm beendet.");
                    return;

                default:
                    System.out.println("Ungültige Option.");
            }
        }
    }
}