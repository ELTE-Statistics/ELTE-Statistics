package hu.elte.inf.statistics.Controllers;

import hu.elte.inf.statistics.DAO.CoursesDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class StatisticsController {
    @GetMapping("/statistics")
    public String getData(Model model) {
        CoursesDAO coursesDAO = new CoursesDAO();
        double x = coursesDAO.getCourseAverageDifficulty("Test Subject");
        System.err.println("============> X: " + x);
        coursesDAO.setCourseAverageDifficulty("Test Subject", 12.5);
        x = coursesDAO.getCourseAverageDifficulty("Test Subject");
        System.err.println("============> X: " + x);
        model.addAttribute("courses", coursesDAO.getAll());
        return "statistics";
    }
}
