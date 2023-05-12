package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.interfaceService.IpersonService;
import com.example.demo.models.Person;

import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping
public class PersonController {
	@Autowired
	private IpersonService personService;
	
	@GetMapping("/listar")
	public String listar(Model model) {
		List<Person> people= personService.listar();
		model.addAttribute("people", people);
		return "index";
	}
	
	@GetMapping("/new")
	public String agregar(Model model) {
		model.addAttribute("person", new Person());
		return "form";
	}
	@PostMapping("/save")
	public String save(@Validated Person person, Model model) {
		model.addAttribute("person", new Person());
		personService.save(person);
		return "redirect:/listar";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model){
		Optional<Person>person=personService.listarId(id);
		model.addAttribute("person", person);
		return "form";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		personService.delete(id);
		return "redirect:/listar";
	}
}

