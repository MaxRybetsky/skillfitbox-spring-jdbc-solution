package ru.skillbox.skillfitbox.ui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.skillbox.skillfitbox.dto.ClientDetailDto;
import ru.skillbox.skillfitbox.dto.ClientDto;
import ru.skillbox.skillfitbox.dto.LockerDto;
import ru.skillbox.skillfitbox.dto.ServiceDto;
import ru.skillbox.skillfitbox.dto.TrainerDto;
import ru.skillbox.skillfitbox.service.ClientService;
import ru.skillbox.skillfitbox.service.LockerService;
import ru.skillbox.skillfitbox.service.AdditionalServicesHandlingService;
import ru.skillbox.skillfitbox.service.TrainerService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/ui/clients")
@RequiredArgsConstructor
public class ClientUiController {

    private final ClientService clientService;
    private final LockerService lockerService;
    private final AdditionalServicesHandlingService additionalServicesHandlingService;
    private final TrainerService trainerService;

    @GetMapping
    public String getAllClients(Model model) {
        List<ClientDto> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "clients/list";
    }

    @GetMapping("/{id}")
    public String getClientById(@PathVariable UUID id, Model model) {
        ClientDto client = clientService.getClientById(id);
        if (client == null) {
            return "redirect:/ui/clients?error=notfound";
        }
        model.addAttribute("client", client);
        return "clients/detail";
    }

    @GetMapping("/{id}/detail")
    public String getClientDetailById(@PathVariable UUID id, Model model) {
        ClientDetailDto clientDetail = clientService.getClientDetailById(id);
        if (clientDetail == null) {
            return "redirect:/ui/clients?error=notfound";
        }
        model.addAttribute("clientDetail", clientDetail);
        return "clients/detail-full";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new ClientDto());
        return "clients/create";
    }

    @PostMapping
    public String createClient(@Valid @ModelAttribute("client") ClientDto client, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clients/create";
        }
        try {
            clientService.addClient(client);
            redirectAttributes.addFlashAttribute("success", "Клиент успешно создан");
            return "redirect:/ui/clients";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании клиента: " + e.getMessage());
            return "redirect:/ui/clients/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable UUID id, Model model) {
        ClientDto client = clientService.getClientById(id);
        if (client == null) {
            return "redirect:/ui/clients?error=notfound";
        }
        model.addAttribute("client", client);
        return "clients/edit";
    }

    @PostMapping("/{id}")
    public String updateClient(@PathVariable UUID id, 
                              @Valid @ModelAttribute("client") ClientDto client, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clients/edit";
        }
        try {
            clientService.updateClient(id, client);
            redirectAttributes.addFlashAttribute("success", "Клиент успешно обновлен");
            return "redirect:/ui/clients";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении клиента: " + e.getMessage());
            return "redirect:/ui/clients/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/status")
    public String updateClientStatus(@PathVariable UUID id, 
                                    @RequestParam Boolean isActive, 
                                    RedirectAttributes redirectAttributes) {
        try {
            clientService.updateClientStatus(id, isActive);
            redirectAttributes.addFlashAttribute("success", "Статус клиента обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении статуса: " + e.getMessage());
        }
        return "redirect:/ui/clients/" + id;
    }

    @GetMapping("/{id}/assign-trainer")
    public String showAssignTrainerForm(@PathVariable UUID id, Model model) {
        ClientDto client = clientService.getClientById(id);
        if (client == null) {
            return "redirect:/ui/clients?error=notfound";
        }
        List<TrainerDto> trainers = trainerService.getAllTrainers();
        model.addAttribute("client", client);
        model.addAttribute("trainers", trainers);
        return "clients/assign-trainer";
    }

    @PostMapping("/{clientId}/trainer/{trainerId}")
    public String assignTrainer(@PathVariable UUID clientId, 
                               @PathVariable UUID trainerId, 
                               RedirectAttributes redirectAttributes) {
        try {
            clientService.assignTrainer(clientId, trainerId);
            redirectAttributes.addFlashAttribute("success", "Тренер назначен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при назначении тренера: " + e.getMessage());
        }
        return "redirect:/ui/clients/" + clientId;
    }

    @GetMapping("/{id}/assign-locker")
    public String showAssignLockerForm(@PathVariable UUID id, Model model) {
        ClientDto client = clientService.getClientById(id);
        if (client == null) {
            return "redirect:/ui/clients?error=notfound";
        }
        List<LockerDto> lockers = lockerService.getAllLockersWithClientInfo();
        model.addAttribute("client", client);
        model.addAttribute("lockers", lockers);
        return "clients/assign-locker";
    }

    @PostMapping("/{clientId}/locker/{lockerId}")
    public String assignLocker(@PathVariable UUID clientId, 
                              @PathVariable UUID lockerId, 
                              RedirectAttributes redirectAttributes) {
        try {
            clientService.assignLocker(clientId, lockerId);
            redirectAttributes.addFlashAttribute("success", "Шкафчик назначен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при назначении шкафчика: " + e.getMessage());
        }
        return "redirect:/ui/clients/" + clientId;
    }

    @GetMapping("/{id}/add-service")
    public String showAddServiceForm(@PathVariable UUID id, Model model) {
        ClientDto client = clientService.getClientById(id);
        if (client == null) {
            return "redirect:/ui/clients?error=notfound";
        }
        List<ServiceDto> services = additionalServicesHandlingService.getAllServices();
        model.addAttribute("client", client);
        model.addAttribute("services", services);
        return "clients/add-service";
    }

    @PostMapping("/{clientId}/services/{serviceId}")
    public String addServiceToClient(@PathVariable UUID clientId, 
                                    @PathVariable String serviceId, 
                                    RedirectAttributes redirectAttributes) {
        try {
            clientService.addServiceToClient(clientId, serviceId);
            redirectAttributes.addFlashAttribute("success", "Услуга добавлена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении услуги: " + e.getMessage());
        }
        return "redirect:/ui/clients/" + clientId;
    }
}
