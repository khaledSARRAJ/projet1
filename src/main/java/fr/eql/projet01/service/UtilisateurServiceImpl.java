package fr.eql.projet01.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fr.eql.projet01.dao.DroitsRepository;
import fr.eql.projet01.dao.UtilisateurRepository;
import fr.eql.projet01.entity.Droits;
import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.exception.AecResourceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService{
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired 
	private DroitsRepository droitsRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public boolean findByLogin(String profile) {
		return utilisateurRepository.existsByProfile(profile);
	}

	@Override
	public boolean findByMail(String mail) {
		return utilisateurRepository.existsByMail(mail);
	}

	@Override
	public Utilisateur rechercherUtilisateurParProfil(String profile) {
		if(!utilisateurRepository.existsByProfile(profile))		
			throw new ResourceNotFoundException("Utilisateur introuvable avec ce login : "+profile);
		return utilisateurRepository.findByProfile(profile);
	}

	@Override
	public Utilisateur findOne(Long id) throws ResourceNotFoundException {
		if(!utilisateurRepository.existsById(id))		
			throw new ResourceNotFoundException("Utilisateur introuvable avec cet id : "+id);
		return utilisateurRepository.findById(id).get();
	}	

	@Override
	public List<Utilisateur> search(Long userId) throws ResourceNotFoundException {
		return null;
	}	

	@Override
	public Page<Utilisateur> findAllByPage(Pageable page) {
		return utilisateurRepository.findAll(page);
	}

	@Override
	public List<Utilisateur> findAll() {
		return utilisateurRepository.findAll();
	}

	@Override
	public Utilisateur save(Utilisateur utilisateur) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(utilisateur, "Ne peut pas être null.");
		if(utilisateur.getId() != null) {
			Utilisateur utilisateurFromDB = this.findOne(utilisateur.getId());
			utilisateur.setDateInscription(utilisateurFromDB.getDateInscription());
			if(utilisateur.getDroits() == null)
				utilisateur.setDroits(utilisateurFromDB.getDroits());
			
			if(utilisateur.getPasseWord()==null) {
				utilisateur.setPasseWord(utilisateurFromDB.getPasseWord()); //Sinon, on remet le password déjà haché
			} else {
				utilisateur.setPasseWord(bCryptPasswordEncoder.encode(utilisateur.getPasseWord())); //MAJ du mot de passe s'il a été modifié
			}
		} else {
			utilisateur.setDroits(droitsRepository.findByTypeDroit("Utilisateur"));
			utilisateur.setPasseWord(bCryptPasswordEncoder.encode(utilisateur.getPasseWord()));
		}
		
		Set<ConstraintViolation<Utilisateur>> errors = validator.validate(utilisateur);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<Utilisateur> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public void delete(Long id) throws AecResourceException {
		if(!utilisateurRepository.existsById(id))
			throw new ResourceNotFoundException("Utilisateur inexistante (pas supprimable) pour l'id : "+id);
		utilisateurRepository.findById(id).get();
		utilisateurRepository.deleteById(id);
	}

	@Override
	public long count() {
		return utilisateurRepository.count();
	}

	private void addUtilisateurDroit(Utilisateur utilisateur) {
		Droits droitUtilisateur = new Droits("ROLE_UTILISATEUR");//initialisation du rôle ROLE_USER
		utilisateur.setDroits(droitUtilisateur);
	}

	private void updateUtilisateurDroit(Utilisateur utilisateur) {
		Object droitFromDB = droitsRepository.findById(utilisateur.getDroits().getId());
		utilisateur.setDroits((Droits) droitFromDB);
	}
}