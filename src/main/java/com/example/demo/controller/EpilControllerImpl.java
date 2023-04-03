package com.example.demo.controller;

import com.example.demo.model.Epil;
import com.example.demo.repository.EpilRepository;
import com.example.demo.service.EpilServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class EpilControllerImpl implements EpilController {

    private final EpilServiceImpl epilService;
    private final EpilRepository epilRepository;

//    @PostMapping("/create")
//    public String createStandings() {
//        standingsService.createStandings();
//        return "table-created_page";
//    }

    @GetMapping("/get")
    public String getClients(Model model) {
        epilService.getEarnings();
        model.addAttribute("epil", epilRepository.findAllByOrderByNameAsc());
        model.addAttribute("manM", epilService.MANDRIK_MARCH);
        model.addAttribute("manA", epilService.MANDRIK_APRIL);
        model.addAttribute("sM", epilService.SHAMILOVA_MARCH);
        model.addAttribute("sA", epilService.SHAMILOVA_APRIL);
        model.addAttribute("mazM", epilService.MAZIKOVA_MARCH);
        model.addAttribute("mazA", epilService.MAZIKOVA_APRIL);
        return "epil_page";
    }

    @GetMapping("/new")
    public String newClient(Map<String, Object> model) {
        Epil epil = new Epil();
        model.put("client", epil);
        return "create_client";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Epil epil = epilRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
        model.addAttribute("client", epil);
        return "edit_client";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid Epil epil, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            epil.setId(id);
            return "edit_client";
        }

        epilRepository.save(epil);
        model.addAttribute("client", epilRepository.findAll());
        return "redirect:/get";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute("client") Epil epil) {
        epilRepository.save(epil);
        return "redirect:/get";
    }

    @GetMapping("delete/{id}")
    public String showDeleteForm(@PathVariable("id") long id) {
        Epil epil = epilRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
        epilRepository.delete(epil);
        return "redirect:/get";
    }

//    @GetMapping("/teamstats")
//    public String teamstats(Model model) {
//        model.addAttribute("teamStats",
//                teamStatsRepository.findAllByOrderByPowerPlayDesc());
//        return "teamStats_page";
//    }
}
