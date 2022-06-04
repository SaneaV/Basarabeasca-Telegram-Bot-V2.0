package md.basarabeasca.bot.parser;

import org.jsoup.nodes.Document;

public interface TimetableTransportParser {

  String getTimetable();

  Document getDocument();
}
