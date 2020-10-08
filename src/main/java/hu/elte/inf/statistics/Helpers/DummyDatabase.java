package hu.elte.inf.statistics.Helpers;

import java.util.ArrayList;
import java.util.HashMap;

public class DummyDatabase {


    class CourseData{
        public String name;
        public double averageDifficulty;
        public double averageGrade;
        public int entryDifficulty;
        public int entryGrade;
        public ArrayList<String> feedback = new ArrayList<>();
    }

    private HashMap<String, CourseData> data = new HashMap<>();

    private static DummyDatabase database;
    public static DummyDatabase getDatabase() {
        if(database == null)
            database = new DummyDatabase();
        return database;
    }

    private DummyDatabase() {}

    public void createNewCourse(String courseName){
        CourseData course = new CourseData();
        course.name = courseName;
        data.put(courseName, course);
    }


    public void addDifficulty(String courseName, int difficulty){
        if(!data.containsKey(courseName)){
            createNewCourse(courseName);
        }
        CourseData course = data.get(courseName);
        double currentDifficulty = course.averageDifficulty * course.entryDifficulty;
        currentDifficulty += difficulty;
        course.entryDifficulty++;
        course.averageDifficulty = currentDifficulty/course.entryDifficulty;
    }

    public void addGrade(String courseName, int grade){
        if(!data.containsKey(courseName)){
            createNewCourse(courseName);
        }
        CourseData course = data.get(courseName);
        double currentGrade = course.averageGrade * course.entryGrade;
        currentGrade += grade;
        course.entryGrade++;
        course.averageGrade = currentGrade/course.entryGrade;
    }

    public void addFeedback(String courseName, String comment){
        if(!data.containsKey(courseName)){
            createNewCourse(courseName);
        }
        CourseData course = data.get(courseName);
        course.feedback.add(comment);
    }

    public double getAverageDifficulty(String courseName){
        if(data.containsKey(courseName))
            return -1.0;
        CourseData course = data.get(courseName);
        return course.averageDifficulty;
    }

    public double getAverageGrade(String courseName){
        if(data.containsKey(courseName))
            return -1.0;
        CourseData course = data.get(courseName);
        return course.averageGrade;
    }

    ArrayList<String> getFeedback(String courseName){
        if(data.containsKey(courseName))
            return null;
        CourseData course = data.get(courseName);
        return course.feedback;
    }



}