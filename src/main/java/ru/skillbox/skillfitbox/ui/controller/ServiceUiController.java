package ru.skillbox.skillfitbox.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.skillfitbox.dto.ServiceDto;
import ru.skillbox.skillfitbox.service.AdditionalServicesHandlingService;

import java.util.List;

@Controller
@RequestMapping("/ui/services")
@RequiredArgsConstructor
public class ServiceUiController {

    private final AdditionalServicesHandlingService additionalServicesHandlingService;

    @GetMapping
    public String getAllServices(Model model) {
        List<ServiceDto> services = additionalServicesHandlingService.getAllServices();
        model.addAttribute("services", services);
        return "services/list";
    }

    @GetMapping("/{id}")
    public String getServiceById(@PathVariable String id, Model model) {
        ServiceDto service = additionalServicesHandlingService.getServiceByIdWithClients(id);
        if (service == null) {
            return "redirect:/ui/services?error=notfound";
        }
        model.addAttribute("service", service);
        return "services/detail";
    }
}
