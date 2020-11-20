package hu.elte.inf.statistics.Controllers;

import hu.elte.inf.statistics.DAO.CoursesDAO;
import hu.elte.inf.statistics.Models.CourseReport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/** Course controller */
@Controller
public class RateCourseController {

    /**
     * @param req
     * @return modelAndView object
     */
    @GetMapping("/rateCourse")
    public ModelAndView render(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("rateCourse");
        return mv;
    }

    /**
     * updates modelAndView
     *
     * @param courseName
     * @param difficulty
     * @param usefulness
     * @param comment
     * @param button
     * @param req
     * @return modelAndView object
     */
    @PostMapping("/rateCourse")
    public ModelAndView update(
            @RequestParam String courseName,
            @RequestParam Integer difficulty,
            @RequestParam Integer usefulness,
            @RequestParam String comment,
            @RequestParam String button,
            HttpServletRequest req) {

        if (button.equals("submit-bt")) {
            CourseReport courseReport =
                    new CourseReport(courseName, comment, difficulty, usefulness);
            CoursesDAO coursesDAO = new CoursesDAO();
            coursesDAO.addCourseReport(courseReport);
            req.setAttribute("txt", "Review has been submitted.");
        }

        return new ModelAndView("rateCourse");
    }
}
