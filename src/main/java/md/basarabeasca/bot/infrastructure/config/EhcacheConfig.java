package md.basarabeasca.bot.infrastructure.config;

import static java.util.Arrays.asList;
import static javax.cache.Caching.getCachingProvider;
import static org.ehcache.config.units.EntryUnit.ENTRIES;
import static org.ehcache.config.units.MemoryUnit.MB;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
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
  public static final String J_CACHE_CACHE_MANAGER = "jCacheCacheManager";

  public static final String BAS_TV = "basTV";
  public static final String FEED_BACK = "feedBack";
  public static final String DISTRICT_COUNCIL = "districtCouncil";
  public static final String WEATHER = "weather";
  public static final String PUBLIC_TRANSPORT_TIMETABLE = "publicTransportTimetable";
  public static final String BANK_HOURS = "bankHours";
  public static final String EXCHANGE_RATE_BNM = "exchangeRateBnm";
  public static final String ALL_EXCHANGE_RATE = "allExchangeRate";
  public static final String LOCATION = "location";
  public static final String ANRE_FUEL_PRICE = "anreFuelPrice";

  private static final List<String> SHORT_CACHES = asList(BAS_TV, FEED_BACK, DISTRICT_COUNCIL, WEATHER, EXCHANGE_RATE_BNM,
      ALL_EXCHANGE_RATE, ANRE_FUEL_PRICE);
  private static final List<String> LONG_CACHES = asList(PUBLIC_TRANSPORT_TIMETABLE, BANK_HOURS, LOCATION);

  @Value("${cache.onheap.size}")
  private Integer onHeapSize;

  @Value("${cache.offheap.size}")
  private Integer offHeapSize;

  @Value("${cache.short-expiry.time}")
  private Integer shortExpiryTime;

  @Value("${cache.long-expiry.time}")
  private Integer longExpiryTime;

  @Bean(J_CACHE_CACHE_MANAGER)
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

    final CacheConfiguration<Object, Object> shortCacheConfiguration = getCacheConfiguration(resourcePools, shortExpiryTime);
    final CacheConfiguration<Object, Object> longCacheConfiguration = getCacheConfiguration(resourcePools, longExpiryTime);

    final Map<String, CacheConfiguration<?, ?>> caches = new HashMap<>();
    SHORT_CACHES.forEach(cache -> caches.put(cache, shortCacheConfiguration));
    LONG_CACHES.forEach(cache -> caches.put(cache, longCacheConfiguration));

    final org.ehcache.config.Configuration configuration = new DefaultConfiguration(caches,
        ehcacheCachingProvider.getDefaultClassLoader());

    return ehcacheCachingProvider.getCacheManager(ehcacheCachingProvider.getDefaultURI(), configuration);
  }

  private CacheConfiguration<Object, Object> getCacheConfiguration(ResourcePools resourcePools, Integer cacheDuration) {
    return CacheConfigurationBuilder
        .newCacheConfigurationBuilder(Object.class, Object.class, resourcePools)
        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(cacheDuration)))
        .build();
  }
}
