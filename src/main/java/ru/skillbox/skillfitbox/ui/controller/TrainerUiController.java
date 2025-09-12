package ru.skillbox.skillfitbox.ui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.skillbox.skillfitbox.dto.TrainerDetailDto;
import ru.skillbox.skillfitbox.dto.TrainerDto;
import ru.skillbox.skillfitbox.entity.TrainerStatus;
import ru.skillbox.skillfitbox.service.TrainerService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/ui/trainers")
@RequiredArgsConstructor
public class TrainerUiController {

    private final TrainerService trainerService;

    @GetMapping
    public String getAllTrainers(Model model) {
        List<TrainerDto> trainers = trainerService.getAllTrainers();
        model.addAttribute("trainers", trainers);
        return "trainers/list";
    }

    @GetMapping("/{id}")
    public String getTrainerById(@PathVariable UUID id, Model model) {
        TrainerDto trainer = trainerService.getTrainerById(id);
        if (trainer == null) {
            return "redirect:/ui/trainers?error=notfound";
        }
        model.addAttribute("trainer", trainer);
        return "trainers/detail";
    }

    @GetMapping("/{id}/detail")
    public String getTrainerDetailById(@PathVariable UUID id, Model model) {
        TrainerDetailDto trainerDetail = trainerService.getTrainerDetailById(id);
        if (trainerDetail == null) {
            return "redirect:/ui/trainers?error=notfound";
        }
        model.addAttribute("trainerDetail", trainerDetail);
        return "trainers/detail-full";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("trainer", new TrainerDto());
        model.addAttribute("statuses", TrainerStatus.values());
        return "trainers/create";
    }

    @PostMapping
    public String createTrainer(@Valid @ModelAttribute("trainer") TrainerDto trainer, 
                               BindingResult result, 
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", TrainerStatus.values());
            return "trainers/create";
        }
        try {
            trainerService.addTrainer(trainer);
            redirectAttributes.addFlashAttribute("success", "Тренер успешно создан");
            return "redirect:/ui/trainers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании тренера: " + e.getMessage());
            return "redirect:/ui/trainers/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable UUID id, Model model) {
        TrainerDto trainer = trainerService.getTrainerById(id);
        if (trainer == null) {
            return "redirect:/ui/trainers?error=notfound";
        }
        model.addAttribute("trainer", trainer);
        model.addAttribute("statuses", TrainerStatus.values());
        return "trainers/edit";
    }

    @PostMapping("/{id}")
    public String updateTrainer(@PathVariable UUID id, 
                               @Valid @ModelAttribute("trainer") TrainerDto trainer, 
                               BindingResult result, 
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", TrainerStatus.values());
            return "trainers/edit";
        }
        try {
            trainerService.updateTrainer(id, trainer);
            redirectAttributes.addFlashAttribute("success", "Тренер успешно обновлен");
            return "redirect:/ui/trainers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении тренера: " + e.getMessage());
            return "redirect:/ui/trainers/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/status")
    public String changeTrainerStatus(@PathVariable UUID id, 
                                     @RequestParam TrainerStatus status, 
                                     RedirectAttributes redirectAttributes) {
        try {
            trainerService.changeTrainerStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Статус тренера обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении статуса: " + e.getMessage());
        }
        return "redirect:/ui/trainers/" + id;
    }
}
