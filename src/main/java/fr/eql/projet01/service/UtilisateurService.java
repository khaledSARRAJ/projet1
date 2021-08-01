package fr.eql.projet01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.exception.AecResourceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

public interface UtilisateurService {
	Utilisateur rechercherUtilisateurParProfil(String profil);
	boolean findByLogin(String profile);
	boolean findByMail(String mail);
	Utilisateur findOne(Long id) throws ResourceNotFoundException;
	Page<Utilisateur> findAllByPage(Pageable page);
	List<Utilisateur> findAll();
	List<Utilisateur> search(Long userId) throws ResourceNotFoundException;
	Utilisateur save(Utilisateur utilisateur) throws NotValidObjectException, ResourceNotFoundException; 
	void delete(Long id) throws AecResourceException;
	long count();
}