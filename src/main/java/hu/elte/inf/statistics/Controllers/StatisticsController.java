package hu.elte.inf.statistics.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StatisticsController {
    @GetMapping("/statistics")
    public ModelAndView render(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("statistics");
        return mv;
    }
}
