package hu.elte.inf.statistics.Controllers;

import hu.elte.inf.statistics.DAO.StaffDAO;
import hu.elte.inf.statistics.Models.StaffReport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/** Rate Professor controller */
@Controller
public class RateProfessorController {

    /**
     * @param req
     * @return modelAndView object
     */
    @GetMapping("/rateProfessor")
    public ModelAndView render(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("rateProfessor");
        return mv;
    }

    /**
     * updates modelAndView
     *
     * @param professorName
     * @param preparedness
     * @param helpfulness
     * @param comment
     * @param button
     * @param req
     * @return modelAndView object
     */
    @PostMapping("/rateProfessor")
    public ModelAndView update(
            @RequestParam String professorName,
            @RequestParam Integer preparedness,
            @RequestParam Integer helpfulness,
            @RequestParam String comment,
            @RequestParam String button,
            HttpServletRequest req) {

        if (button.equals("submit-bt")) {
            StaffReport staffReport =
                    new StaffReport(professorName, comment, preparedness, helpfulness);
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.addStaffReport(staffReport);
            req.setAttribute("txt", "Review has been submitted.");
        }

        return new ModelAndView("rateProfessor");
    }
}
