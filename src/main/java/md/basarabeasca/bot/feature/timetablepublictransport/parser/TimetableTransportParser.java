package md.basarabeasca.bot.feature.timetablepublictransport.parser;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface TimetableTransportParser {

    String getTimetable() throws IOException;

    Document getDocument() throws IOException;
}
