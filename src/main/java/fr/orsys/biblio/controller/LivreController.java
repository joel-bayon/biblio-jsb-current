package fr.orsys.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.orsys.biblio.service.Bibliotheque;

@Controller
@RequestMapping("/livres")
public class LivreController {
	
	@Autowired
	Bibliotheque biblio;
	
	@RequestMapping("/all")
	public String getLivreAll(Model model) {
		model.addAttribute("livres", biblio.listerLivre());
		return "livres";
	}
	
	@RequestMapping("/{id}")
	public String getLivre(@PathVariable int id, Model model) {
		model.addAttribute("livre", biblio.getLivreDao().findOne(id));
		return "livre";
	}

}
