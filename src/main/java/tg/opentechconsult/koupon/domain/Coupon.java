package tg.opentechconsult.koupon.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Coupon.
 */
@Entity
@Table(name = "coupon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "ref_coupon", length = 10, nullable = false)
    private String refCoupon;

    @Column(name = "date_achat")
    private String dateAchat;

    @Column(name = "date_utilisation")
    private String dateUtilisation;

    @Column(name = "est_cadeaux")
    private Boolean estCadeaux;

    @ManyToOne
    @JsonIgnoreProperties("coupons")
    private Deal deal;

    @ManyToOne
    @JsonIgnoreProperties("coupons")
    private Commande commande;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefCoupon() {
        return refCoupon;
    }

    public Coupon refCoupon(String refCoupon) {
        this.refCoupon = refCoupon;
        return this;
    }

    public void setRefCoupon(String refCoupon) {
        this.refCoupon = refCoupon;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public Coupon dateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
        return this;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getDateUtilisation() {
        return dateUtilisation;
    }

    public Coupon dateUtilisation(String dateUtilisation) {
        this.dateUtilisation = dateUtilisation;
        return this;
    }

    public void setDateUtilisation(String dateUtilisation) {
        this.dateUtilisation = dateUtilisation;
    }

    public Boolean isEstCadeaux() {
        return estCadeaux;
    }

    public Coupon estCadeaux(Boolean estCadeaux) {
        this.estCadeaux = estCadeaux;
        return this;
    }

    public void setEstCadeaux(Boolean estCadeaux) {
        this.estCadeaux = estCadeaux;
    }

    public Deal getDeal() {
        return deal;
    }

    public Coupon deal(Deal deal) {
        this.deal = deal;
        return this;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Commande getCommande() {
        return commande;
    }

    public Coupon commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
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
        Coupon coupon = (Coupon) o;
        if (coupon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coupon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coupon{" +
            "id=" + getId() +
            ", refCoupon='" + getRefCoupon() + "'" +
            ", dateAchat='" + getDateAchat() + "'" +
            ", dateUtilisation='" + getDateUtilisation() + "'" +
            ", estCadeaux='" + isEstCadeaux() + "'" +
            "}";
    }
}
