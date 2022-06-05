package md.basarabeasca.bot.service.impl;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.LF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class FileReaderService {

  public static String readFromInputStream(InputStream inputStream) throws IOException {
    final StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while (nonNull(line = br.readLine())) {
        resultStringBuilder.append(line).append(LF);
      }
    }
    return resultStringBuilder.toString();
  }
}
