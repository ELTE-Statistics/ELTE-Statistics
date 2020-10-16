package hu.elte.inf.statistics;

import hu.elte.inf.statistics.Models.CourseReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseReportTests {
    CourseReport cr;

    @BeforeEach
    public void init() {
        cr = null;
    }

    @Test
    public void testReport() {
        cr = new CourseReport("cName", "No Comment", 1,10);
        assertNotNull(cr);
        assertEquals("cName", cr.getCourseName());
        assertEquals("No Comment", cr.getComment());
        assertEquals(1, cr.getDifficulty());
        assertEquals(10, cr.getUsefulness());
    }

}
