package royalstacks.app.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;

@Controller
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class Index {
}