package md.basarabeasca.bot.parser;

import org.jsoup.nodes.Document;

public interface TransportTimetableParser {

  String getTimetable();

  Document getDocument();
}
