package hu.elte.inf.statistics.DAO;

import hu.elte.inf.statistics.Models.Staff;
import hu.elte.inf.statistics.Models.StaffReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/** Data access object for Staff */
public class StaffDAO {

    static final String CUR_DIR = System.getProperty("user.dir");
    static final String DB_URL = "jdbc:h2:file:" + CUR_DIR + "/data/staff";

    private Connection conn = null;

    /** Constructor of StaffDAO */
    public StaffDAO() {
        try {
            this.conn = DriverManager.getConnection(DB_URL);
            this.conn
                    .prepareStatement(
                            "create table if not exists staff (full_name varchar(64),"
                                + " average_preparedness float, preparedness_count int,"
                                + " average_helpfulness float, helpfulness_count int)")
                    .executeUpdate();
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
        String cName = report.getFullName();
        if (!this.contains(report.getFullName())) {
            this.addStaff(
                    new Staff(cName, report.getPreparedness(), 1, report.getHelpfulness(), 1));
            return true;
        }

        int preparednessDataCount = this.getPreparednessDataCount(cName);
        int helpfulnessDataCount = this.getHelpfulnessDataCount(cName);

        this.setAveragePreparedness(
                cName,
                (this.getAveragePreparedness(cName) * preparednessDataCount
                                + report.getPreparedness())
                        / (preparednessDataCount + 1));
        this.setAverageHelpfulness(
                cName,
                (this.getAverageHelpfulness(cName) * helpfulnessDataCount + report.getHelpfulness())
                        / (helpfulnessDataCount + 1));
        this.setPreparednessDataCount(cName, this.getPreparednessDataCount(cName) + 1);
        this.setHelpfulnessDataCount(cName, this.getHelpfulnessDataCount(cName) + 1);

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
            st.setDouble(2, staff.getAveragePreparedness());
            st.setInt(3, staff.getPreparednessDataCount());
            st.setDouble(4, staff.getAverageHelpfulness());
            st.setInt(5, staff.getHelpfulnessDataCount());
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
                        this.getAveragePreparedness(name),
                        this.getPreparednessDataCount(name),
                        this.getAverageHelpfulness(name),
                        this.getHelpfulnessDataCount(name));
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
        double averagePreparedness = 0.0;
        double averageHelpfulness = 0.0;
        int preparednessDataCount = 0;
        int helpfulnessDataCount = 0;
        try {
            while (res != null && res.next()) {
                name = res.getString("full_name");
                averagePreparedness = res.getDouble("average_preparedness");
                preparednessDataCount = res.getInt("preparedness_count");
                averageHelpfulness = res.getDouble("average_helpfulness");
                helpfulnessDataCount = res.getInt("helpfulness_count");
                Staff staff =
                        new Staff(
                                name,
                                averagePreparedness,
                                preparednessDataCount,
                                averageHelpfulness,
                                helpfulnessDataCount);
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
     * @return preparedness data amount
     */
    public int getPreparednessDataCount(String staffName) {
        String query = "select preparedness_count from staff where full_name = ?";
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
     * Setter for preparedness Data amount
     *
     * @param staffName
     * @param preparednessData
     */
    public void setPreparednessDataCount(String staffName, int preparednessData) {
        String query = "update staff set preparedness_count = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, preparednessData);
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
     * @return helpfulness data amount
     */
    public int getHelpfulnessDataCount(String staffName) {
        String query = "select helpfulness_count from staff where full_name = ?";
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
     * Setter for helpfulness data amount
     *
     * @param staffName
     * @param helpfulnessData
     */
    public void setHelpfulnessDataCount(String staffName, int helpfulnessData) {
        String query = "update staff set helpfulness_count = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, helpfulnessData);
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
     * @return average level of preparedness
     */
    public double getAveragePreparedness(String staffName) {
        String query = "select average_preparedness from staff where full_name = ?";
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
     * Setter for average preparedness
     *
     * @param staffName
     * @param averagePreparedness
     */
    public void setAveragePreparedness(String staffName, double averagePreparedness) {
        String query = "update  staff set average_preparedness = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averagePreparedness);
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
     * @return average level of helpfulness
     */
    public double getAverageHelpfulness(String staffName) {
        String query = "select average_helpfulness from staff where full_name = ?";
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
     * Setter for average level of helpfulness
     *
     * @param staffName
     * @param averageHelpfulness
     */
    public void setAverageHelpfulness(String staffName, double averageHelpfulness) {
        String query = "update  staff set average_helpfulness = ? where full_name = ?";
        PreparedStatement st = null;
        ResultSet res = null;
        try {
            st = conn.prepareStatement(query);
            st.setDouble(1, averageHelpfulness);
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
