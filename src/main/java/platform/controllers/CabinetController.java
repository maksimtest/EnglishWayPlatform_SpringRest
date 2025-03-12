package platform.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.dtos.CabinetDto;
import platform.dtos.JwtResponse;
import platform.services.CabinetService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CabinetController {
    private final CabinetService cabinetService;

    @PostMapping("/cabinet")
    public CabinetDto cabinet(Principal principal) {
        System.out.println("CabinetController.cabinet, principal-> name: " + principal.getName()
        +", principal: "+principal);
        CabinetDto cabinetDto = cabinetService.getCabinet(principal.getName());
        return cabinetDto;
    }

}
