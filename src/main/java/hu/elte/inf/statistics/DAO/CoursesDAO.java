package hu.elte.inf.statistics.DAO;

import hu.elte.inf.statistics.Helpers.DummyDatabase;
import hu.elte.inf.statistics.Models.Course;
import hu.elte.inf.statistics.Models.CourseReport;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;

public class CoursesDAO {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:file:C:\\Users\\waschbar\\Desktop\\Workspace\\ELTE-Statistics/data/courses";

    private final DummyDatabase database;
    private Connection conn = null;

    public CoursesDAO() {
        try {
            this.conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.database = DummyDatabase.getDatabase();
    }

    public boolean addCourseReport(CourseReport report) {

        return true;
    }

    public double getCourseAverageDifficulty(String courseName)  {
        String query = "select average_difficulty from courses where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,courseName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        double val = -1;
        try {
            if(res.next())
                val = res.getDouble(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            res.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return val;
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
