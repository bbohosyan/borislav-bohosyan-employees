package logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Record {
    private int id;
    private int projectID;
    private Calendar dateFrom;
    private Calendar dateTo;

    public Record(String id, String projectID, String dateFrom, String dateTo) {
        this.id = Integer.parseInt(id);
        this.projectID = Integer.parseInt(projectID);

        this.dateFrom = getCalendarFormat(dateFrom);

        String[] splittedDateTo = dateTo.split("-");
        if (!dateTo.equals("NULL")) {
            this.dateTo = getCalendarFormat(dateTo);
        } else {
            this.dateTo = Calendar.getInstance();
        }
    }

    public int getId() {
        return this.id;
    }

    public int getProjectID() {
        return this.projectID;
    }

    public Calendar getDateFrom() {
        return this.dateFrom;
    }

    public Calendar getDateTo() {
        return this.dateTo;
    }

    private static Calendar getCalendarFormat(String dateString) {

        ArrayList<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss'Z'"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));

        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                // Take a try
                Date date = pattern.parse(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar;

            } catch (ParseException pe) {
                // Loop on

            }
        }

        return null;
    }
}
