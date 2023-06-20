package md.basarabeasca.bot.infrastructure.service;

import static md.basarabeasca.bot.infrastructure.config.EhcacheConfig.BANK_HOURS;
import static md.basarabeasca.bot.infrastructure.config.EhcacheConfig.J_CACHE_CACHE_MANAGER;
import static md.basarabeasca.bot.infrastructure.util.InputStreamReader.readFromInputStream;

import java.io.IOException;
import java.io.InputStream;
import md.basarabeasca.bot.infrastructure.service.api.BankHoursService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BankHoursServiceImpl implements BankHoursService {

  private static final String bankHoursFile = "BankHours.txt";

  @Override
  @Cacheable(value = BANK_HOURS, cacheManager = J_CACHE_CACHE_MANAGER)
  public String getBankHours() {
    final ClassLoader classLoader = getClass().getClassLoader();
    final InputStream inputStream = classLoader.getResourceAsStream(bankHoursFile);
    try {
      return readFromInputStream(inputStream);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
