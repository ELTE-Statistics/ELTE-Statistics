package hu.elte.inf.statistics.DAO;

import hu.elte.inf.statistics.Helpers.DummyDatabase;
import hu.elte.inf.statistics.Models.Course;
import hu.elte.inf.statistics.Models.CourseReport;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for Course
 */
public class CoursesDAO {

    static final String CUR_DIR = System.getProperty("user.dir");
    static final String DB_URL = "jdbc:h2:file:" + CUR_DIR + "/data/courses/courses";

    private Connection conn = null;

    /**
     * Constructor of CoursesDAO
     */
    public CoursesDAO() {
        try {
            this.conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Adds course Report
     * @param report
     * @return false if database contains report, true otherwise
     */
    public boolean addCourseReport(CourseReport report) {
        if(!this.contains(report.getCourseName()))
            return false;

        String cName = report.getCourseName();
        int diffCount = this.getCourseDifficultyCount(cName);
        int usflCount = this.getCourseUsefulnessCount(cName);

        this.setCourseAverageDifficulty(cName,
                (this.getCourseAverageDifficulty(cName) * diffCount + report.getDifficulty()) / (diffCount + 1));
        this.setCourseAverageUsefulness(cName,
                (this.getCourseAverageUsefulness(cName) * usflCount + report.getUsefulness()) / (usflCount + 1) );
        this.setCourseDifficultyCount(cName,
                this.getCourseDifficultyCount(cName) + 1);
        this.setCourseUsefulnessCount(cName,
                this.getCourseUsefulnessCount(cName) + 1);

        return true;
    }

    /**
     * Adds course in database
     * @param course
     * @return true if database doesn't contain given course already, false otherwise
     */
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
            st.executeUpdate();

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

    /**
     * @param name
     * @return Course if database contains course with
     * given name, null otherwise
     */
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

    /**
     * removes course if database contains it
     * @param courseName
     * @return true if database contains given course false otherwise
     */
    public boolean removeCourse(String courseName) {
        if(!this.contains(courseName))
            return false;
        String query = "delete from courses where course_name = ?";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,courseName);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * @param courseName
     * @return true if database contains given course
     * false otherwise
     */
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

    /**
     * @return List of courses from database
     */
    public List<Course> getAll() {
        String query = "select * from courses";
        PreparedStatement st = null;
        List<Course> lst = new ArrayList<>();
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            res = st.executeQuery();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        String name = "";
        double average_difficulty = 0.0;
        double average_usefulness = 0.0;
        int difficulty_count = 0;
        int usefulness_count = 0;
        try {
            while(res != null && res.next()){
                name = res.getString("course_name");
                average_difficulty = res.getDouble("average_difficulty");
                difficulty_count = res.getInt("difficulty_count");
                average_usefulness = res.getDouble("average_usefulness");
                usefulness_count = res.getInt("usefulness_count");
                Course course = new Course(name,average_difficulty,difficulty_count,average_usefulness,usefulness_count);
                lst.add(course);
            }
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
        return lst;
    }

    /**
     * removes all the courses from the database
     * @return true if deletion is successful
     */
    public boolean removeAll() {
        String query = "delete from courses";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(query);
            st.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }

    /**
     * @param courseName
     * @return course average level of difficulty
     */
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

    /**
     * @param courseName
     * @param averageDifficulty
     * Setter for course average difficulty
     */
    public void setCourseAverageDifficulty(String courseName, double averageDifficulty)  {
        String query = "update  courses set average_difficulty = ? where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averageDifficulty);
            st.setString(2,courseName);
            st.executeUpdate();
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

    /**
     * @param courseName
     * @return course average level of usefulness
     */
    public double getCourseAverageUsefulness(String courseName)  {
        String query = "select average_usefulness from courses where course_name = ?";
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

    /**
     * Setter for course average level of usefulness
     * @param courseName
     * @param averageUsefulness
     */
    public void setCourseAverageUsefulness(String courseName, double averageUsefulness)  {
        String query = "update  courses set average_usefulness = ? where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averageUsefulness);
            st.setString(2,courseName);
            st.executeUpdate();
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

    /**
     * @param courseName
     * @return course level of usefulness
     */
    public int getCourseUsefulnessCount(String courseName)  {
        String query = "select usefulness_count from courses where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,courseName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int val = -1;
        try {
            if(res.next())
                val = res.getInt(1);
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

    /**
     * Setter for course usefulness level
     * @param courseName
     * @param usefulnessCount
     */
    public void setCourseUsefulnessCount(String courseName, int usefulnessCount)  {
        String query = "update  courses set usefulness_count = ? where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, usefulnessCount);
            st.setString(2,courseName);
            st.executeUpdate();
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

    /**
     * @param courseName
     * @return  difficulty level of course
     */
    public int getCourseDifficultyCount(String courseName)  {
        String query = "select difficulty_count from courses where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,courseName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int val = -1;
        try {
            if(res.next())
                val = res.getInt(1);
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

    /**
     * Setter for course difficulty level
     * @param courseName
     * @param difficultyCount
     */
    public void setCourseDifficultyCount(String courseName, int difficultyCount)  {
        String query = "update  courses set difficulty_count = ? where course_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, difficultyCount);
            st.setString(2,courseName);
            st.executeUpdate();
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

}
