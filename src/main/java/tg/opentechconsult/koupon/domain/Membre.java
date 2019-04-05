package tg.opentechconsult.koupon.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Membre.
 */
@Entity
@Table(name = "membre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "membre")
public class Membre implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "login_member", length = 50, nullable = false, unique = true)
    private String loginMember;

    @NotNull
    @Size(max = 50)
    @Column(name = "nom_membre", length = 50, nullable = false)
    private String nomMembre;

    @Size(max = 50)
    @Column(name = "prenom_membre", length = 50)
    private String prenomMembre;

    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    @Column(name = "email_membre")
    private String emailMembre;

    @Column(name = "souscrire_mail_perso")
    private Boolean souscrireMailPerso;

    @OneToMany(mappedBy = "membre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commande> commandes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginMember() {
        return loginMember;
    }

    public Membre loginMember(String loginMember) {
        this.loginMember = loginMember;
        return this;
    }

    public void setLoginMember(String loginMember) {
        this.loginMember = loginMember;
    }

    public String getNomMembre() {
        return nomMembre;
    }

    public Membre nomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
        return this;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    public String getPrenomMembre() {
        return prenomMembre;
    }

    public Membre prenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
        return this;
    }

    public void setPrenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public Membre dateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getEmailMembre() {
        return emailMembre;
    }

    public Membre emailMembre(String emailMembre) {
        this.emailMembre = emailMembre;
        return this;
    }

    public void setEmailMembre(String emailMembre) {
        this.emailMembre = emailMembre;
    }

    public Boolean isSouscrireMailPerso() {
        return souscrireMailPerso;
    }

    public Membre souscrireMailPerso(Boolean souscrireMailPerso) {
        this.souscrireMailPerso = souscrireMailPerso;
        return this;
    }

    public void setSouscrireMailPerso(Boolean souscrireMailPerso) {
        this.souscrireMailPerso = souscrireMailPerso;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public Membre commandes(Set<Commande> commandes) {
        this.commandes = commandes;
        return this;
    }

    public Membre addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setMembre(this);
        return this;
    }

    public Membre removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setMembre(null);
        return this;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Membre membre = (Membre) o;
        if (membre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Membre{" +
            "id=" + getId() +
            ", loginMember='" + getLoginMember() + "'" +
            ", nomMembre='" + getNomMembre() + "'" +
            ", prenomMembre='" + getPrenomMembre() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", emailMembre='" + getEmailMembre() + "'" +
            ", souscrireMailPerso='" + isSouscrireMailPerso() + "'" +
            "}";
    }
}
