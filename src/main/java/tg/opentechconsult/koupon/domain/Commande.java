package tg.opentechconsult.koupon.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "ref_commande", length = 10, nullable = false, unique = true)
    private String refCommande;

    @Column(name = "date_commande")
    private LocalDate dateCommande;

    @OneToMany(mappedBy = "commande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Coupon> coupons = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("commandes")
    private Membre membre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefCommande() {
        return refCommande;
    }

    public Commande refCommande(String refCommande) {
        this.refCommande = refCommande;
        return this;
    }

    public void setRefCommande(String refCommande) {
        this.refCommande = refCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public Commande dateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public Commande coupons(Set<Coupon> coupons) {
        this.coupons = coupons;
        return this;
    }

    public Commande addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
        coupon.setCommande(this);
        return this;
    }

    public Commande removeCoupon(Coupon coupon) {
        this.coupons.remove(coupon);
        coupon.setCommande(null);
        return this;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    public Membre getMembre() {
        return membre;
    }

    public Commande membre(Membre membre) {
        this.membre = membre;
        return this;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
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
        Commande commande = (Commande) o;
        if (commande.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commande.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", refCommande='" + getRefCommande() + "'" +
            ", dateCommande='" + getDateCommande() + "'" +
            "}";
    }
}
