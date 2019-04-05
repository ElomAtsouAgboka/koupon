package tg.opentechconsult.koupon.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Commerce.
 */
@Entity
@Table(name = "commerce")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "commerce")
public class Commerce implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nom_commerce", length = 100, nullable = false, unique = true)
    private String nomCommerce;

    @Column(name = "nom_rue")
    private String nomRue;

    @Column(name = "code_postale")
    private String codePostale;

    @Column(name = "site_web")
    private String siteWeb;

    @Column(name = "desc_commerce")
    private String descCommerce;

    @OneToMany(mappedBy = "commerce")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Deal> deals = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("commerce")
    private TypeDeCommerce typedecommerce;

    @ManyToOne
    @JsonIgnoreProperties("commerce")
    private Marchand marchand;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCommerce() {
        return nomCommerce;
    }

    public Commerce nomCommerce(String nomCommerce) {
        this.nomCommerce = nomCommerce;
        return this;
    }

    public void setNomCommerce(String nomCommerce) {
        this.nomCommerce = nomCommerce;
    }

    public String getNomRue() {
        return nomRue;
    }

    public Commerce nomRue(String nomRue) {
        this.nomRue = nomRue;
        return this;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    public String getCodePostale() {
        return codePostale;
    }

    public Commerce codePostale(String codePostale) {
        this.codePostale = codePostale;
        return this;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public Commerce siteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
        return this;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getDescCommerce() {
        return descCommerce;
    }

    public Commerce descCommerce(String descCommerce) {
        this.descCommerce = descCommerce;
        return this;
    }

    public void setDescCommerce(String descCommerce) {
        this.descCommerce = descCommerce;
    }

    public Set<Deal> getDeals() {
        return deals;
    }

    public Commerce deals(Set<Deal> deals) {
        this.deals = deals;
        return this;
    }

    public Commerce addDeal(Deal deal) {
        this.deals.add(deal);
        deal.setCommerce(this);
        return this;
    }

    public Commerce removeDeal(Deal deal) {
        this.deals.remove(deal);
        deal.setCommerce(null);
        return this;
    }

    public void setDeals(Set<Deal> deals) {
        this.deals = deals;
    }

    public TypeDeCommerce getTypedecommerce() {
        return typedecommerce;
    }

    public Commerce typedecommerce(TypeDeCommerce typeDeCommerce) {
        this.typedecommerce = typeDeCommerce;
        return this;
    }

    public void setTypedecommerce(TypeDeCommerce typeDeCommerce) {
        this.typedecommerce = typeDeCommerce;
    }

    public Marchand getMarchand() {
        return marchand;
    }

    public Commerce marchand(Marchand marchand) {
        this.marchand = marchand;
        return this;
    }

    public void setMarchand(Marchand marchand) {
        this.marchand = marchand;
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
        Commerce commerce = (Commerce) o;
        if (commerce.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commerce.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commerce{" +
            "id=" + getId() +
            ", nomCommerce='" + getNomCommerce() + "'" +
            ", nomRue='" + getNomRue() + "'" +
            ", codePostale='" + getCodePostale() + "'" +
            ", siteWeb='" + getSiteWeb() + "'" +
            ", descCommerce='" + getDescCommerce() + "'" +
            "}";
    }
}
