package tg.opentechconsult.koupon.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Marchand.
 */
@Entity
@Table(name = "marchand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "marchand")
public class Marchand implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nom_marchand", length = 50, nullable = false)
    private String nomMarchand;

    @NotNull
    @Size(max = 50)
    @Column(name = "prenom_marchand", length = 50, nullable = false)
    private String prenomMarchand;

    @NotNull
    @Column(name = "tel_principale", nullable = false)
    private String telPrincipale;

    @Column(name = "tel_secondaire")
    private String telSecondaire;

    @NotNull
    @Column(name = "email_principale", nullable = false)
    private String emailPrincipale;

    @Column(name = "email_secondaire")
    private String emailSecondaire;

    @Column(name = "newsletter")
    private Boolean newsletter;

    @OneToMany(mappedBy = "marchand")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commerce> commerce = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMarchand() {
        return nomMarchand;
    }

    public Marchand nomMarchand(String nomMarchand) {
        this.nomMarchand = nomMarchand;
        return this;
    }

    public void setNomMarchand(String nomMarchand) {
        this.nomMarchand = nomMarchand;
    }

    public String getPrenomMarchand() {
        return prenomMarchand;
    }

    public Marchand prenomMarchand(String prenomMarchand) {
        this.prenomMarchand = prenomMarchand;
        return this;
    }

    public void setPrenomMarchand(String prenomMarchand) {
        this.prenomMarchand = prenomMarchand;
    }

    public String getTelPrincipale() {
        return telPrincipale;
    }

    public Marchand telPrincipale(String telPrincipale) {
        this.telPrincipale = telPrincipale;
        return this;
    }

    public void setTelPrincipale(String telPrincipale) {
        this.telPrincipale = telPrincipale;
    }

    public String getTelSecondaire() {
        return telSecondaire;
    }

    public Marchand telSecondaire(String telSecondaire) {
        this.telSecondaire = telSecondaire;
        return this;
    }

    public void setTelSecondaire(String telSecondaire) {
        this.telSecondaire = telSecondaire;
    }

    public String getEmailPrincipale() {
        return emailPrincipale;
    }

    public Marchand emailPrincipale(String emailPrincipale) {
        this.emailPrincipale = emailPrincipale;
        return this;
    }

    public void setEmailPrincipale(String emailPrincipale) {
        this.emailPrincipale = emailPrincipale;
    }

    public String getEmailSecondaire() {
        return emailSecondaire;
    }

    public Marchand emailSecondaire(String emailSecondaire) {
        this.emailSecondaire = emailSecondaire;
        return this;
    }

    public void setEmailSecondaire(String emailSecondaire) {
        this.emailSecondaire = emailSecondaire;
    }

    public Boolean isNewsletter() {
        return newsletter;
    }

    public Marchand newsletter(Boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    public Set<Commerce> getCommerce() {
        return commerce;
    }

    public Marchand commerce(Set<Commerce> commerce) {
        this.commerce = commerce;
        return this;
    }

    public Marchand addCommerce(Commerce commerce) {
        this.commerce.add(commerce);
        commerce.setMarchand(this);
        return this;
    }

    public Marchand removeCommerce(Commerce commerce) {
        this.commerce.remove(commerce);
        commerce.setMarchand(null);
        return this;
    }

    public void setCommerce(Set<Commerce> commerce) {
        this.commerce = commerce;
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
        Marchand marchand = (Marchand) o;
        if (marchand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marchand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Marchand{" +
            "id=" + getId() +
            ", nomMarchand='" + getNomMarchand() + "'" +
            ", prenomMarchand='" + getPrenomMarchand() + "'" +
            ", telPrincipale='" + getTelPrincipale() + "'" +
            ", telSecondaire='" + getTelSecondaire() + "'" +
            ", emailPrincipale='" + getEmailPrincipale() + "'" +
            ", emailSecondaire='" + getEmailSecondaire() + "'" +
            ", newsletter='" + isNewsletter() + "'" +
            "}";
    }
}
