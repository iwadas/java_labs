package reader;

import java.io.StringReader;

public class TestCSVReader {
    public static void main(String[] args) {
        // Dane testowe
        String text = "a,b,c\n123.4,567.8,91011.12";
        String emptyText = "a,b,c\n,,\n123,,456";


        // Test 1: Odczyt sekwencji jako String i wyświetlenie
        System.out.println("=== Test 1: Odczyt sekwencji ===");
        CSVReader reader = new CSVReader(new StringReader(text), ",", true);
        while (reader.next()) {
            for (int i = 0; i < reader.getRecordLength(); i++) {
                System.out.print(reader.get(i) + " ");
            }
            System.out.println();
        }

        // Test 2: Odczyt wartości różnych typów
        System.out.println("\n=== Test 2: Odczyt wartości poszczególnych typów ===");
        reader = new CSVReader(new StringReader(text), ",", true);
        while (reader.next()) {
            System.out.println("Kolumna 0 (double): " + reader.getDouble(0));
            System.out.println("Kolumna 1 (double): " + reader.getDouble(1));
            try {
                System.out.println("Kolumna 2 (long): " + reader.getLong(2));
            } catch (NumberFormatException e) {
                System.out.println("Kolumna 2 nie jest liczbą całkowitą: " + e.getMessage());
            }

        }

        System.out.println("\n=== Test 3: Odczyt brakujących wartości ===");
        CSVReader missingReader = new CSVReader(new StringReader(emptyText), ",", true);

        if (!missingReader.next()) {
            System.out.println("Brak danych w pliku.");
        } else {
            do {
                for (int i = 0; i < missingReader.getRecordLength(); i++) {
                    if (missingReader.isMissing(i)) {
                        System.out.println("Brakująca wartość w kolumnie: " + i);
                    } else {
                        System.out.println("Wartość: " + missingReader.get(i));
                    }
                }
            } while (missingReader.next());
        }


        // Test 4: Odwołania do nieistniejących kolumn
        System.out.println("\n=== Test 4: Odwołania do nieistniejących kolumn ===");
        try {
            reader = new CSVReader(new StringReader(text), ",", true);
            reader.next();
            System.out.println("Nieistniejąca kolumna (index): " + reader.get(10)); // Kolumna index 10
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }

        try {
            System.out.println("Nieistniejąca kolumna (nazwa): " + reader.get("z")); // Kolumna "z"
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }

        // Test 5: Odczyt z innych źródeł
        System.out.println("\n=== Test 5: Odczyt z innych źródeł ===");
        String customText = "x;y;z\n1;2;3\n4;5;6";
        CSVReader customReader = new CSVReader(new StringReader(customText), ";", true);
        while (customReader.next()) {
            for (int i = 0; i < customReader.getRecordLength(); i++) {
                System.out.print(customReader.get(i) + " ");
            }
            System.out.println();
        }
    }
}
