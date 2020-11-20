package hu.elte.inf.statistics.Helpers;

import hu.elte.inf.statistics.Models.Course;
import hu.elte.inf.statistics.Models.CourseReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DummyDatabase {
    private final HashMap<String, Course> data = new HashMap<>();

    private static DummyDatabase database;

    public static DummyDatabase getDatabase() {
        if (database == null) database = new DummyDatabase();
        return database;
    }

    private DummyDatabase() {}

    public void createNewCourse(String courseName) {
        this.data.put(courseName, new Course(courseName));
    }

    public void addReport(CourseReport report) {
        if (!this.data.containsKey(report.getCourseName())) {
            createNewCourse(report.getCourseName());
        }
        this.data.get(report.getCourseName()).addReport(report);
    }

    public List<Course> getAll() {
        return new ArrayList<>(this.data.values());
    }

    public double getAverageDifficulty(String courseName) {
        if (data.containsKey(courseName)) return -1.0;
        return this.data.get(courseName).getAverageDifficulty();
    }

    public double getAverageUsefulness(String courseName) {
        if (data.containsKey(courseName)) return -1.0;
        return this.data.get(courseName).getAverageUsefulness();
    }

    ArrayList<String> getFeedback(String courseName) {
        if (data.containsKey(courseName)) return null;
        return this.data.get(courseName).getComments();
    }
}
