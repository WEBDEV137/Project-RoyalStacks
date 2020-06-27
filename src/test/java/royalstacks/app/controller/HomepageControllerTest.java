package royalstacks.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

class HomepageControllerTest {

    @Test
    void startHandlerTest() {
        //ARRANGE
        HomepageController homepageController = new HomepageController();

        //ACT
        ModelAndView result = homepageController.startHandler();


        //ASSERT
        String expected = "homepage";
        assertEquals(expected , result.getViewName());
    }
}