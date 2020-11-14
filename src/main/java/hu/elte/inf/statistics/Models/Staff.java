package hu.elte.inf.statistics.Models;

import java.util.ArrayList;

public class Staff {

    private String fullName;
    private double averageCommunicationSkills, averageTeachingQuality;
    private int communicationDataCount, teachingDataCount;
    private ArrayList<String> comments = new ArrayList<String>();

    public Staff(String name) {
        this.fullName = name;
    }

    public Staff(String fullName, double averageCommunicationSkills, int communicationDataCount, double averageTeachingQuality, int teachingDataCount) {
        this.fullName = fullName;
        this.averageCommunicationSkills = averageCommunicationSkills;
        this.communicationDataCount = communicationDataCount;
        this.averageTeachingQuality = averageTeachingQuality;
        this.teachingDataCount = teachingDataCount;
    }

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

    public String getFullName() {
        return fullName;
    }

    public double getAverageCommunicationSkills() {
        return averageCommunicationSkills;
    }

    public double getAverageTeachingQuality() {
        return averageTeachingQuality;
    }

    public int getCommunicationDataCount() {
        return communicationDataCount;
    }

    public int getTeachingDataCount() {
        return teachingDataCount;
    }

    public ArrayList<String> getComments() {
        return comments;
    }
}
