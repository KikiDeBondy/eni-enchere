package fr.eni.tp.eniencheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.eniencheres.bll.CoursService;
import fr.eni.tp.eniencheres.bll.FormateurService;
import fr.eni.tp.eniencheres.bo.Cours;
import fr.eni.tp.eniencheres.bo.Formateur;
import fr.eni.tp.eniencheres.exceptions.BusinessException;
import jakarta.validation.Valid;

//@Controller --> permet de définir la classe comme un bean Spring de type Controller
@Controller
//Url par défaut pour toutes les méthodes du contrôleur
@RequestMapping("/formateurs")
//Mise en session de la liste des cours
@SessionAttributes({ "coursSession" })
public class FormateurController {
	// Injection du FormateurService
	private FormateurService formateurService;
	private CoursService coursService;

	public FormateurController(FormateurService formateurService, CoursService coursService) {
		this.formateurService = formateurService;
		this.coursService = coursService;
	}

	@GetMapping
	public String afficherFormateurs(Model model) {
		List<Formateur> lstFormateurs = formateurService.getFormateurs();
		model.addAttribute("formateurs", lstFormateurs);
		return "view-formateurs";

	}

	@GetMapping("/detail")
	public String detailFormateurParParametre(
			@RequestParam(name = "email", required = false, defaultValue = "coach@campus-eni.fr") String emailFormateur,
			Model model) {
		System.out.println("Le paramètre - " + emailFormateur);
		Formateur formateur = formateurService.findByEmail(emailFormateur);
		// Ajout de l'instance dans le modèle
		model.addAttribute("formateur", formateur);
		return "view-formateur-detail";
	}

	@PostMapping("/detail")
	public String mettreAJourFormateur(@Valid @ModelAttribute("formateur") Formateur f, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "view-formateur-detail";
		} else {

			try {
				System.out.println("Le formateur récupéré depuis le formulaire : ");

				System.out.println(f);

				// Sauvegarder les modifications
				formateurService.update(f);

				return "redirect:/formateurs";
			} catch (BusinessException e) {
				// Afficher les messages d’erreur - il faut les injecter dans le contexte de
				// BindingResult
				e.getClefsExternalisations().forEach(key -> {
					ObjectError error = new ObjectError("globalError", key);
					bindingResult.addError(error);
				});

				return "view-formateur-detail";
			}
		}

	}

	// Méthode pour charger la liste des cours en session
	@ModelAttribute("coursSession")
	public List<Cours> chargerCoursSession() {
		System.out.println("Chargement en session de tous les cours");
		return coursService.getCours();
	}

	// Ajout d'un cours au formateur courant
	@PostMapping("/cours")
	public String ajouterCours(@RequestParam(required = true) String email,
			@RequestParam(name = "idCours", required = true) String id) {
		long idCours = Long.parseLong(id);
		formateurService.updateCoursFormateur(email, idCours);
		return "redirect:/formateurs/detail?email=" + email;
	}

	@GetMapping("/creer")
	public String creerFormateur(Model model) {
		Formateur formateur = new Formateur();
		// Ajout de l'instance dans le modèle
		model.addAttribute("formateur", formateur);

		return "view-formateur-creer";
	}

	// Récupération de l'objet formateur du formulaire
	// Traçage de la liste des cours associés via Converter
	// sauvegarde
	@PostMapping("/creer")
	public String creerFormateur(@Valid @ModelAttribute("formateur") Formateur formateur, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "view-formateur-creer";
		} else {
			try {
				formateurService.add(formateur);
				return "redirect:/formateurs";
			} catch (BusinessException e) {
				// Afficher les messages d’erreur - il faut les injecter dans le contexte de
				// BindingResult
				e.getClefsExternalisations().forEach(key -> {
					ObjectError error = new ObjectError("globalError", key);
					bindingResult.addError(error);
				});

				return "view-formateur-creer";
			}
		}
	}

}
