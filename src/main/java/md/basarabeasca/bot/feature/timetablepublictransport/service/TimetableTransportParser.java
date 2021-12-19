package md.basarabeasca.bot.feature.timetablepublictransport.service;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface TimetableTransportParser {

    String getTimetable() throws IOException;

    Document getDocument() throws IOException;
}
