package fr.eql.projet01.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.service.SexeService;
import fr.eql.projet01.service.UtilisateurService;
import fr.eql.projet01.service.VilleService;
import fr.eql.projet01.service.DroitsService;

@Controller
public class UtilisateurController {

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private SexeService sexeService;
	
	@Autowired
	private DroitsService droitsService;
	
	@Autowired
	private VilleService villeService;

	// l'url affInfoUti?id=1 @RequestParam("id")Long id
	// autre solution @PathParam("id")Long id
	@GetMapping("/affInfoUti")
	public String affInfoUti(Model model, @RequestParam("id") Long id) {
		try {
			model.addAttribute("utilisateur", utilisateurService.findOne(id));
			return "profil";
		} catch(Exception e) {
			e.getMessage();
			return "profilErreur";
		}
	}

	@GetMapping("/majUti")
	public String majUti(Model model, @RequestParam("id") Long id) {
		model.addAttribute("listeSexe",sexeService.findAll() );
		model.addAttribute("listeDroits",droitsService.findAll() );
		model.addAttribute("listeVille",villeService.findAll() );

		if (id != null) {
			Utilisateur uti = utilisateurService.findOne(id);
			if (uti != null) {
				model.addAttribute("utilisateur", uti);
				return "profilMaj";
			} else {
				return "profilErreur";
			}
		} else {
			return "profilErreur";
		}
	}

	@PostMapping("/sauvegardeUti")
	public String SauvegardeUti(Model model, @ModelAttribute("utilisateur") Utilisateur uti) {	
		
		System.out.println(uti.toString());
		model.addAttribute("utilisateur", utilisateurService.save(uti));
		return "profil";
	}

	@ModelAttribute("utilisateur")
	public Utilisateur addUtilisateurInModel() {
		return new Utilisateur();
	}

	@GetMapping("/inscription")
	public String inscription(Model model) {
		model.addAttribute("listeSexe", sexeService.findAll());
		model.addAttribute("listeVille", villeService.findAll());
		model.addAttribute("message", "Inscription");
		return "inscription";
	}

	@PostMapping("/traitement-inscription")
	@Transactional
	public String traitementInscription(Model model, @Valid Long idsexe, @Valid Long idville, @Valid String password, @Valid String passwordConfirm,
			@Valid Utilisateur utilisateur, BindingResult bindingResult) {
		String redirect = null;
		model.addAttribute("listeSexe", sexeService.findAll());
		model.addAttribute("listeVille", villeService.findAll());

		if(utilisateurService.findByMail(utilisateur.getMail())) {
			model.addAttribute("message", "L'adresse mail " + utilisateur.getMail() + " est déja utilisée !");
			return "inscription";
		}

		if(utilisateurService.findByLogin(utilisateur.getProfile())) {
			model.addAttribute("message", "Le login " + utilisateur.getProfile() + " est déja utilisé !");
			return "inscription";
		}

		if(!password.equals(passwordConfirm)) {
			model.addAttribute("message", "Les mots de passes sont différents !");
			return "inscription";
		}

		if(bindingResult.hasErrors()) {
			redirect = "inscription";
		} else {
			try {
				utilisateur.setSexe(sexeService.findOne(idsexe));
				utilisateur.setVille(villeService.findOne(idville));
				utilisateur.setPasseWord(password);
				redirect = "home";
			} catch (Exception e) {
				model.addAttribute("message", "Oups, quelque chose c'est mal passé ! :-)");
				redirect = "inscription";
			}
		}
		return redirect;
	}

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null,  new CustomDateEditor(dateFormat, true));
	}	
}