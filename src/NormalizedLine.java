import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NormalizedLine {
    private String timestamp;
    private String address;
    private String zipCode;
    private String fullName;
    private String fooDuration;
    private String barDuration;
    private String totalDuration;
    private String notes;

    public NormalizedLine(String[] columns) {
        int index = 0;
        index = normalizeTimeStamp(columns, index);
        index = getAddress(columns, index);
        
        int remainingColumns = columns.length - index;
        index = columns.length - 1;

        switch (remainingColumns) {
            case 6:
                notes = columns[index--];
            case 5:
                totalDuration = columns[index--];
            case 4:
                barDuration = columns[index--];
            case 3:
                fooDuration = columns[index--];
            case 2:
                fullName = columns[index--].toUpperCase();
            case 1:
                zipCode = normalizeZipCode(columns[index--], 5);
        }
    }

    private int getAddress(String[] columns, int index) {
        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(columns[index++]);
        if (addressBuilder != null
                && addressBuilder.toString().contains("\"")) {
            while (index < columns.length) {
                addressBuilder.append(columns[index]);
                if (columns[index++].contains("\""))
                    break;
            }
        }

        address = addressBuilder.toString();
        return index;
    }

    private int normalizeTimeStamp(String[] columns, int index) {
        timestamp = columns[index++];
        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yy HH:mm:ss aa");
        fmt.setTimeZone(TimeZone.getTimeZone("CST"));
        SimpleDateFormat rfc3339 = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssZ");
        rfc3339.setTimeZone(TimeZone.getTimeZone("US/Eastern"));

        try {
            Date date1 = fmt.parse(timestamp);
            timestamp = rfc3339.format(date1);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return index;
    }

    private static String normalizeZipCode(String zipCode, int maxLength) {
        StringBuilder sb = new StringBuilder(zipCode);
        if (sb.length() <= maxLength) {
            while (sb.length() < maxLength) {
                sb.insert(0, '0');
            }
        }

        return sb.toString();
    }
    
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (timestamp != null) {
            result.append(timestamp);
        }
        if (address != null) {
            result.append(", ").append(address);
        }
        if (zipCode != null) {
            result.append(", ").append(zipCode);
        }
        if (fullName != null) {
            result.append(", ").append(fullName);
        }
        if (fooDuration != null) {
            result.append(", ").append(fooDuration);
        }
        if (barDuration != null) {
            result.append(", ").append(barDuration);
        }
        if (totalDuration != null) {
            result.append(", ").append(totalDuration);
        }
        if (notes != null) {
            result.append(", ").append(notes);
        }

        return result.toString();
    }
}
