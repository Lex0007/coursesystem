package at.hakimst.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Student {

    private int studentId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Student(int studentId, String firstName, String lastName, LocalDate birthDate) {
        validateName(firstName, "Vorname");
        validateName(lastName, "Nachname");

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Student(String firstName, String lastName, LocalDate birthDate) {
        this(0, firstName, lastName, birthDate); // für Studenten ohne ID (z. B. bei Neuerstellung)
    }


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Die Student-ID muss größer als 0 sein.");
        }
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        validateName(firstName, "Vorname");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        validateName(lastName, "Nachname");
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Das Geburtsdatum muss in der Vergangenheit liegen.");
        }
        this.birthDate = birthDate;
    }


    private void validateName(String name, String field) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " darf nicht leer sein.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
