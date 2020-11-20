package hu.elte.inf.statistics.DAO;

import hu.elte.inf.statistics.Models.Staff;
import hu.elte.inf.statistics.Models.StaffReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/** Data access object for Staff */
public class StaffDAO {

    static final String CUR_DIR = System.getProperty("user.dir");
    static final String DB_URL = "jdbc:h2:file:" + CUR_DIR + "/data/courses/courses";

    private Connection conn = null;

    /** Constructor of StaffDAO */
    public StaffDAO() {
        try {
            this.conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * adds report i
     *
     * @param report
     * @return false if database contains report, true otherwise
     */
    public boolean addStaffReport(StaffReport report) {

        if (!this.contains(report.getFullName())) return false;

        String cName = report.getFullName();
        int communicationDataCount = this.getCommunicationDataCount(cName);
        int teachingDataCount = this.getTeachingDataCount(cName);

        this.setAverageCommunicationSkills(
                cName,
                (this.getAverageCommunicationSkills(cName) * communicationDataCount
                                + report.getCommunicationSkills())
                        / (communicationDataCount + 1));
        this.setAverageTeachingQuality(
                cName,
                (this.getAverageTeachingQuality(cName) * teachingDataCount
                                + report.getTeachingQuality())
                        / (teachingDataCount + 1));
        this.setCommunicationDataCount(cName, this.getCommunicationDataCount(cName) + 1);
        this.setTeachingDataCount(cName, this.getTeachingDataCount(cName) + 1);

        return true;
    }

    /**
     * Adds Staff in database
     *
     * @param staff
     * @return true if database doesn't contain given staff already, false otherwise
     */
    public boolean addStaff(Staff staff) {

        if (this.contains(staff.getFullName())) return false;

        String query = "insert into STAFF values ( ?, ?, ?, ?, ? )";

        try {
            PreparedStatement st = null;
            ResultSet res = null;

            st = conn.prepareStatement(query);
            st.setString(1, staff.getFullName());
            st.setDouble(2, staff.getAverageCommunicationSkills());
            st.setInt(3, staff.getCommunicationDataCount());
            st.setDouble(4, staff.getAverageTeachingQuality());
            st.setInt(5, staff.getTeachingDataCount());
            st.executeUpdate();

            if (res == null || !res.next()) {
                if (res != null) res.close();
                if (st != null) st.close();
                return false;
            }

            if (res != null) res.close();
            if (st != null) st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    /**
     * @param name
     * @return staff if database contains staff with given name, null otherwise
     */
    public Staff getStaffByName(String name) {
        if (!this.contains(name)) return null;
        Staff staff =
                new Staff(
                        name,
                        this.getAverageCommunicationSkills(name),
                        this.getCommunicationDataCount(name),
                        this.getAverageTeachingQuality(name),
                        this.getTeachingDataCount(name));
        return staff;
    }

    /**
     * removes staff if database contains it
     *
     * @param staffName
     * @return true if deletion is successful, false otherwise
     */
    public boolean removeStaff(String staffName) {
        if (!this.contains(staffName)) return false;
        String query = "delete from staff where full_name = ?";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, staffName);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * @param staffName
     * @return true if database contains given staff, false otherwise
     */
    public boolean contains(String staffName) {
        String query = "select * from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, staffName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        boolean exists = false;
        try {
            if (res.next()) exists = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (res != null) res.close();
            if (st != null) st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return exists;
    }

    /** @return List of staff from database */
    public List<Staff> getAll() {
        String query = "select * from staff";
        PreparedStatement st = null;
        List<Staff> lst = new ArrayList<>();
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String name = "";
        double averageCommunicationSkills = 0.0;
        double averageTeachingQuality = 0.0;
        int communicationDataCount = 0;
        int teachingDataCount = 0;
        try {
            while (res != null && res.next()) {
                name = res.getString("full_name");
                averageCommunicationSkills = res.getDouble("average_communication");
                communicationDataCount = res.getInt("communication_count");
                averageTeachingQuality = res.getDouble("average_teaching");
                teachingDataCount = res.getInt("teaching_count");
                Staff staff =
                        new Staff(
                                name,
                                averageCommunicationSkills,
                                communicationDataCount,
                                averageTeachingQuality,
                                teachingDataCount);
                lst.add(staff);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (res != null) res.close();
            if (st != null) st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lst;
    }

    /**
     * removes all the staffs from the database
     *
     * @return true if deletion is successful
     */
    public boolean removeAll() {
        String query = "delete from staff";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(query);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    /**
     * @param staffName
     * @return communication data amount
     */
    public int getCommunicationDataCount(String staffName) {
        String query = "select communication_count from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, staffName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int val = -1;
        try {
            if (res.next()) val = res.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (res != null) res.close();
            if (st != null) st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return val;
    }

    /**
     * Setter for communication Data amount
     *
     * @param staffName
     * @param communicationData
     */
    public void setCommunicationDataCount(String staffName, int communicationData) {
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

        if (st != null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @param staffName
     * @return teaching data amount
     */
    public int getTeachingDataCount(String staffName) {
        String query = "select teaching_count from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, staffName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int val = -1;
        try {
            if (res.next()) val = res.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (res != null) res.close();
            if (st != null) st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return val;
    }

    /**
     * Setter for teaching data amount
     *
     * @param staffName
     * @param teachingData
     */
    public void setTeachingDataCount(String staffName, int teachingData) {
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

        if (st != null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @param staffName
     * @return average level of communication skills
     */
    public double getAverageCommunicationSkills(String staffName) {
        String query = "select average_communication from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, staffName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        double val = -1;
        try {
            if (res.next()) val = res.getDouble(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (res != null) res.close();
            if (st != null) st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return val;
    }

    /**
     * Setter for average communication skills
     *
     * @param staffName
     * @param averageCommunicationSkills
     */
    public void setAverageCommunicationSkills(String staffName, double averageCommunicationSkills) {
        String query = "update  staff set average_communication = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averageCommunicationSkills);
            st.setString(2, staffName);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @param staffName
     * @return average level of teaching quality
     */
    public double getAverageTeachingQuality(String staffName) {
        String query = "select average_teaching from staff where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, staffName);
            res = st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        double val = -1;
        try {
            if (res.next()) val = res.getDouble(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (res != null) res.close();
            if (st != null) st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return val;
    }

    /**
     * Setter for average level of teaching quality
     *
     * @param staffName
     * @param averageTeachingQuality
     */
    public void setAverageTeachingQuality(String staffName, double averageTeachingQuality) {
        String query = "update  staff set average_teaching = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averageTeachingQuality);
            st.setString(2, staffName);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
