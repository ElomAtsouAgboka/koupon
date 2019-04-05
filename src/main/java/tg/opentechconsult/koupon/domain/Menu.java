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
 * A Menu.
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "menu_item", length = 50, nullable = false, unique = true)
    private String menuItem;

    @Column(name = "menu_item_img")
    private String menuItemImg;

    @OneToMany(mappedBy = "menuParent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Menu> menus = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("menus")
    private Menu menuParent;

    @ManyToMany(mappedBy = "menus")
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

    public String getMenuItem() {
        return menuItem;
    }

    public Menu menuItem(String menuItem) {
        this.menuItem = menuItem;
        return this;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getMenuItemImg() {
        return menuItemImg;
    }

    public Menu menuItemImg(String menuItemImg) {
        this.menuItemImg = menuItemImg;
        return this;
    }

    public void setMenuItemImg(String menuItemImg) {
        this.menuItemImg = menuItemImg;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Menu menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Menu addMenu(Menu menu) {
        this.menus.add(menu);
        menu.setMenuParent(this);
        return this;
    }

    public Menu removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.setMenuParent(null);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Menu getMenuParent() {
        return menuParent;
    }

    public Menu menuParent(Menu menu) {
        this.menuParent = menu;
        return this;
    }

    public void setMenuParent(Menu menu) {
        this.menuParent = menu;
    }

    public Set<Deal> getDeals() {
        return deals;
    }

    public Menu deals(Set<Deal> deals) {
        this.deals = deals;
        return this;
    }

    public Menu addDeal(Deal deal) {
        this.deals.add(deal);
        deal.getMenus().add(this);
        return this;
    }

    public Menu removeDeal(Deal deal) {
        this.deals.remove(deal);
        deal.getMenus().remove(this);
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
        Menu menu = (Menu) o;
        if (menu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", menuItem='" + getMenuItem() + "'" +
            ", menuItemImg='" + getMenuItemImg() + "'" +
            "}";
    }
}
