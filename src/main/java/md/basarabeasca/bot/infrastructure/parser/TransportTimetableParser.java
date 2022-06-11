package md.basarabeasca.bot.infrastructure.parser;

import org.jsoup.nodes.Document;

public interface TransportTimetableParser {

  String getTimetable();

  Document getDocument();
}
