package at.hakimst.dataaccess;

import at.hakimst.domain.Course;
import at.hakimst.domain.CourseType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlCourseRepository implements MyCourseRepository{

    private Connection con;
    public MySqlCourseRepository() throws SQLException, ClassNotFoundException {
        this.con = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem","root","");
    }

    @Override
    public List<Course> findAllCoursesByName(String name) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByDescription(String description) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByNameOrDescription(String searchText) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByCourseType(String courseType) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByStartDate(Date startDate) {
        return List.of();
    }

    @Override
    public List<Course> findAllRunningCourses() {
        return List.of();
    }

    @Override
    public Optional<Course> insert(Course entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM courses";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while (resultSet.next()) {
                courseList.add(new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("begindate"),
                        resultSet.getDate("enddate"),
                        CourseType.valueOf(resultSet.getString("coursetype"))
                        )
                );
                return courseList;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Database error occured!");
        }
        return List.of();
    }

    @Override
    public Optional<Course> update(Course entity) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
