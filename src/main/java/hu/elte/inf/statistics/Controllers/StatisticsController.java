package hu.elte.inf.statistics.Controllers;

import hu.elte.inf.statistics.DAO.CoursesDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {
    @GetMapping("/statistics")
    public String getData(Model model) {
        CoursesDAO coursesDAO = new CoursesDAO();
        model.addAttribute("courses", coursesDAO.getAll());
        return "statistics";
    }
}
