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
    public int getCommunicationDataCount(String staffName){
        String query = "select communication_count from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,staffName);
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
    public void setCommunicationDataCount(String staffName, int communicationData){
        String query = "update  staff set communication_count = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, communicationData);
            st.setString(2, staffName);
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


    public int getTeachingDataCount(String staffName){
        String query = "select teaching_count from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,staffName);
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

    public void setTeachingDataCount(String staffName, int teachingData){
        String query = "update  staff set teaching_count = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, teachingData);
            st.setString(2, staffName);
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


    public double getAverageCommunicationSkills(String staffName){
        String query = "select average_communication from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,staffName);
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

    public void setAverageCommunicationSkills(String staffName, double averageCommunicationSkills){
        String query = "update  staff set average_communication = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averageCommunicationSkills);
            st.setString(2,staffName);
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


    public double getAverageTeachingQuality(String staffName){
        String query = "select average_teaching from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1,staffName);
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

    public void setAverageTeachingQuality(String staffName, double averageTeachingQuality){
        String query = "update  staff set average_teaching = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averageTeachingQuality);
            st.setString(2,staffName);
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
