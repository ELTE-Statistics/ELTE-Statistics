package hu.elte.inf.statistics.DAO;

import hu.elte.inf.statistics.Helpers.DummyDatabase;
import hu.elte.inf.statistics.Models.Course;
import hu.elte.inf.statistics.Models.CourseReport;

import java.util.List;

public class CoursesDAO {

    private final DummyDatabase database;

    public CoursesDAO() {
        this.database = DummyDatabase.getDatabase();
    }

    public boolean addCourseReport(CourseReport report) {
        this.database.addReport(report);
        return true;
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
        return this.database.getAll();
    }

    public boolean removeAll() {
        return false;
    }
}
