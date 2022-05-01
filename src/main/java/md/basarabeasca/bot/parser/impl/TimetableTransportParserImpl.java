package md.basarabeasca.bot.parser.impl;

import static org.apache.commons.lang3.StringUtils.LF;

import java.io.IOException;
import java.util.Objects;
import lombok.Getter;
import md.basarabeasca.bot.parser.TimetableTransportParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TimetableTransportParserImpl implements TimetableTransportParser {

  private static final String ENTRY_CONTENT = "div[class=\"entry-content clearfix single-post-content\"]";
  private static final String TWO_NEW_LINES = "\n\n";
  private static final String ACTUAL_TIME_TABLE = "Актуальное расписание рейсов в и из Басарабяски:";
  private static final String ACTUAL_TIME_TABLE_REPLACE = "Актуальное расписание междугородних рейсов:";
  private static final String TYPO_TIPASPAOL = "Типасполь";
  private static final String TYPO_CHISINAU_PRIMORSKOE = "Кишинев Приморское";
  private static final String TIRASPOL = "Тирасполь";
  private static final String CHISINAU_PRIMORSKOE = "Кишинев — Приморское";

  private final String site;

  public TimetableTransportParserImpl(
      @Value("${site.timetable-public-transport.bastv}") String site) {
    this.site = site;
  }

  @Override
  public String getTimetable() {
    return improveText(
        Objects.requireNonNull(getDocument().select(ENTRY_CONTENT).first()).wholeText());
  }

  private String improveText(String text) {
    return text.replaceAll(LF, TWO_NEW_LINES)
        .replace(ACTUAL_TIME_TABLE, ACTUAL_TIME_TABLE_REPLACE)
        .replace(TYPO_TIPASPAOL, TIRASPOL)
        .replace(TYPO_CHISINAU_PRIMORSKOE, CHISINAU_PRIMORSKOE);
  }

  @Override
  public Document getDocument() {
    try {
      return Jsoup.connect(site).get();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
