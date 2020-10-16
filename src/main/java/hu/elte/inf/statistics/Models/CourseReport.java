package hu.elte.inf.statistics.Models;

public class CourseReport {

    private String courseName;
    private String comment;
    private int difficulty, usefulness;

    public CourseReport(String courseName, String comment, int difficulty, int usefulness) {
        this.courseName = courseName;
        this.comment = comment;
        this.difficulty = difficulty;
        this.usefulness = usefulness;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getComment() {
        return comment;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getUsefulness() {
        return usefulness;
    }
}
