package com.example.demo.controller;

import com.example.demo.model.Epil;
import com.example.demo.repository.EpilRepository;
import com.example.demo.service.EpilServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
        List<Epil> allByOrderByNameAsc = epilRepository.findAllByOrderByNameAsc();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Collections.sort(allByOrderByNameAsc, Comparator.comparing(o -> LocalDate.parse(o.getDate(), formatter)));
        Collections.reverse(allByOrderByNameAsc);

        model.addAttribute("epil", allByOrderByNameAsc);

        model.addAttribute("manM", epilRepository.findSumInMarchByMandrik());
        model.addAttribute("manA", epilRepository.findSumInAprilByMandrik());
        model.addAttribute("manMay", epilRepository.findSumInMayByMandrik());
        model.addAttribute("manJune", epilRepository.findSumInJuneByMandrik());

        model.addAttribute("sM", epilRepository.findSumInMarchByShamilova());
        model.addAttribute("sA", epilRepository.findSumInAprilByShamilova());
        model.addAttribute("sMay", epilRepository.findSumInMayByShamilova());
        model.addAttribute("sJune", epilRepository.findSumInJuneByShamilova());

        model.addAttribute("mazM", epilRepository.findSumInMarchByMazikova());
        model.addAttribute("mazA", epilRepository.findSumInAprilByMazikova());
        model.addAttribute("mazMay", epilRepository.findSumInMayByMazikova());
        model.addAttribute("mazJune", epilRepository.findSumInJuneByMazikova());
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

    @GetMapping("/copy")
    public ResponseEntity<Object> downloadBackUp() throws IOException {
        String filename = "backup.txt";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

    @GetMapping("/rollback")
    public String rollBack() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("backup.txt"));

            String c;
            while ((c = reader.readLine()) != null) {
                String[] words = c.split("\\|");
                epilRepository.save(new Epil(Long.parseLong(words[0]), Boolean.parseBoolean(words[1]), words[2], words[3], words[4], words[5], words[6],
                        words[7], words[8], words[9]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "rollback-success_page";
    }
}
