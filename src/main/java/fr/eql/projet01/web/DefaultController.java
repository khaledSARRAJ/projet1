package fr.eql.projet01.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.service.UtilisateurService;

@Controller
public class DefaultController {
	@Autowired
	private UtilisateurService utilisateurService;

	@GetMapping("/")
	public String home1() {
		return "home";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/espaceConnect")
	public RedirectView espaceConnect(HttpSession session) {
		Utilisateur uti = null;
		RedirectView rv = new RedirectView();
		rv.setContextRelative(true);
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				String username = ((UserDetails)principal).getUsername();
				uti = utilisateurService.rechercherUtilisateurParProfil(username);
				session.setAttribute("utilisateur", uti);
			}
			rv.setUrl("/mur?id="+ uti.getId());
		} catch(Exception e)
		{
			rv.setUrl("/home");
		} 
		return rv;
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping("/user")
	public String user(Model model) {
		model.addAttribute("users", utilisateurService.findAll());
		return "user";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/403")
	public String error403() {
		return "error/403";
	}

	@RequestMapping("/session-end")
	public RedirectView finSession(Model model,HttpSession session) {
		RedirectView rv = new RedirectView();
		SecurityContextHolder.clearContext(); //RAZ context Spring security
		session.invalidate();
		rv.setContextRelative(false);
		rv.setUrl("/home");
		return rv; 
	}
	
	@ModelAttribute("idSession")
	public String idSession(HttpSession session) {
		return session.getId();
	}
}