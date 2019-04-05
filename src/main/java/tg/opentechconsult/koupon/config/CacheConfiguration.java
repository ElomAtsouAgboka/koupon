package tg.opentechconsult.koupon.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(tg.opentechconsult.koupon.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Pays.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Pays.class.getName() + ".villes", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Ville.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Ville.class.getName() + ".quartiers", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Quartier.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Categorie.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Categorie.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Categorie.class.getName() + ".deals", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Menu.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Menu.class.getName() + ".menus", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Menu.class.getName() + ".deals", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.TypeDeCommerce.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.TypeDeCommerce.class.getName() + ".commerce", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Commerce.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Commerce.class.getName() + ".deals", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Marchand.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Marchand.class.getName() + ".commerce", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Deal.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Deal.class.getName() + ".optiondeals", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Deal.class.getName() + ".coupons", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Deal.class.getName() + ".avis", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Deal.class.getName() + ".menus", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Deal.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.OptionDeal.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Coupon.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Avis.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Membre.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Membre.class.getName() + ".commandes", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Commande.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupon.domain.Commande.class.getName() + ".coupons", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
