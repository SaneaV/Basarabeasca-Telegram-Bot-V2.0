package md.basarabeasca.bot.infrastructure.config;

import static javax.cache.Caching.getCachingProvider;
import static org.ehcache.config.units.EntryUnit.ENTRIES;
import static org.ehcache.config.units.MemoryUnit.MB;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import javax.cache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhcacheConfig {

  private static final String CACHE_PROVIDER = "org.ehcache.jsr107.EhcacheCachingProvider";
  public static final String BAS_TV = "basTV";
  public static final String FEED_BACK = "feedBack";
  public static final String DISTRICT_COUNCIL = "districtCouncil";

  @Value("${cache.onheap.size}")
  private Integer onHeapSize;

  @Value("${cache.offheap.size}")
  private Integer offHeapSize;

  @Value("${cache.expiry.time}")
  private Integer expiryTime;

  @Bean("jCacheCacheManager")
  public JCacheCacheManager jCacheCacheManager(@Qualifier("cacheManager") CacheManager cacheManager) {
    return new JCacheCacheManager(cacheManager);
  }

  @Bean(name = "cacheManager", destroyMethod = "close")
  public CacheManager cacheManager() {
    final EhcacheCachingProvider ehcacheCachingProvider = (EhcacheCachingProvider) getCachingProvider(CACHE_PROVIDER);

    final ResourcePools resourcePools = ResourcePoolsBuilder.newResourcePoolsBuilder()
        .heap(onHeapSize, ENTRIES)
        .offheap(offHeapSize, MB)
        .build();

    final CacheConfiguration<Object, Object> cacheConfiguration = CacheConfigurationBuilder
        .newCacheConfigurationBuilder(Object.class, Object.class, resourcePools)
        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(expiryTime)))
        .build();

    final Map<String, CacheConfiguration<?, ?>> caches = new HashMap<>();
    caches.put(BAS_TV, cacheConfiguration);
    caches.put(FEED_BACK, cacheConfiguration);
    caches.put(DISTRICT_COUNCIL, cacheConfiguration);

    final org.ehcache.config.Configuration configuration = new DefaultConfiguration(caches,
        ehcacheCachingProvider.getDefaultClassLoader());

    return ehcacheCachingProvider.getCacheManager(ehcacheCachingProvider.getDefaultURI(), configuration);
  }
}
