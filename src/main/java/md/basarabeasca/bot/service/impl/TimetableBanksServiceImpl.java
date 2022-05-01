package md.basarabeasca.bot.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lombok.AllArgsConstructor;
import md.basarabeasca.bot.service.TimetableBanksService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TimetableBanksServiceImpl implements TimetableBanksService {

  private static final String timeTableFile = "src/main/resources/BanksTimetable.txt";

  @Override
  public String getTimetable() {
    try (final BufferedReader br = new BufferedReader(new FileReader(timeTableFile))) {
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
