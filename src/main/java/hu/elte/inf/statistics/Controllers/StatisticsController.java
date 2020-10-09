package hu.elte.inf.statistics.Controllers;

import hu.elte.inf.statistics.Helpers.DummyDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Set;

@Controller
public class StatisticsController {
    @GetMapping("/statistics")
    public String getData(Model model) {
        Set<String> data = DummyDatabase.getDatabase().getCourseNames();
        ArrayList<Double> avg_difficulty = new ArrayList<Double>();
        for(String name: data) {
            avg_difficulty.add(DummyDatabase.getDatabase().getAverageDifficulty(name));
        }
        model.addAttribute("average_difficulty", avg_difficulty);
        return "statistics";
    }
}
