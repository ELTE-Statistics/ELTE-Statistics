package hu.elte.inf.statistics.Models;

/**
 * Represents CourseReport Class
 * Report includes course name, comment
 * difficulty and usefulness level
 */
public class CourseReport {

    private String courseName;
    private String comment;
    private int difficulty, usefulness;


    /**
     * @param courseName
     * @param comment
     * @param difficulty
     * @param usefulness
     * Constructor of CourseReport Class with four parameters
     */
    public CourseReport(String courseName, String comment, int difficulty, int usefulness) {
        this.courseName = courseName;
        this.comment = comment;
        this.difficulty = difficulty;
        this.usefulness = usefulness;
    }

    /**
     * @return course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @return difficulty level
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * @return usefulness level
     */
    public int getUsefulness() {
        return usefulness;
    }
}
