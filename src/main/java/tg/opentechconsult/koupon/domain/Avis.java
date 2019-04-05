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
 * A Avis.
 */
@Entity
@Table(name = "avis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "avis")
public class Avis implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "text_avis", length = 255, nullable = false)
    private String textAvis;

    @Column(name = "derniere_utilisation_coupon")
    private Integer derniereUtilisationCoupon;

    @ManyToOne
    @JsonIgnoreProperties("avis")
    private Deal deal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextAvis() {
        return textAvis;
    }

    public Avis textAvis(String textAvis) {
        this.textAvis = textAvis;
        return this;
    }

    public void setTextAvis(String textAvis) {
        this.textAvis = textAvis;
    }

    public Integer getDerniereUtilisationCoupon() {
        return derniereUtilisationCoupon;
    }

    public Avis derniereUtilisationCoupon(Integer derniereUtilisationCoupon) {
        this.derniereUtilisationCoupon = derniereUtilisationCoupon;
        return this;
    }

    public void setDerniereUtilisationCoupon(Integer derniereUtilisationCoupon) {
        this.derniereUtilisationCoupon = derniereUtilisationCoupon;
    }

    public Deal getDeal() {
        return deal;
    }

    public Avis deal(Deal deal) {
        this.deal = deal;
        return this;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
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
        Avis avis = (Avis) o;
        if (avis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), avis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Avis{" +
            "id=" + getId() +
            ", textAvis='" + getTextAvis() + "'" +
            ", derniereUtilisationCoupon=" + getDerniereUtilisationCoupon() +
            "}";
    }
}
