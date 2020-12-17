package co.edu.unicauca.gestordocumental.controller.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/email")
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class EmailRest {

    @Autowired
    private EmailPort emailPort;

    // @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path = "/send")
    @ResponseBody
    public boolean SendEmail(@RequestBody EmailBody emailBody)  {
        return emailPort.sendEmail(emailBody, "Carlos Cobos");
    }
}
