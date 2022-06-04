package md.basarabeasca.bot.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lombok.Getter;
import md.basarabeasca.bot.service.BankHoursService;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BankHoursServiceImpl implements BankHoursService {

  private static final String bankHoursFile = "src/main/resources/BankHours.txt";

  @Override
  public String getBankHours() {
    try (final BufferedReader br = new BufferedReader(new FileReader(bankHoursFile))) {
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
      }

      return sb.toString();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
