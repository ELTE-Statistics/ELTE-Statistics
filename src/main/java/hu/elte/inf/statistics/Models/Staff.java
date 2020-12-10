package hu.elte.inf.statistics.Models;

import java.util.ArrayList;

/**
 * Represents Staff Class Staff includes: fullname, information about : preparedness, helpfulness
 */
public class Staff {

    private String fullName;
    private double averagePreparedness, averageHelpfulness;
    private int preparednessDataCount, helpfulnessDataCount;
    private ArrayList<String> comments = new ArrayList<String>();

    /** @param name Constructor of Staff with staff name */
    public Staff(String name) {
        this.fullName = name;
    }

    /**
     * constructor of Staff with five arguments
     *
     * @param fullName
     * @param averagePreparedness
     * @param preparednessDataCount
     * @param averageHelpfulness
     * @param helpfulnessDataCount
     */
    public Staff(
            String fullName,
            double averagePreparedness,
            int preparednessDataCount,
            double averageHelpfulness,
            int helpfulnessDataCount) {
        this.fullName = fullName;
        this.averagePreparedness = averagePreparedness;
        this.preparednessDataCount = preparednessDataCount;
        this.averageHelpfulness = averageHelpfulness;
        this.helpfulnessDataCount = helpfulnessDataCount;
    }

    /**
     * @param report
     * @return false if report doesn't match Staff, true otherwise
     */
    public boolean addReport(CourseReport report) {
        if (!report.getCourseName().equals(this.fullName)) return false;

        averagePreparedness =
                (averagePreparedness * preparednessDataCount + report.getDifficulty())
                        / (double) (preparednessDataCount + 1);
        preparednessDataCount += 1;

        averageHelpfulness =
                (averageHelpfulness * helpfulnessDataCount + report.getUsefulness())
                        / (double) (helpfulnessDataCount + 1);
        helpfulnessDataCount += 1;

        comments.add(report.getComment());

        return true;
    }

    /** @return staff fullname */
    public String getFullName() {
        return fullName;
    }

    /** @return average level of preparedness */
    public double getAveragePreparedness() {
        return averagePreparedness;
    }

    /** @return average level of helpfulness */
    public double getAverageHelpfulness() {
        return averageHelpfulness;
    }

    /** @return number of preparedness */
    public int getPreparednessDataCount() {
        return preparednessDataCount;
    }

    /** @return number of helpfulness */
    public int getHelpfulnessDataCount() {
        return helpfulnessDataCount;
    }

    /** @return arraylist that contains comments about Staff */
    public ArrayList<String> getComments() {
        return comments;
    }
}
