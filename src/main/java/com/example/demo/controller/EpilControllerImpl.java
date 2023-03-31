package com.example.demo.controller;

import com.example.demo.model.Epil;
import com.example.demo.repository.EpilRepository;
import com.example.demo.service.EpilServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        model.addAttribute("epil", epilRepository.findAllByOrderByNameAsc());
        model.addAttribute("i", epilService.i);
        model.addAttribute("apr", epilService.apr);
        model.addAttribute("in", epilService.in);
        model.addAttribute("inter", epilService.inter);
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

    @GetMapping("/earn")
    public String getEarn() {
        epilService.getEarnings();
        return "epil_page";
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

//    @GetMapping("/teamstats")
//    public String teamstats(Model model) {
//        model.addAttribute("teamStats",
//                teamStatsRepository.findAllByOrderByPowerPlayDesc());
//        return "teamStats_page";
//    }
}
