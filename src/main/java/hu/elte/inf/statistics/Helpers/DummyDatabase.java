package hu.elte.inf.statistics.Helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class DummyDatabase {


    class CourseData{
        public String name;
        public double averageDifficulty;
        public double averageUsefulness;
        public int entryDifficulty;
        public int entryUsefulness;
        public ArrayList<String> feedback = new ArrayList<>();
    }

    private HashMap<String, CourseData> data = new HashMap<>();

    private static DummyDatabase database;
    public static DummyDatabase getDatabase() {
        if(database == null)
            database = new DummyDatabase();
        return database;
    }

    private DummyDatabase() {
        this.data = new HashMap<>();
    }

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

    public void addUsefulness(String courseName, int usefulness){
        if(!data.containsKey(courseName)){
            createNewCourse(courseName);
        }
        CourseData course = data.get(courseName);
        double currentUsefulness = course.averageUsefulness * course.entryUsefulness;
        currentUsefulness += usefulness;
        course.entryUsefulness++;
        course.averageUsefulness = currentUsefulness/course.entryUsefulness;
    }

    public void addFeedback(String courseName, String comment){
        if(!data.containsKey(courseName)){
            createNewCourse(courseName);
        }
        CourseData course = data.get(courseName);
        course.feedback.add(comment);
    }

    public Set<String> getCourseNames() {
        return Collections.unmodifiableSet(this.data.keySet());
    }

    public double getAverageDifficulty(String courseName){
        if(data.containsKey(courseName))
            return -1.0;
        CourseData course = data.get(courseName);
        return course.averageDifficulty;
    }

    public double getAverageUsefulness(String courseName){
        if(data.containsKey(courseName))
            return -1.0;
        CourseData course = data.get(courseName);
        return course.averageUsefulness;
    }

    ArrayList<String> getFeedback(String courseName){
        if(data.containsKey(courseName))
            return null;
        CourseData course = data.get(courseName);
        return course.feedback;
    }



}