package md.basarabeasca.bot.feature.timetablepublictransport.impl;

import lombok.Getter;
import md.basarabeasca.bot.feature.timetablepublictransport.parser.TimetableTransportParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Getter
public class TimetableTransportParserImpl implements TimetableTransportParser {

    private static final String ENTRY_CONTENT = "div[class=\"entry-content clearfix single-post-content\"]";
    private static final String NEW_LINE = "\n";
    private static final String TWO_NEW_LINES = "\n\n";
    private static final String ACTUAL_TIME_TABLE = "Актуальное расписание рейсов в и из Басарабяски:";
    private static final String ACTUAL_TIME_TABLE_REPLACE = "Актуальное расписание междугородних рейсов:";

    private final String siteLink;

    public TimetableTransportParserImpl(@Value("${site.timetable-public-transport.bastv}") String siteLink) {
        this.siteLink = siteLink;
    }

    @Override
    public String getTimetable() throws IOException {
        return improveText(getDocument().select(ENTRY_CONTENT).first().wholeText());
    }

    private String improveText(String text) {
        return text.replaceAll(NEW_LINE, TWO_NEW_LINES)
                .replace(ACTUAL_TIME_TABLE, ACTUAL_TIME_TABLE_REPLACE);
    }

    @Override
    public Document getDocument() throws IOException {
        return Jsoup.connect(siteLink).get();
    }
}
