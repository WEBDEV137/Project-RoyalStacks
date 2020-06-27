package royalstacks.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomepageController {

    @GetMapping(value ={ "" , "/", "/doLogin", "index"})
    public ModelAndView startHandler() {
        return new ModelAndView("homepage");
    }




}
