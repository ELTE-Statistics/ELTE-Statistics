package hu.elte.inf.statistics.DAO;

import hu.elte.inf.statistics.Helpers.DummyDatabase;
import hu.elte.inf.statistics.Models.Course;
import hu.elte.inf.statistics.Models.CourseReport;

import java.util.List;

public class CoursesDAO {

    DummyDatabase database;

    public CoursesDAO() {
        database = DummyDatabase.getDatabase();
    }

    public boolean addCourseReport(CourseReport report) {
        return false;
    }

    public boolean addCourse(Course course) {
        return false;
    }

    public Course getCourseByName(String name) {
        return null;
    }

    public boolean removeCourse(String courseName) {
        return false;
    }

    public boolean contains(String courseName) {
        return false;
    }

    public List<Course> getAll() {
        return null;
    }

    public boolean removeALl() {
        return false;
    }
}
