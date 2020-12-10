package hu.elte.inf.statistics.Models;

/**
 * Represent StaffReport Class Includes: name of report, comment, preparedness amount and
 * helpfulness amount
 */
public class StaffReport {

    private String fullName;
    private String comment;
    private double preparedness, helpfulness;

    /**
     * Constructor staffReport with four parameters
     *
     * @param fullName
     * @param comment
     * @param preparedness
     * @param helpfulness
     */
    public StaffReport(String fullName, String comment, int preparedness, int helpfulness) {
        this.fullName = fullName;
        this.comment = comment;
        this.helpfulness = helpfulness;
        this.preparedness = preparedness;
    }

    /** @return name of courseReport */
    public String getFullName() {
        return fullName;
    }

    /** @return comment of courseReport */
    public String getComment() {
        return comment;
    }

    /** @return level of preparedness */
    public double getPreparedness() {
        return preparedness;
    }

    /** @return level of helpfulness */
    public double getHelpfulness() {
        return helpfulness;
    }
}
