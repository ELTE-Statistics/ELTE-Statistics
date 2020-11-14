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

    public String getName() {
        return fullName;
    }

    public double getAverageDifficulty() {
        return averageCommunicationSkills;
    }

    public double getAverageUsefulness() {
        return averageTeachingQuality;
    }

    public int getDifficultyDataCount() {
        return communicationDataCount;
    }

    public int getUsefulnessDataCount() {
        return teachingDataCount;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

}
