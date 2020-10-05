package hu.elte.inf.statistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @GetMapping("/")
    public ModelAndView render(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("main");
        req.setAttribute("txt", "");
        return mv;
    }

    @PostMapping("/")
    public ModelAndView update(HttpServletRequest req, @RequestParam String button) {
        ModelAndView mv = new ModelAndView("main");
        if (button.equals("bt1")) {
            req.setAttribute("txt", "Hello World!");
        } else {
            req.setAttribute("txt", "Goodbye World!");
        }
        return mv;
    }

}
