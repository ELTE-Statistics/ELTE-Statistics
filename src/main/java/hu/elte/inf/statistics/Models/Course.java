package hu.elte.inf.statistics.Models;

import java.util.ArrayList;

public class Course {

    private String name;
    private double averageDifficulty, averageUsefulness;
    private int difficultyDataCount, usefulnessDataCount;
    private ArrayList<String> comments = new ArrayList<String>();

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, double averageDifficulty, int difficultyDataCount, double averageUsefulness, int usefulnessDataCount) {
        this.name = name;
        this.averageDifficulty = averageDifficulty;
        this.difficultyDataCount = difficultyDataCount;
        this.averageUsefulness = averageUsefulness;
        this.usefulnessDataCount = usefulnessDataCount;
    }

    public boolean addReport(CourseReport report) {
        if(!report.getCourseName().equals(this.name))
            return false;

        averageDifficulty = (averageDifficulty * difficultyDataCount + report.getDifficulty())
                            / (double) (difficultyDataCount + 1);
        difficultyDataCount += 1;

        averageUsefulness = (averageUsefulness * usefulnessDataCount + report.getUsefulness())
                / (double) (usefulnessDataCount + 1);
        usefulnessDataCount += 1;

        comments.add(report.getComment());

        return true;
    }

    public String getName() {
        return name;
    }

    public double getAverageDifficulty() {
        return averageDifficulty;
    }

    public double getAverageUsefulness() {
        return averageUsefulness;
    }

    public int getDifficultyDataCount() {
        return difficultyDataCount;
    }

    public int getUsefulnessDataCount() {
        return usefulnessDataCount;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

}
