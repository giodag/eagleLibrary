package com.univaq.eaglelibraryapp.config;

import java.time.Duration;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;

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
            cm.createCache(com.univaq.eaglelibraryapp.domain.Profile.class.getName(), jcacheConfiguration);
            cm.createCache(com.univaq.eaglelibraryapp.domain.Literary_work.class.getName(), jcacheConfiguration);
            cm.createCache(com.univaq.eaglelibraryapp.domain.Page.class.getName(), jcacheConfiguration);
            cm.createCache(com.univaq.eaglelibraryapp.domain.Transcript.class.getName(), jcacheConfiguration);
            cm.createCache(com.univaq.eaglelibraryapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.univaq.eaglelibraryapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.univaq.eaglelibraryapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.univaq.eaglelibraryapp.domain.Transcript.class.getName() + ".users", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
