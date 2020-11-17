package hu.elte.inf.statistics.Models;

import java.util.ArrayList;

/**
 * Represents Course Class
 * Course includes: course's name, information about difficulty and usefulness
 */
public class Course {

    private String name;
    private double averageDifficulty, averageUsefulness;
    private int difficultyDataCount, usefulnessDataCount;
    private ArrayList<String> comments = new ArrayList<String>();


    /**
     * @param name
     * Constructor of Course Class with one parameter
     */
    public Course(String name) {
        this.name = name;
    }

    /**
     * @param name
     * @param averageDifficulty
     * @param difficultyDataCount
     * @param averageUsefulness
     * @param usefulnessDataCount
     * Constructor of Course Class with five parameters
     */
    public Course(String name, double averageDifficulty, int difficultyDataCount, double averageUsefulness, int usefulnessDataCount) {
        this.name = name;
        this.averageDifficulty = averageDifficulty;
        this.difficultyDataCount = difficultyDataCount;
        this.averageUsefulness = averageUsefulness;
        this.usefulnessDataCount = usefulnessDataCount;
    }

    /**
     * @param report
     * Adds CourseReport
     * @return false if report doesn't match Course, true otherwise
     *
     */
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

    /**
     * @return the name of course
     */
    public String getName() {
        return name;
    }

    /**
     * @return avarage difficulty Level
     */
    public double getAverageDifficulty() {
        return averageDifficulty;
    }

    /**
     * @return average usefulness level
     */
    public double getAverageUsefulness() {
        return averageUsefulness;
    }

    /**
     * @return number of difficulty data
     */
    public int getDifficultyDataCount() {
        return difficultyDataCount;
    }

    /**
     * @return number of usefulness data
     */
    public int getUsefulnessDataCount() {
        return usefulnessDataCount;
    }

    /**
     * @return arraylist that contains comments about Course
     */
    public ArrayList<String> getComments() {
        return comments;
    }

}
