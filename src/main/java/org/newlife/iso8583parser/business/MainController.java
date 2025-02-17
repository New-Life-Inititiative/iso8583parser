package org.newlife.iso8583parser.business;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MainController {

    @RequestMapping(value = "/")
    public String main()
    {
        return "home";
    }

}
