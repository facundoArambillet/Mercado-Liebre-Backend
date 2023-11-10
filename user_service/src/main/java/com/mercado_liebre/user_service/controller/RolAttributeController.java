package com.mercado_liebre.user_service.controller;

import com.mercado_liebre.user_service.model.rolAttribute.RolAttribute;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttributeDTO;
import com.mercado_liebre.user_service.service.RolAttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/rol-attribute")
public class RolAttributeController {
    @Autowired
    private RolAttributeServiceImpl rolAttributeService;
    @GetMapping
    public List<RolAttributeDTO> getAll() {
        return rolAttributeService.getAll();
    }
    @GetMapping("/{idAttribute}")
    public Optional<RolAttribute> getById(@PathVariable("idAttribute") Long idAttribute) {
        return rolAttributeService.getById(idAttribute);
    }
    @GetMapping("/name/{attributeName}")
    public  Optional<RolAttribute> getByName(@PathVariable("attributeName") String attributeName) {
        return rolAttributeService.getByName(attributeName);
    }
    @PostMapping
    public RolAttribute createRolAttribute(@RequestBody RolAttribute rolAttribute) {
        return rolAttributeService.createRolAttribute(rolAttribute);
    }
    @PutMapping("/{idAttribute}")
    public RolAttributeDTO updateRolAttribute(@PathVariable("idAttribute")Long idAttribute, @RequestBody RolAttributeDTO rolAttributeDTO) {
        return rolAttributeService.updateRolAttribute(idAttribute, rolAttributeDTO);
    }
    @DeleteMapping("/{idAttribute}")
    public RolAttributeDTO deleteRolAttribute(@PathVariable("idAttribute") Long idAttribute) {
        return rolAttributeService.deleteRolAttribute(idAttribute);
    }
}
