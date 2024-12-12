package at.hakimst.dataaccess;

import at.hakimst.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository {

    private Connection connection;

    public MySqlStudentRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Student student) throws SQLException {
        String sql = "INSERT INTO students (first_name, last_name, birth_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setDate(3, Date.valueOf(student.getBirthDate()));
            stmt.executeUpdate();

            // Automatisch generierte ID speichern
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    student.setStudentId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Student student) throws SQLException {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, birth_date = ? WHERE student_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setDate(3, Date.valueOf(student.getBirthDate()));
            stmt.setInt(4, student.getStudentId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Student findById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStudent(rs);
                }
            }
        }
        return null; // Kein Eintrag gefunden
    }

    @Override
    public List<Student> findByLastName(String lastName) throws SQLException {
        String sql = "SELECT * FROM students WHERE last_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, lastName);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapResultSetToStudents(rs);
            }
        }
    }

    @Override
    public List<Student> findByBirthYear(int year) throws SQLException {
        String sql = "SELECT * FROM students WHERE YEAR(birth_date) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapResultSetToStudents(rs);
            }
        }
    }

    @Override
    public List<Student> findByBirthDateBetween(String start, String end) throws SQLException {
        String sql = "SELECT * FROM students WHERE birth_date BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));
            try (ResultSet rs = stmt.executeQuery()) {
                return mapResultSetToStudents(rs);
            }
        }
    }

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("student_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("birth_date").toLocalDate()
        );
    }

    private List<Student> mapResultSetToStudents(ResultSet rs) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            students.add(mapResultSetToStudent(rs));
        }
        return students;
    }

    @Override
    public Optional insert(Object entity) {
        return Optional.empty();
    }

    @Override
    public Optional getById(Object id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public Optional update(Object entity) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Object id) {

    }
}
