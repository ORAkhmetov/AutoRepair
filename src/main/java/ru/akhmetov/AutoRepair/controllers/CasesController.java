package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.dto.CaseDTO;
import ru.akhmetov.AutoRepair.dto.ClientDTO;
import ru.akhmetov.AutoRepair.mappers.CasesMapper;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.services.CasesServiceImpl;
import ru.akhmetov.AutoRepair.util.CaseValidator;

import java.util.stream.Collectors;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/cases")
public class CasesController {
    private final CasesServiceImpl casesServiceImpl;
    private final CaseValidator caseValidator;
    private final CasesMapper casesMapper;
    @Autowired
    public CasesController(CasesServiceImpl casesServiceImpl, CaseValidator caseValidator, CasesMapper casesMapper) {
        this.casesServiceImpl = casesServiceImpl;
        this.caseValidator = caseValidator;
        this.casesMapper = casesMapper;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("cases", casesServiceImpl.findAll().stream()
                .map(casesMapper::convertToCaseDTO).collect(Collectors.toList()));
        return "cases/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("case", casesMapper.convertToCaseDTO(casesServiceImpl.findOne(id)));
        return "cases/show";
    }
    @GetMapping("/new")
    public String newCase(@ModelAttribute("case") CaseDTO caseDTO) {
        return "cases/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("case") @Valid CaseDTO caseDTO,
                         BindingResult bindingResult) {
       /* caseValidator.validate(caseDTO, bindingResult);
        if (bindingResult.hasErrors())
            return "cases/new";*/

        casesServiceImpl.save(casesMapper.convertToCase(caseDTO));
        return "redirect:/cases";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("case", casesMapper.convertToCaseDTO(casesServiceImpl.findOne(id)));
        return "cases/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("case") @Valid CaseDTO caseDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        caseValidator.validate(caseDTO, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "cases/edit";

        casesServiceImpl.update(id, casesMapper.convertToCase(caseDTO));
        return "redirect:/cases";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        casesServiceImpl.delete(id);
        return "redirect:/cases";
    }
}
