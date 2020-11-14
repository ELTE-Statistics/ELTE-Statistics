package hu.elte.inf.statistics.DAO;

import hu.elte.inf.statistics.Models.Course;
import hu.elte.inf.statistics.Models.CourseReport;
import hu.elte.inf.statistics.Models.Staff;
import hu.elte.inf.statistics.Models.StaffReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    static final String CUR_DIR = System.getProperty("user.dir");
    static final String DB_URL = "jdbc:h2:file:" + CUR_DIR + "/data/courses/courses";

    private Connection conn = null;

    public StaffDAO() {
        try {
            this.conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // TODO: Implement all functions

    public boolean addStaffReport(StaffReport report) {
        return true;
    }

    public boolean addStaff(Staff staff) {
        return true;
    }

    public Staff getStaffByName(String name) {
        return null;
    }

    public boolean removeStaff(String staffName) {
        return false;
    }

    public boolean contains(String staffName) {
       return false;
    }

    public List<Staff> getAll() {
       return null;
    }

    public boolean removeAll() {
        return true;
    }

    // TODO: add getters and setters for each staff attribute.

}
