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
 * A Deal.
 */
@Entity
@Table(name = "deal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "deal")
public class Deal implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "ref_deal", length = 10, nullable = false, unique = true)
    private String refDeal;

    @NotNull
    @Size(max = 100)
    @Column(name = "titre_deal", length = 100, nullable = false)
    private String titreDeal;

    @NotNull
    @Size(max = 255)
    @Column(name = "description_deal", length = 255, nullable = false)
    private String descriptionDeal;

    @NotNull
    @Column(name = "prix_reduit_deal", nullable = false)
    private String prixReduitDeal;

    @NotNull
    @Column(name = "prix_normal_deal", nullable = false)
    private Integer prixNormalDeal;

    @NotNull
    @Column(name = "pc_reduction_deal", nullable = false)
    private Integer pcReductionDeal;

    @NotNull
    @Column(name = "photo_deal_un", nullable = false)
    private String photoDealUn;

    @NotNull
    @Column(name = "photo_deal_deux", nullable = false)
    private String photoDealDeux;

    @NotNull
    @Column(name = "photo_deal_trois", nullable = false)
    private String photoDealTrois;

    @Column(name = "photo_deal_quatre")
    private String photoDealQuatre;

    @Column(name = "photo_deal_cinq")
    private String photoDealCinq;

    @Column(name = "photo_deal_six")
    private String photoDealSix;

    @Column(name = "photo_deal_spet")
    private String photoDealSpet;

    @Column(name = "photo_deal_huit")
    private String photoDealHuit;

    @Column(name = "photo_deal_neuf")
    private String photoDealNeuf;

    @Column(name = "photo_deal_dix")
    private String photoDealDix;

    @NotNull
    @Column(name = "photo_min_deal_un", nullable = false)
    private String photoMinDealUn;

    @NotNull
    @Column(name = "photo_min_deal_deux", nullable = false)
    private String photoMinDealDeux;

    @NotNull
    @Column(name = "photo_min_deal_trois", nullable = false)
    private String photoMinDealTrois;

    @Column(name = "photo_min_deal_quatre")
    private String photoMinDealQuatre;

    @Column(name = "photo_min_deal_cinq")
    private String photoMinDealCinq;

    @Column(name = "photo_min_deal_six")
    private String photoMinDealSix;

    @Column(name = "photo_min_deal_spet")
    private String photoMinDealSpet;

    @Column(name = "photo_min_deal_huit")
    private String photoMinDealHuit;

    @Column(name = "photo_min_deal_neuf")
    private String photoMinDealNeuf;

    @Column(name = "photo_min_deal_dix")
    private String photoMinDealDix;

    @Column(name = "desc_point_fort_deal")
    private String descPointFortDeal;

    @Column(name = "details_offre_deal")
    private String detailsOffreDeal;

    @Column(name = "conditions_deal")
    private String conditionsDeal;

    @Column(name = "est_limite")
    private Boolean estLimite;

    @Column(name = "est_epuise")
    private Boolean estEpuise;

    @Column(name = "date_creation_deal")
    private LocalDate dateCreationDeal;

    @Column(name = "date_cloture_deal")
    private LocalDate dateClotureDeal;

    @OneToMany(mappedBy = "deal")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OptionDeal> optiondeals = new HashSet<>();
    @OneToMany(mappedBy = "deal")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Coupon> coupons = new HashSet<>();
    @OneToMany(mappedBy = "deal")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Avis> avis = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "deal_menu",
               joinColumns = @JoinColumn(name = "deal_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    private Set<Menu> menus = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "deal_categorie",
               joinColumns = @JoinColumn(name = "deal_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categorie_id", referencedColumnName = "id"))
    private Set<Categorie> categories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("deals")
    private Commerce commerce;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefDeal() {
        return refDeal;
    }

    public Deal refDeal(String refDeal) {
        this.refDeal = refDeal;
        return this;
    }

    public void setRefDeal(String refDeal) {
        this.refDeal = refDeal;
    }

    public String getTitreDeal() {
        return titreDeal;
    }

    public Deal titreDeal(String titreDeal) {
        this.titreDeal = titreDeal;
        return this;
    }

    public void setTitreDeal(String titreDeal) {
        this.titreDeal = titreDeal;
    }

    public String getDescriptionDeal() {
        return descriptionDeal;
    }

    public Deal descriptionDeal(String descriptionDeal) {
        this.descriptionDeal = descriptionDeal;
        return this;
    }

    public void setDescriptionDeal(String descriptionDeal) {
        this.descriptionDeal = descriptionDeal;
    }

    public String getPrixReduitDeal() {
        return prixReduitDeal;
    }

    public Deal prixReduitDeal(String prixReduitDeal) {
        this.prixReduitDeal = prixReduitDeal;
        return this;
    }

    public void setPrixReduitDeal(String prixReduitDeal) {
        this.prixReduitDeal = prixReduitDeal;
    }

    public Integer getPrixNormalDeal() {
        return prixNormalDeal;
    }

    public Deal prixNormalDeal(Integer prixNormalDeal) {
        this.prixNormalDeal = prixNormalDeal;
        return this;
    }

    public void setPrixNormalDeal(Integer prixNormalDeal) {
        this.prixNormalDeal = prixNormalDeal;
    }

    public Integer getPcReductionDeal() {
        return pcReductionDeal;
    }

    public Deal pcReductionDeal(Integer pcReductionDeal) {
        this.pcReductionDeal = pcReductionDeal;
        return this;
    }

    public void setPcReductionDeal(Integer pcReductionDeal) {
        this.pcReductionDeal = pcReductionDeal;
    }

    public String getPhotoDealUn() {
        return photoDealUn;
    }

    public Deal photoDealUn(String photoDealUn) {
        this.photoDealUn = photoDealUn;
        return this;
    }

    public void setPhotoDealUn(String photoDealUn) {
        this.photoDealUn = photoDealUn;
    }

    public String getPhotoDealDeux() {
        return photoDealDeux;
    }

    public Deal photoDealDeux(String photoDealDeux) {
        this.photoDealDeux = photoDealDeux;
        return this;
    }

    public void setPhotoDealDeux(String photoDealDeux) {
        this.photoDealDeux = photoDealDeux;
    }

    public String getPhotoDealTrois() {
        return photoDealTrois;
    }

    public Deal photoDealTrois(String photoDealTrois) {
        this.photoDealTrois = photoDealTrois;
        return this;
    }

    public void setPhotoDealTrois(String photoDealTrois) {
        this.photoDealTrois = photoDealTrois;
    }

    public String getPhotoDealQuatre() {
        return photoDealQuatre;
    }

    public Deal photoDealQuatre(String photoDealQuatre) {
        this.photoDealQuatre = photoDealQuatre;
        return this;
    }

    public void setPhotoDealQuatre(String photoDealQuatre) {
        this.photoDealQuatre = photoDealQuatre;
    }

    public String getPhotoDealCinq() {
        return photoDealCinq;
    }

    public Deal photoDealCinq(String photoDealCinq) {
        this.photoDealCinq = photoDealCinq;
        return this;
    }

    public void setPhotoDealCinq(String photoDealCinq) {
        this.photoDealCinq = photoDealCinq;
    }

    public String getPhotoDealSix() {
        return photoDealSix;
    }

    public Deal photoDealSix(String photoDealSix) {
        this.photoDealSix = photoDealSix;
        return this;
    }

    public void setPhotoDealSix(String photoDealSix) {
        this.photoDealSix = photoDealSix;
    }

    public String getPhotoDealSpet() {
        return photoDealSpet;
    }

    public Deal photoDealSpet(String photoDealSpet) {
        this.photoDealSpet = photoDealSpet;
        return this;
    }

    public void setPhotoDealSpet(String photoDealSpet) {
        this.photoDealSpet = photoDealSpet;
    }

    public String getPhotoDealHuit() {
        return photoDealHuit;
    }

    public Deal photoDealHuit(String photoDealHuit) {
        this.photoDealHuit = photoDealHuit;
        return this;
    }

    public void setPhotoDealHuit(String photoDealHuit) {
        this.photoDealHuit = photoDealHuit;
    }

    public String getPhotoDealNeuf() {
        return photoDealNeuf;
    }

    public Deal photoDealNeuf(String photoDealNeuf) {
        this.photoDealNeuf = photoDealNeuf;
        return this;
    }

    public void setPhotoDealNeuf(String photoDealNeuf) {
        this.photoDealNeuf = photoDealNeuf;
    }

    public String getPhotoDealDix() {
        return photoDealDix;
    }

    public Deal photoDealDix(String photoDealDix) {
        this.photoDealDix = photoDealDix;
        return this;
    }

    public void setPhotoDealDix(String photoDealDix) {
        this.photoDealDix = photoDealDix;
    }

    public String getPhotoMinDealUn() {
        return photoMinDealUn;
    }

    public Deal photoMinDealUn(String photoMinDealUn) {
        this.photoMinDealUn = photoMinDealUn;
        return this;
    }

    public void setPhotoMinDealUn(String photoMinDealUn) {
        this.photoMinDealUn = photoMinDealUn;
    }

    public String getPhotoMinDealDeux() {
        return photoMinDealDeux;
    }

    public Deal photoMinDealDeux(String photoMinDealDeux) {
        this.photoMinDealDeux = photoMinDealDeux;
        return this;
    }

    public void setPhotoMinDealDeux(String photoMinDealDeux) {
        this.photoMinDealDeux = photoMinDealDeux;
    }

    public String getPhotoMinDealTrois() {
        return photoMinDealTrois;
    }

    public Deal photoMinDealTrois(String photoMinDealTrois) {
        this.photoMinDealTrois = photoMinDealTrois;
        return this;
    }

    public void setPhotoMinDealTrois(String photoMinDealTrois) {
        this.photoMinDealTrois = photoMinDealTrois;
    }

    public String getPhotoMinDealQuatre() {
        return photoMinDealQuatre;
    }

    public Deal photoMinDealQuatre(String photoMinDealQuatre) {
        this.photoMinDealQuatre = photoMinDealQuatre;
        return this;
    }

    public void setPhotoMinDealQuatre(String photoMinDealQuatre) {
        this.photoMinDealQuatre = photoMinDealQuatre;
    }

    public String getPhotoMinDealCinq() {
        return photoMinDealCinq;
    }

    public Deal photoMinDealCinq(String photoMinDealCinq) {
        this.photoMinDealCinq = photoMinDealCinq;
        return this;
    }

    public void setPhotoMinDealCinq(String photoMinDealCinq) {
        this.photoMinDealCinq = photoMinDealCinq;
    }

    public String getPhotoMinDealSix() {
        return photoMinDealSix;
    }

    public Deal photoMinDealSix(String photoMinDealSix) {
        this.photoMinDealSix = photoMinDealSix;
        return this;
    }

    public void setPhotoMinDealSix(String photoMinDealSix) {
        this.photoMinDealSix = photoMinDealSix;
    }

    public String getPhotoMinDealSpet() {
        return photoMinDealSpet;
    }

    public Deal photoMinDealSpet(String photoMinDealSpet) {
        this.photoMinDealSpet = photoMinDealSpet;
        return this;
    }

    public void setPhotoMinDealSpet(String photoMinDealSpet) {
        this.photoMinDealSpet = photoMinDealSpet;
    }

    public String getPhotoMinDealHuit() {
        return photoMinDealHuit;
    }

    public Deal photoMinDealHuit(String photoMinDealHuit) {
        this.photoMinDealHuit = photoMinDealHuit;
        return this;
    }

    public void setPhotoMinDealHuit(String photoMinDealHuit) {
        this.photoMinDealHuit = photoMinDealHuit;
    }

    public String getPhotoMinDealNeuf() {
        return photoMinDealNeuf;
    }

    public Deal photoMinDealNeuf(String photoMinDealNeuf) {
        this.photoMinDealNeuf = photoMinDealNeuf;
        return this;
    }

    public void setPhotoMinDealNeuf(String photoMinDealNeuf) {
        this.photoMinDealNeuf = photoMinDealNeuf;
    }

    public String getPhotoMinDealDix() {
        return photoMinDealDix;
    }

    public Deal photoMinDealDix(String photoMinDealDix) {
        this.photoMinDealDix = photoMinDealDix;
        return this;
    }

    public void setPhotoMinDealDix(String photoMinDealDix) {
        this.photoMinDealDix = photoMinDealDix;
    }

    public String getDescPointFortDeal() {
        return descPointFortDeal;
    }

    public Deal descPointFortDeal(String descPointFortDeal) {
        this.descPointFortDeal = descPointFortDeal;
        return this;
    }

    public void setDescPointFortDeal(String descPointFortDeal) {
        this.descPointFortDeal = descPointFortDeal;
    }

    public String getDetailsOffreDeal() {
        return detailsOffreDeal;
    }

    public Deal detailsOffreDeal(String detailsOffreDeal) {
        this.detailsOffreDeal = detailsOffreDeal;
        return this;
    }

    public void setDetailsOffreDeal(String detailsOffreDeal) {
        this.detailsOffreDeal = detailsOffreDeal;
    }

    public String getConditionsDeal() {
        return conditionsDeal;
    }

    public Deal conditionsDeal(String conditionsDeal) {
        this.conditionsDeal = conditionsDeal;
        return this;
    }

    public void setConditionsDeal(String conditionsDeal) {
        this.conditionsDeal = conditionsDeal;
    }

    public Boolean isEstLimite() {
        return estLimite;
    }

    public Deal estLimite(Boolean estLimite) {
        this.estLimite = estLimite;
        return this;
    }

    public void setEstLimite(Boolean estLimite) {
        this.estLimite = estLimite;
    }

    public Boolean isEstEpuise() {
        return estEpuise;
    }

    public Deal estEpuise(Boolean estEpuise) {
        this.estEpuise = estEpuise;
        return this;
    }

    public void setEstEpuise(Boolean estEpuise) {
        this.estEpuise = estEpuise;
    }

    public LocalDate getDateCreationDeal() {
        return dateCreationDeal;
    }

    public Deal dateCreationDeal(LocalDate dateCreationDeal) {
        this.dateCreationDeal = dateCreationDeal;
        return this;
    }

    public void setDateCreationDeal(LocalDate dateCreationDeal) {
        this.dateCreationDeal = dateCreationDeal;
    }

    public LocalDate getDateClotureDeal() {
        return dateClotureDeal;
    }

    public Deal dateClotureDeal(LocalDate dateClotureDeal) {
        this.dateClotureDeal = dateClotureDeal;
        return this;
    }

    public void setDateClotureDeal(LocalDate dateClotureDeal) {
        this.dateClotureDeal = dateClotureDeal;
    }

    public Set<OptionDeal> getOptiondeals() {
        return optiondeals;
    }

    public Deal optiondeals(Set<OptionDeal> optionDeals) {
        this.optiondeals = optionDeals;
        return this;
    }

    public Deal addOptiondeal(OptionDeal optionDeal) {
        this.optiondeals.add(optionDeal);
        optionDeal.setDeal(this);
        return this;
    }

    public Deal removeOptiondeal(OptionDeal optionDeal) {
        this.optiondeals.remove(optionDeal);
        optionDeal.setDeal(null);
        return this;
    }

    public void setOptiondeals(Set<OptionDeal> optionDeals) {
        this.optiondeals = optionDeals;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public Deal coupons(Set<Coupon> coupons) {
        this.coupons = coupons;
        return this;
    }

    public Deal addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
        coupon.setDeal(this);
        return this;
    }

    public Deal removeCoupon(Coupon coupon) {
        this.coupons.remove(coupon);
        coupon.setDeal(null);
        return this;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    public Set<Avis> getAvis() {
        return avis;
    }

    public Deal avis(Set<Avis> avis) {
        this.avis = avis;
        return this;
    }

    public Deal addAvis(Avis avis) {
        this.avis.add(avis);
        avis.setDeal(this);
        return this;
    }

    public Deal removeAvis(Avis avis) {
        this.avis.remove(avis);
        avis.setDeal(null);
        return this;
    }

    public void setAvis(Set<Avis> avis) {
        this.avis = avis;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Deal menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Deal addMenu(Menu menu) {
        this.menus.add(menu);
        menu.getDeals().add(this);
        return this;
    }

    public Deal removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.getDeals().remove(this);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public Deal categories(Set<Categorie> categories) {
        this.categories = categories;
        return this;
    }

    public Deal addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.getDeals().add(this);
        return this;
    }

    public Deal removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.getDeals().remove(this);
        return this;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public Deal commerce(Commerce commerce) {
        this.commerce = commerce;
        return this;
    }

    public void setCommerce(Commerce commerce) {
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
        Deal deal = (Deal) o;
        if (deal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Deal{" +
            "id=" + getId() +
            ", refDeal='" + getRefDeal() + "'" +
            ", titreDeal='" + getTitreDeal() + "'" +
            ", descriptionDeal='" + getDescriptionDeal() + "'" +
            ", prixReduitDeal='" + getPrixReduitDeal() + "'" +
            ", prixNormalDeal=" + getPrixNormalDeal() +
            ", pcReductionDeal=" + getPcReductionDeal() +
            ", photoDealUn='" + getPhotoDealUn() + "'" +
            ", photoDealDeux='" + getPhotoDealDeux() + "'" +
            ", photoDealTrois='" + getPhotoDealTrois() + "'" +
            ", photoDealQuatre='" + getPhotoDealQuatre() + "'" +
            ", photoDealCinq='" + getPhotoDealCinq() + "'" +
            ", photoDealSix='" + getPhotoDealSix() + "'" +
            ", photoDealSpet='" + getPhotoDealSpet() + "'" +
            ", photoDealHuit='" + getPhotoDealHuit() + "'" +
            ", photoDealNeuf='" + getPhotoDealNeuf() + "'" +
            ", photoDealDix='" + getPhotoDealDix() + "'" +
            ", photoMinDealUn='" + getPhotoMinDealUn() + "'" +
            ", photoMinDealDeux='" + getPhotoMinDealDeux() + "'" +
            ", photoMinDealTrois='" + getPhotoMinDealTrois() + "'" +
            ", photoMinDealQuatre='" + getPhotoMinDealQuatre() + "'" +
            ", photoMinDealCinq='" + getPhotoMinDealCinq() + "'" +
            ", photoMinDealSix='" + getPhotoMinDealSix() + "'" +
            ", photoMinDealSpet='" + getPhotoMinDealSpet() + "'" +
            ", photoMinDealHuit='" + getPhotoMinDealHuit() + "'" +
            ", photoMinDealNeuf='" + getPhotoMinDealNeuf() + "'" +
            ", photoMinDealDix='" + getPhotoMinDealDix() + "'" +
            ", descPointFortDeal='" + getDescPointFortDeal() + "'" +
            ", detailsOffreDeal='" + getDetailsOffreDeal() + "'" +
            ", conditionsDeal='" + getConditionsDeal() + "'" +
            ", estLimite='" + isEstLimite() + "'" +
            ", estEpuise='" + isEstEpuise() + "'" +
            ", dateCreationDeal='" + getDateCreationDeal() + "'" +
            ", dateClotureDeal='" + getDateClotureDeal() + "'" +
            "}";
    }
}
