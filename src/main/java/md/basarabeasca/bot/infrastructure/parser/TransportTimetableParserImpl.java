package md.basarabeasca.bot.infrastructure.parser;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;
import lombok.Getter;
import md.basarabeasca.bot.infrastructure.parser.api.TransportTimetableParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TransportTimetableParserImpl implements TransportTimetableParser {

  private static final String CONTENT = "div[class=\"elementor-element elementor-element-5fc6b9d elementor-widget "
      + "elementor-widget-theme-post-content\"]";
  private static final String P_ATTR = "p";

  private final String site;

  public TransportTimetableParserImpl(
      @Value("${site.public-transport-timetable.bastv}") String site) {
    this.site = site;
  }

  @Override
  public List<String> getTimetable() {
    return requireNonNull(
        requireNonNull(getDocument().select(CONTENT).first()).child(0).select(P_ATTR)).eachText();
  }

  public Document getDocument() {
    try {
      return Jsoup.connect(site).get();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
