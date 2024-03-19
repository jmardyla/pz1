package reader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;
    String[] currentLine;
    List<String> columnLabels = new ArrayList<>();
    Map<String, Integer> columnLabelsToInt = new HashMap<>();


    public CSVReader(Reader reader, String delimiter, boolean hasHeader) {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader) parseHeader();
    }

    /**
     * @param filename  - nazwa pliku
     * @param delimiter - separator pól
     * @param hasHeader - czy plik ma wiersz nagłówkowy
     */

    public CSVReader(String filename, String delimiter, boolean hasHeader) throws IOException {
        this(new FileReader(filename, StandardCharsets.UTF_8), delimiter, hasHeader);
    }
    public CSVReader(String filename, String delimiter) throws IOException {
        this(filename, delimiter, false);
    }

    public CSVReader(String filename) throws IOException {
        this(filename, ",");
    }

    private void parseHeader() {
        // wczytaj wiersz
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            return;
        }
        // podziel na pola
        String[] header = line.split(delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        // przetwarzaj dane w wierszu
        for (int i = 0; i < header.length; i++) {
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i], i);
        }
    }


    public boolean next(){
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            return false;
        }
        if (line==null) return false;
        this.currentLine = line.split(delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        return true;
    }

    public List<String> getColumnLabels() {
        return columnLabels;
    }

    public int getRecordLength() {
        if (currentLine!=null) {
            return currentLine.length;
        } else if (hasHeader) {
            return columnLabels.size();
        } else {
            throw new RuntimeException("Couldn't figure out length of the record");
        }
    }

    public boolean isMissing(int columnIndex) {
        if (currentLine==null) {
            throw new RuntimeException("No line in the buffer");
        }
        if (columnIndex>=getRecordLength() || columnIndex<0) {
            return true;
        }
        return currentLine[columnIndex].equals("");
    }

    public boolean isMissing(String columnLabel) {
        if (!hasHeader) {
            throw new RuntimeException("File does not have labels");
        }
        else if (!columnLabels.contains(columnLabel) || columnLabelsToInt.get(columnLabel)>=currentLine.length) {
            return true;
        }

        return isMissing(columnLabelsToInt.get(columnLabel));
    }

    public String get(int columnIndex) {
        if (currentLine==null) {
            throw new RuntimeException("No line in the buffer");
        }
        if (columnIndex>=getRecordLength() || columnIndex<0) {
            throw new IndexOutOfBoundsException();
        }
        return currentLine[columnIndex];
    }

    public String get(String columnLabel) {
        if (!hasHeader) {
            throw new RuntimeException("File does not have labels");
        }
        return get(columnLabelsToInt.get(columnLabel));
    }

    public int getInt(int columnIndex) {
        if (currentLine==null) {
            throw new RuntimeException("No line in the buffer");
        }
        if (columnIndex>=getRecordLength() || columnIndex<0) {
            throw new IndexOutOfBoundsException();
        }
        if (isMissing(columnIndex)) {
            throw new RuntimeException("Empty field");
        }
        return Integer.parseInt(currentLine[columnIndex]);
    }

    public int getInt(String columnLabel) {
        if (!hasHeader) {
            throw new RuntimeException("File does not have labels");
        }

        return getInt(columnLabelsToInt.get(columnLabel));
    }

    public long getLong(int columnIndex) {
        if (currentLine==null) {
            throw new RuntimeException("No line in the buffer");
        }
        if (columnIndex>=getRecordLength() || columnIndex<0) {
            throw new IndexOutOfBoundsException();
        }
        if (isMissing(columnIndex)) {
            throw new RuntimeException("Empty field");
        }
        return Long.parseLong(currentLine[columnIndex]);
    }

    public long getLong(String columnLabel) {
        if (!hasHeader) {
            throw new RuntimeException("File does not have labels");
        }
        return getLong(columnLabelsToInt.get(columnLabel));
    }

    public double getDouble(int columnIndex) {
        if (currentLine==null) {
            throw new RuntimeException("No line in the buffer");
        }
        if (columnIndex>=getRecordLength() || columnIndex<0) {
            throw new IndexOutOfBoundsException();
        }
        if (isMissing(columnIndex)) {
            throw new RuntimeException("Empty field");
        }
        return Double.parseDouble(currentLine[columnIndex]);
    }

    public double getDouble(String columnLabel) {
        if (!hasHeader) {
            throw new RuntimeException("File does not have labels");
        }
        return getDouble(columnLabelsToInt.get(columnLabel));
    }
}