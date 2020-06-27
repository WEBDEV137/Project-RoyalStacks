package royalstacks.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.model.dataGenerator.Generator;
import royalstacks.app.model.repository.TransactionRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;


@Controller
public class GeneratorController {


    @Autowired
    Generator generator;


    @GetMapping("/generate")
    public ModelAndView Generate() throws IOException {
        System.out.println("...data generating");
        generator.fillDbAllData();
        System.out.println("...data generating succesful");
        return new ModelAndView("homepage");
    }


}
