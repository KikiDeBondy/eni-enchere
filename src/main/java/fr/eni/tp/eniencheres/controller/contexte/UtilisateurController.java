package fr.eni.tp.eniencheres.controller.contexte;
import fr.eni.tp.eniencheres.bll.UtilisateurService;
import fr.eni.tp.eniencheres.bo.Formateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.eniencheres.bo.contexte.Utilisateur;

import java.util.List;

@Controller
@RequestMapping("/contexte/utilisateur")
@SessionAttributes({ "utilisateurSession" })
public class UtilisateurController {
	private UtilisateurService utilisateurService;

	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/all")
	public String afficherFormateurs(Model model) {
		List<Utilisateur> lstUtilisateurs = utilisateurService.getUtilisateurs();
		model.addAttribute("users", lstUtilisateurs);
		return "view-users";

	}
	@GetMapping
	public String getDetail(@ModelAttribute("utilisateurSession") String utilisateurSession) {
	System.out.println("UtilisateurController - Contexte de la session comporte : " 
			+ utilisateurSession);

		return "contexte/view-utilisateur";
	}
	@ModelAttribute("utilisateurSession")
	public Utilisateur addAttributSession() {
		System.out.println("Add Attribut Session");
		return new Utilisateur("Anne-Lise");
	}
}
