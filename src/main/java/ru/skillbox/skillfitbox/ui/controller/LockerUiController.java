package ru.skillbox.skillfitbox.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.skillfitbox.dto.LockerDto;
import ru.skillbox.skillfitbox.service.LockerService;

import java.util.List;

@Controller
@RequestMapping("/ui/lockers")
@RequiredArgsConstructor
public class LockerUiController {

    private final LockerService lockerService;

    @GetMapping
    public String getAllLockers(Model model) {
        List<LockerDto> lockers = lockerService.getAllLockersWithClientInfo();
        model.addAttribute("lockers", lockers);
        return "lockers/list";
    }
}
