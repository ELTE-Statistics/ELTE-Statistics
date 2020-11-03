package hu.elte.inf.statistics.DAO;

import hu.elte.inf.statistics.Helpers.DummyDatabase;
import hu.elte.inf.statistics.Models.Course;
import hu.elte.inf.statistics.Models.CourseReport;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;

public class CoursesDAO {

    static final String CUR_DIR = System.getProperty("user.dir");
    static final String DB_URL = "jdbc:h2:file:" + CUR_DIR + "/data/courses/courses";

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
        // TODO
        return true;
    }

    public boolean addCourse(Course course) {
        if(this.contains(course.getName()))
            return false;

        String query = "insert into COURSES values ( ?, ?, ?, ?, ? )";

        try {
            PreparedStatement st = null;
            ResultSet res = null;

            st = conn.prepareStatement(query);
            st.setString(1,course.getName());
            st.setDouble(2,course.getAverageDifficulty());
            st.setInt(3,course.getDifficultyDataCount());
            st.setDouble(4,course.getAverageUsefulness());
            st.setInt(5,course.getUsefulnessDataCount());
            res = st.executeQuery();

            if(res == null || !res.next()) {
                if(res != null)
                    res.close();
                if(st != null)
                    st.close();
                return false;
            }

            if(res != null)
                res.close();
            if(st != null)
                st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public Course getCourseByName(String name) {
        if(!this.contains(name))
            return null;
        Course cr = new Course( name,
                                this.getCourseAverageDifficulty(name),
                                this.getCourseDifficultyCount(name),
                                this.getCourseAverageUsefulness(name),
                                this.getCourseUsefulnessCount(name));
        return cr;
    }

    public boolean removeCourse(String courseName) {
        // TODO
        return false;
    }

    public boolean contains(String courseName) {
        String query = "select * from courses where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,courseName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        boolean exists = false;
        try {
            if(res.next())
                exists = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if(res != null)
                res.close();
            if(st != null)
                st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return exists;
    }

    public List<Course> getAll() {
        // TODO
        return this.database.getAll();
    }

    public boolean removeAll() {
        // TODO
        return false;
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
            if(res != null)
                res.close();
            if(st != null)
                st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return val;
    }

    public void setCourseAverageDifficulty(String courseName, double averageDifficulty)  {
        String query = "update  courses set average_difficulty = ? where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averageDifficulty);
            st.setString(2,courseName);
            st.executeUpdate();
            System.err.println("EXECUTED");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(st != null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public double getCourseAverageUsefulness(String courseName)  {
        // TODO
        return 0;
    }

    public void setCourseAverageUsefulness(String courseName, double averageDifficulty)  {
        // TODO
    }

    public int getCourseUsefulnessCount(String courseName)  {
        // TODO
        return 0;
    }

    public void setCourseUsefulnessCount(String courseName, double averageDifficulty)  {
        // TODO
    }

    public int getCourseDifficultyCount(String courseName)  {
        // TODO
        return 0;
    }

    public void setCourseDifficultyCount(String courseName, double averageDifficulty)  {
        // TODO
    }

}
