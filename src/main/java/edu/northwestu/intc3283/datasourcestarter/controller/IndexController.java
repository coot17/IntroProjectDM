package edu.northwestu.intc3283.datasourcestarter.controller;

import edu.northwestu.intc3283.datasourcestarter.entity.Entry;
import edu.northwestu.intc3283.datasourcestarter.repository.EntryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final EntryRepository entryRepository;

    public IndexController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @GetMapping("/")
    public String indexAction(Model model) {
        // Help our html pages access repo data
        model.addAttribute("entries", this.entryRepository.findAll());
        return "index";
    }

    @GetMapping("/entries/view")
    public String tableAction(Model model) {
        model.addAttribute("entries", this.entryRepository.findAll());
        return "table";
    }
    @GetMapping("/entries/new")
    public String indexAction(final @ModelAttribute("entry") Entry input,
                              final BindingResult bindingResult,
                              final Model model) {
        return "form";
    }

    @GetMapping("/entries/{id}")
    public String viewEntry(final @PathVariable("id") Long id, final Model model) {
        /*Optional<Entry>*/ final Entry entry = this.entryRepository.findById(id).orElse(null);
        model.addAttribute("entry", entry);
        return "entry";
    }

    @PostMapping("/entries")
    public String submitEntry(final @Valid @ModelAttribute("entry") Entry input,
                              final BindingResult bindingResult,
                              final Model model) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        // verify email is unique
        try {
            this.entryRepository.save(input);
        } catch(Exception e) {
            bindingResult.addError(new ObjectError("globalError", "email must be unique"));
            return "form";
        }
        return "redirect:/entries/" + input.getId();
    }
}
