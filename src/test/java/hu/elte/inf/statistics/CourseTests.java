package hu.elte.inf.statistics;

import hu.elte.inf.statistics.Models.Course;
import hu.elte.inf.statistics.Models.CourseReport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTests {

    @Test
    public void testCourse() {
        Course c = new Course("Test");
        assertNotNull(c);
        assertEquals("Test",c.getName());
        assertNotNull(c.getComments());
    }

    @Test
    public void testDefaultValues() {
        Course c = new Course("Test");
        assertEquals(0,c.getAverageDifficulty());
        assertEquals(0,c.getDifficultyDataCount());
        assertEquals(0,c.getAverageUsefulness());
        assertEquals(0,c.getUsefulnessDataCount());
        assertEquals(0,c.getComments().size());
    }

    @Test
    public void testDataUpdate() {
        Course c = new Course("Test");

        c.addReport(new CourseReport("Test", "No Comment", 6, 10));
        assertEquals(6,c.getAverageDifficulty());
        assertEquals(1,c.getDifficultyDataCount());
        assertEquals(10,c.getAverageUsefulness());
        assertEquals(1,c.getUsefulnessDataCount());
        assertEquals(1,c.getComments().size());
        assertTrue(c.getComments().contains("No Comment"));

        c.addReport(new CourseReport("Test", "Some Comment", 5, 0));
        assertEquals(5.5,c.getAverageDifficulty());
        assertEquals(2,c.getDifficultyDataCount());
        assertEquals(10,c.getAverageUsefulness());
        assertEquals(2,c.getUsefulnessDataCount());
        assertEquals(2,c.getComments().size());
        assertTrue(c.getComments().contains("Some Comment"));
    }

    @Test
    public void testWrongReport() {
        Course c = new Course("Test");

        c.addReport(new CourseReport("NotTest", "No Comment", 6, 10));
        assertEquals(0,c.getAverageDifficulty());
        assertEquals(0,c.getDifficultyDataCount());
        assertEquals(0,c.getAverageUsefulness());
        assertEquals(0,c.getUsefulnessDataCount());
        assertEquals(0,c.getComments().size());
    }

}
