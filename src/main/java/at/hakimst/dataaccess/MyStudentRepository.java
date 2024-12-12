package at.hakimst.dataaccess;

import at.hakimst.domain.Student;

import java.sql.SQLException;
import java.util.List;

public interface MyStudentRepository extends BaseRepository{

    void save(Student student) throws SQLException;

    void update(Student student) throws SQLException;

    void delete(int id) throws SQLException;

    Student findById(int id) throws SQLException;

    List<Student> findByLastName(String lastName) throws SQLException;

    List<Student> findByBirthYear(int year) throws SQLException;

    List<Student> findByBirthDateBetween(String start, String end) throws SQLException;
}
