package at.hakimst.dataaccess;

import at.hakimst.domain.Course;
import java.sql.Date;

import java.util.List;

public interface MyCourseRepository extends BaseRepository<Course, Long>{
    List<Course> findAllCoursesByName(String name);
    List<Course> findAllCoursesByDescription(String description);
    List<Course> findAllCoursesByNameOrDescription(String searchText);
    List<Course> findAllCoursesByCourseType(String courseType);
    List<Course> findAllCoursesByStartDate(Date startDate);
    List<Course> findAllRunningCourses();

}
