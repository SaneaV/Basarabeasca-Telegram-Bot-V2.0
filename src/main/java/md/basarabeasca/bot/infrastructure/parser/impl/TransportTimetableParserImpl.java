package md.basarabeasca.bot.infrastructure.parser.impl;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import lombok.Getter;
import md.basarabeasca.bot.infrastructure.parser.TransportTimetableParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TransportTimetableParserImpl implements TransportTimetableParser {

  private static final String ENTRY_CONTENT = "div[class=\"entry-content clearfix single-post-content\"]";

  private final String site;

  public TransportTimetableParserImpl(
      @Value("${site.public-transport-timetable.bastv}") String site) {
    this.site = site;
  }

  @Override
  public String getTimetable() {
    return requireNonNull(getDocument().select(ENTRY_CONTENT).first()).wholeText();
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
