package edu.northwestu.intc3283.datasourcestarter.controller;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.repository.DonorsRepository;
import edu.northwestu.intc3283.datasourcestarter.util.DataGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/")
public class DonorsController {

    private final DonorsRepository donorRepository;
    private final DataGeneratorService dataGeneratorService;

    public DonorsController(DonorsRepository donorRepository, DataGeneratorService dataGeneratorService) {
        this.donorRepository = donorRepository;
        this.dataGeneratorService = dataGeneratorService;
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Donor>> searchDonors(@RequestParam("searchTerm") String searchTerm) {
        List<Donor> donors = donorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);

        if (donors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(donors);
        }
    }


    @GetMapping("")
    public String getAll(@RequestParam(value = "searchTerm", required = false) String searchTerm, Model model) {
        List<Donor> donors;

        // If searchTerm is provided and non-empty, search by name
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            donors = donorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
        } else {
            // Otherwise, retrieve the 10 most recent donors
            donors = donorRepository.findTop10ByOrderByCreatedAtDesc();
        }

        model.addAttribute("donors", donors);
        model.addAttribute("searchTerm", searchTerm);
        return "donors/index";
    }

    @GetMapping("/report")
    public String topDonorsReport(Model model) {
        model.addAttribute("topDonors", donorRepository.findTopDonors(5));
        return "donors/report";
    }

    @GetMapping("/donors/random")
    public String generateRandomDonors(@RequestParam("numDonors") int numDonors, @RequestParam("maxDonationsPerDonor") int maxDonationsPerDonor) {
        this.dataGeneratorService.generateRandomDonorsAndDonations(numDonors, maxDonationsPerDonor);
        return "redirect:/";
    }

    @GetMapping("reports/janiceReport")
    public String JaniceReport(Model model) {
        model.addAttribute("janiceDonors", donorRepository.JaniceReport(50));
        return "donors/janiceReport";
    }

    @GetMapping("reports/missingAddresses")
    public String LarryAddresses(Model model) {
        model.addAttribute("missingAddresses", donorRepository.LarryAddresses());
        return "donors/LarryMissingAddressReport";
    }

    @GetMapping("reports/yearlyDonations")
    public String LarryYearlyDonations(Model model) {
        model.addAttribute("yearlyDonations", donorRepository.LarryYearlyDonations(2023));
        return "donors/LarryYearlyDonation";
    }

    @GetMapping("reports/monthlyDonationJennifer")
    public String JenniferMonthlyDonor(Model model) {
        model.addAttribute("jenniferMonthly", donorRepository.JenniferMonthlyDonor());
        return "donors/JenniferMonthlyDonors";
    }

    @GetMapping("reports/yearlyDonationJennifer")
    public String JenniferYearlyReport(Model model) {
        model.addAttribute("jenniferYearly", donorRepository.JenniferYearlyReport());
        return "donors/JenniferYearly";
    }

    @GetMapping("reports/weeklyDonationJennifer")
    public String JenniferWeeklyReport(Model model) {
        model.addAttribute("jenniferWeekly", donorRepository.JenniferWeeklyReport());
        return "donors/JenniferWeekly";
    }

    @GetMapping("reports/JenniferTop5Report")
    public String JenniferTop5(Model model) {
        model.addAttribute("jenniferTop5", donorRepository.JenniferTop5(2024, 11));
        return "donors/JenniferTop5";
    }
}
