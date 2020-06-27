package royalstacks.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;



@Controller
public class LogoutController {

    @RequestMapping("/logoutAction")
    public ModelAndView logout(HttpServletRequest request,
                               SessionStatus session,
                               Model model) {

        model.asMap().clear();
        session.setComplete();
        request.getSession().invalidate();

        return new ModelAndView("homepage");
    }
}

