package hu.elte.inf.statistics.Models;

public class StaffReport {

    private String fullName;
    private String comment;
    private double communicationSkills, teachingQuality;

    public StaffReport(String fullName, String comment, int communicationSkills, int teachingQuality) {
        this.fullName = fullName;
        this.comment = comment;
        this.teachingQuality = teachingQuality;
        this.communicationSkills = communicationSkills;
    }

    public String getFullName() {
        return fullName;
    }

    public String getComment() {
        return comment;
    }

    public double getCommunicationSkills() {
        return communicationSkills;
    }

    public double getTeachingQuality() {
        return teachingQuality;
    }
}
