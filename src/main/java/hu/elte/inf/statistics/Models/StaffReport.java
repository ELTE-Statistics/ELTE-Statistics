package hu.elte.inf.statistics.Models;

/**
 * Represent StaffReport Class Includes: name of report, comment, communication Skills amount and
 * teaching quality amount
 */
public class StaffReport {

    private String fullName;
    private String comment;
    private double communicationSkills, teachingQuality;

    /**
     * @param fullName
     * @param comment
     * @param communicationSkills
     * @param teachingQuality Constructor staffReport with four parameters
     */
    public StaffReport(
            String fullName, String comment, int communicationSkills, int teachingQuality) {
        this.fullName = fullName;
        this.comment = comment;
        this.teachingQuality = teachingQuality;
        this.communicationSkills = communicationSkills;
    }

    /** @return name of courseReport */
    public String getFullName() {
        return fullName;
    }

    /** @return comment of courseReport */
    public String getComment() {
        return comment;
    }

    /** @return level of communication skills */
    public double getCommunicationSkills() {
        return communicationSkills;
    }

    /** @return level of teaching quality */
    public double getTeachingQuality() {
        return teachingQuality;
    }
}
