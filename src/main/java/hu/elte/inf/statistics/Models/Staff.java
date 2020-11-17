package hu.elte.inf.statistics.Models;

import java.util.ArrayList;

/**
 * Represents Staff Class
 * Staff includes: fullname,
 * information about : communication skills, teaching quality
 */
public class Staff {

    private String fullName;
    private double averageCommunicationSkills, averageTeachingQuality;
    private int communicationDataCount, teachingDataCount;
    private ArrayList<String> comments = new ArrayList<String>();

    /**
     * @param name
     * Constructor of Staff with staff name
     */
    public Staff(String name) {
        this.fullName = name;
    }

    /**
     * @param fullName
     * @param averageCommunicationSkills
     * @param communicationDataCount
     * @param averageTeachingQuality
     * @param teachingDataCount
     * constructor of Staff with five arguments
     */
    public Staff(String fullName, double averageCommunicationSkills, int communicationDataCount, double averageTeachingQuality, int teachingDataCount) {
        this.fullName = fullName;
        this.averageCommunicationSkills = averageCommunicationSkills;
        this.communicationDataCount = communicationDataCount;
        this.averageTeachingQuality = averageTeachingQuality;
        this.teachingDataCount = teachingDataCount;
    }

    /**
     * @param report
     * @return false if report doesn't match Staff, true otherwise
     */
    public boolean addReport(CourseReport report) {
        if(!report.getCourseName().equals(this.fullName))
            return false;

        averageCommunicationSkills = (averageCommunicationSkills * communicationDataCount + report.getDifficulty())
                / (double) (communicationDataCount + 1);
        communicationDataCount += 1;

        averageTeachingQuality = (averageTeachingQuality * teachingDataCount + report.getUsefulness())
                / (double) (teachingDataCount + 1);
        teachingDataCount += 1;

        comments.add(report.getComment());

        return true;
    }

    /**
     * @return staff fullname
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return average level of communication skills
     */
    public double getAverageCommunicationSkills() {
        return averageCommunicationSkills;
    }

    /**
     * @return average level of teaching quality
     */
    public double getAverageTeachingQuality() {
        return averageTeachingQuality;
    }

    /**
     * @return number of communication data
     */
    public int getCommunicationDataCount() {
        return communicationDataCount;
    }

    /**
     * @return number of teaching data
     */
    public int getTeachingDataCount() {
        return teachingDataCount;
    }

    /**
     * @return arraylist that contains comments about Staff
     */
    public ArrayList<String> getComments() {
        return comments;
    }
}
