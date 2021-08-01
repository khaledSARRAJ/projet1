package fr.eql.projet01.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedQueries({
	@NamedQuery(name="Utilisateur.findByProfil", query="SELECT u FROM Utilisateur AS u WHERE u.profile=:profil"),
	@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u")})

@Entity
@Getter @Setter @NoArgsConstructor
public class Utilisateur implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String NAME = "tutilisateur";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom;
	@Temporal(TemporalType.DATE) // 2021-05-25 <-- en bdd
	private Date dateNais; 
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Sexe sexe;
	
	@Column(unique = true)
	private String mail;
	private String telephone;
	private String rue;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Ville ville;
	private String complement;
	@Column(unique = true)
	private String profile;
	private String passeWord;
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date dateInscription=new Date();
	@Temporal(TemporalType.DATE)
	private Date dateResiliation;
		
	@OneToMany(fetch=FetchType.LAZY,mappedBy="utilisateur")
	private List<Publication> listPublication;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="utilisateur")
	private List<Annonce> listeAnnonce;
		
	@OneToMany(mappedBy="follower")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Abonnement> Listefollower;
	
	@OneToMany(mappedBy="following")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Abonnement> Listefollowing; 
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Droits droits;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private MotifResiliation MotifResiliation;

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNais=" + dateNais + ", sexe="
				+ sexe + ", mail=" + mail + ", telephone=" + telephone + ", rue=" + rue + ", ville=" + ville
				+ ", complement=" + complement + ", profile=" + profile + ", passeWord=" + passeWord
				+ ", dateInscription=" + dateInscription + ", dateResiliation=" + dateResiliation + ", listeAnnonce="
				+ listeAnnonce + ", droits=" + droits + ", MotifResiliation=" + MotifResiliation + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Listefollower, Listefollowing, MotifResiliation, complement, dateInscription, dateNais,
				dateResiliation, droits, id, listPublication, listeAnnonce, mail, nom, passeWord, prenom, profile, rue,
				sexe, telephone, ville);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(Listefollower, other.Listefollower)
				&& Objects.equals(Listefollowing, other.Listefollowing)
				&& Objects.equals(MotifResiliation, other.MotifResiliation)
				&& Objects.equals(complement, other.complement)
				&& Objects.equals(dateInscription, other.dateInscription) && Objects.equals(dateNais, other.dateNais)
				&& Objects.equals(dateResiliation, other.dateResiliation) && Objects.equals(droits, other.droits)
				&& Objects.equals(id, other.id) && Objects.equals(listPublication, other.listPublication)
				&& Objects.equals(listeAnnonce, other.listeAnnonce) && Objects.equals(mail, other.mail)
				&& Objects.equals(nom, other.nom) && Objects.equals(passeWord, other.passeWord)
				&& Objects.equals(prenom, other.prenom) && Objects.equals(profile, other.profile)
				&& Objects.equals(rue, other.rue) && Objects.equals(sexe, other.sexe)
				&& Objects.equals(telephone, other.telephone) && Objects.equals(ville, other.ville);
	}
	
	
}