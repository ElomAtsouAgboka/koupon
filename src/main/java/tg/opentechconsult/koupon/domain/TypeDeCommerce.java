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
 * A TypeDeCommerce.
 */
@Entity
@Table(name = "type_de_commerce")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typedecommerce")
public class TypeDeCommerce implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nom_type_de_commerce", length = 50, nullable = false, unique = true)
    private String nomTypeDeCommerce;

    @OneToMany(mappedBy = "typedecommerce")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commerce> commerce = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTypeDeCommerce() {
        return nomTypeDeCommerce;
    }

    public TypeDeCommerce nomTypeDeCommerce(String nomTypeDeCommerce) {
        this.nomTypeDeCommerce = nomTypeDeCommerce;
        return this;
    }

    public void setNomTypeDeCommerce(String nomTypeDeCommerce) {
        this.nomTypeDeCommerce = nomTypeDeCommerce;
    }

    public Set<Commerce> getCommerce() {
        return commerce;
    }

    public TypeDeCommerce commerce(Set<Commerce> commerce) {
        this.commerce = commerce;
        return this;
    }

    public TypeDeCommerce addCommerce(Commerce commerce) {
        this.commerce.add(commerce);
        commerce.setTypedecommerce(this);
        return this;
    }

    public TypeDeCommerce removeCommerce(Commerce commerce) {
        this.commerce.remove(commerce);
        commerce.setTypedecommerce(null);
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
        TypeDeCommerce typeDeCommerce = (TypeDeCommerce) o;
        if (typeDeCommerce.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeDeCommerce.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeDeCommerce{" +
            "id=" + getId() +
            ", nomTypeDeCommerce='" + getNomTypeDeCommerce() + "'" +
            "}";
    }
}
