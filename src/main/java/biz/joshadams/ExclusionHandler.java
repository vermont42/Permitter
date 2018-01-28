package biz.joshadams;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.time.LocalDate;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class ExclusionHandler extends DefaultHandler {
    private List<LocalDate> holidays = new ArrayList<>();
    private List<Vacation> vacations = new ArrayList<>();

    public List<LocalDate> getHolidays() {
        return holidays;
    }

    public List<Vacation> getVacations() {
        return vacations;
    }

    public void parse() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(new File("exclude.xml"), this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Logger.log("Parsing of exclude.xml failed.");
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("holiday")) {
            String holidayString = attributes.getValue("date");
            LocalDate holiday;
            try {
                holiday = LocalDate.parse(holidayString, ISO_LOCAL_DATE);
                holidays.add(holiday);
            }
            catch (DateTimeParseException exc) {
                Logger.log("Unable to parse holiday " + holidayString + ".");
                System.exit(-1);
            }
        } else if (qName.equalsIgnoreCase("vacation")) {
            String startString = attributes.getValue("start");
            LocalDate start = null;
            try {
                start = LocalDate.parse(startString, ISO_LOCAL_DATE);
            }
            catch (DateTimeParseException exc) {
                Logger.log("Unable to parse vacation start date " + startString + ".");
                System.exit(-1);
            }
            String endString = attributes.getValue("end");
            LocalDate end = null;
            try {
                end = LocalDate.parse(endString, ISO_LOCAL_DATE);
            }
            catch (DateTimeParseException exc) {
                Logger.log("Unable to parse vacation end date " + endString + ".");
                System.exit(-1);
            }
            vacations.add(new Vacation(start, end));
        }
    }
}