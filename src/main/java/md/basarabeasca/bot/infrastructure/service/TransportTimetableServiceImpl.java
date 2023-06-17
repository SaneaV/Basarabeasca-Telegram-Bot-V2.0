package md.basarabeasca.bot.infrastructure.service;

import static org.apache.commons.lang3.StringUtils.LF;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.infrastructure.parser.api.TransportTimetableParser;
import md.basarabeasca.bot.infrastructure.service.api.TransportTimetableService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransportTimetableServiceImpl implements TransportTimetableService {

  private static final String TWO_NEW_LINES = "\n\n";
  private static final String ACTUAL_TIME_TABLE = "Актуальное расписание рейсов в и из Басарабяски:";
  private static final String ACTUAL_TIME_TABLE_REPLACE = "Актуальное расписание междугородних рейсов:";
  private static final String TYPO_TIPASPAOL = "Типасполь";
  private static final String TYPO_CHISINAU_PRIMORSKOE = "Кишинев Приморское";
  private static final String TIRASPOL = "Тирасполь";
  private static final String CHISINAU_PRIMORSKOE = "Кишинев — Приморское";

  private final TransportTimetableParser transportTimetableParser;

  @Override
  public String getTimetable() {
    return improveText(transportTimetableParser.getTimetable());
  }

  private String improveText(String text) {
    return text.replaceAll(LF, TWO_NEW_LINES)
        .replace(ACTUAL_TIME_TABLE, ACTUAL_TIME_TABLE_REPLACE)
        .replace(TYPO_TIPASPAOL, TIRASPOL)
        .replace(TYPO_CHISINAU_PRIMORSKOE, CHISINAU_PRIMORSKOE);
  }
}
