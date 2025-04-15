package reader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {

    private BufferedReader reader;
    private String delimiter;
    private boolean hasHeader;
    public List<String> columnLabels = new ArrayList<>();
    private Map<String, Integer> columnLabelsToInt = new HashMap<>();
    public String[] current;

    /**
     * Konstruktor CSVReader.
     *
     * @param filename   - nazwa pliku
     * @param delimiter  - separator pól
     * @param hasHeader  - czy plik ma wiersz nagłówkowy
     */
    public CSVReader(String filename, String delimiter, boolean hasHeader) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader) parseHeader();
    }

    public CSVReader(String filename, String delimiter) throws FileNotFoundException {
        this(filename, delimiter, true);
    }

    public CSVReader(String filename) throws FileNotFoundException {
        this(filename, ",", true);
    }

    public CSVReader(Reader reader, String delimiter, boolean hasHeader) {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader) parseHeader();
    }

    private void parseHeader() {
        try {
            String line = reader.readLine();
            if (line == null) return;
            String[] header = line.split(delimiter);
            for (int i = 0; i < header.length; i++) {
                columnLabels.add(header[i]);
                columnLabelsToInt.put(header[i], i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean next() {
        try {
            String line = reader.readLine();
            if (line == null) return false;
            current = line.split(delimiter);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String get(int columnIndex) {
        if (isMissing(columnIndex)) return "";
        return current[columnIndex];
    }

    public String get(String columnName) {
        Integer index = columnLabelsToInt.get(columnName);
        if (index == null) throw new IllegalArgumentException("Column not found: " + columnName);
        return get(index);
    }

    public int getInt(int columnIndex) {
        if (isMissing(columnIndex)) return 0;
        return Integer.parseInt(get(columnIndex));
    }

    public int getInt(String columnName) {
        return getInt(columnLabelsToInt.get(columnName));
    }

    public double getDouble(int columnIndex) {
        if (isMissing(columnIndex)) return Double.NaN;
        return Double.parseDouble(get(columnIndex));
    }

    public double getDouble(String columnName) {
        return getDouble(columnLabelsToInt.get(columnName));
    }

    public long getLong(int columnIndex) {
        String value = get(columnIndex);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            double doubleValue = Double.parseDouble(value);
            return (long) doubleValue;
        }
    }


    public long getLong(String columnName) {
        return getLong(columnLabelsToInt.get(columnName));
    }

    public boolean isMissing(int columnIndex) {
        return columnIndex >= current.length || current[columnIndex].isEmpty();
    }

    public boolean isMissing(String columnName) {
        Integer index = columnLabelsToInt.get(columnName);
        if (index == null) throw new IllegalArgumentException("Column not found: " + columnName);
        return isMissing(index);
    }

    public int getRecordLength() {
        return current != null ? current.length : 0;
    }
}