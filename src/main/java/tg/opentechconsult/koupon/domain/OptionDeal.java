package tg.opentechconsult.koupon.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OptionDeal.
 */
@Entity
@Table(name = "option_deal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "optiondeal")
public class OptionDeal implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "lib_option_deal")
    private String libOptionDeal;

    @Column(name = "prix_normal_option_deal")
    private Integer prixNormalOptionDeal;

    @Column(name = "prix_reduction_option_bon_plan")
    private Integer prixReductionOptionBonPlan;

    @Column(name = "pc_reduction_option_bon_plan")
    private Integer pcReductionOptionBonPlan;

    @ManyToOne
    @JsonIgnoreProperties("optiondeals")
    private Deal deal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibOptionDeal() {
        return libOptionDeal;
    }

    public OptionDeal libOptionDeal(String libOptionDeal) {
        this.libOptionDeal = libOptionDeal;
        return this;
    }

    public void setLibOptionDeal(String libOptionDeal) {
        this.libOptionDeal = libOptionDeal;
    }

    public Integer getPrixNormalOptionDeal() {
        return prixNormalOptionDeal;
    }

    public OptionDeal prixNormalOptionDeal(Integer prixNormalOptionDeal) {
        this.prixNormalOptionDeal = prixNormalOptionDeal;
        return this;
    }

    public void setPrixNormalOptionDeal(Integer prixNormalOptionDeal) {
        this.prixNormalOptionDeal = prixNormalOptionDeal;
    }

    public Integer getPrixReductionOptionBonPlan() {
        return prixReductionOptionBonPlan;
    }

    public OptionDeal prixReductionOptionBonPlan(Integer prixReductionOptionBonPlan) {
        this.prixReductionOptionBonPlan = prixReductionOptionBonPlan;
        return this;
    }

    public void setPrixReductionOptionBonPlan(Integer prixReductionOptionBonPlan) {
        this.prixReductionOptionBonPlan = prixReductionOptionBonPlan;
    }

    public Integer getPcReductionOptionBonPlan() {
        return pcReductionOptionBonPlan;
    }

    public OptionDeal pcReductionOptionBonPlan(Integer pcReductionOptionBonPlan) {
        this.pcReductionOptionBonPlan = pcReductionOptionBonPlan;
        return this;
    }

    public void setPcReductionOptionBonPlan(Integer pcReductionOptionBonPlan) {
        this.pcReductionOptionBonPlan = pcReductionOptionBonPlan;
    }

    public Deal getDeal() {
        return deal;
    }

    public OptionDeal deal(Deal deal) {
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
        OptionDeal optionDeal = (OptionDeal) o;
        if (optionDeal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), optionDeal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OptionDeal{" +
            "id=" + getId() +
            ", libOptionDeal='" + getLibOptionDeal() + "'" +
            ", prixNormalOptionDeal=" + getPrixNormalOptionDeal() +
            ", prixReductionOptionBonPlan=" + getPrixReductionOptionBonPlan() +
            ", pcReductionOptionBonPlan=" + getPcReductionOptionBonPlan() +
            "}";
    }
}
