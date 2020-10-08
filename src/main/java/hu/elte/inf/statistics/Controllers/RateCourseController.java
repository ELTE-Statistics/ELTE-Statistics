package hu.elte.inf.statistics.Controllers;

import hu.elte.inf.statistics.Helpers.DummyDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RateCourseController {

    @GetMapping("/rateCourse")
    public ModelAndView render(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("rateCourse");
        return mv;
    }

    @PostMapping("/rateCourse")
    public ModelAndView update(@RequestParam String courseName,
                               @RequestParam Integer difficulty,
                               @RequestParam Integer grade,
                               @RequestParam String feedback,
                               @RequestParam String button,
                               HttpServletRequest req) {

        if (button.equals("submit-bt")) {
            DummyDatabase.getDatabase().addDifficulty(courseName, difficulty);
            DummyDatabase.getDatabase().addGrade(courseName, grade);
            DummyDatabase.getDatabase().addFeedback(courseName, feedback);
            req.setAttribute("txt","Review has been submitted.");
        }

        return new ModelAndView("rateCourse");
    }
}
