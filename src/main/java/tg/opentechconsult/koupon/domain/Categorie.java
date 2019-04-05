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
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "categorie")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nom_categorie", length = 50, nullable = false, unique = true)
    private String nomCategorie;

    @OneToMany(mappedBy = "categorieParent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Categorie> categories = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("categories")
    private Categorie categorieParent;

    @ManyToMany(mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Deal> deals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public Categorie nomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
        return this;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public Categorie categories(Set<Categorie> categories) {
        this.categories = categories;
        return this;
    }

    public Categorie addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.setCategorieParent(this);
        return this;
    }

    public Categorie removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.setCategorieParent(null);
        return this;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public Categorie getCategorieParent() {
        return categorieParent;
    }

    public Categorie categorieParent(Categorie categorie) {
        this.categorieParent = categorie;
        return this;
    }

    public void setCategorieParent(Categorie categorie) {
        this.categorieParent = categorie;
    }

    public Set<Deal> getDeals() {
        return deals;
    }

    public Categorie deals(Set<Deal> deals) {
        this.deals = deals;
        return this;
    }

    public Categorie addDeal(Deal deal) {
        this.deals.add(deal);
        deal.getCategories().add(this);
        return this;
    }

    public Categorie removeDeal(Deal deal) {
        this.deals.remove(deal);
        deal.getCategories().remove(this);
        return this;
    }

    public void setDeals(Set<Deal> deals) {
        this.deals = deals;
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
        Categorie categorie = (Categorie) o;
        if (categorie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", nomCategorie='" + getNomCategorie() + "'" +
            "}";
    }
}
