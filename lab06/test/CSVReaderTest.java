import org.junit.Test;
import reader.CSVReader;

import java.io.StringReader;
import java.util.List;

public class CSVReaderTest {

    @Test
    public void readingFileWithoutIssuesWithHeaderTest() {
        String fileContent = """
                id;imię;nazwisko;ulica;nrdomu;nrmieszkania
                1;Jan;Kowal;Floriańska;2;4
                2;Anna;Kowalska;Grodzka;23;3
                3;Zofia;Kowalewska;Sienna;8;2
                4;Marek;Jabłoński;św. Marka;6;6
                5;Katarzyna;Jabłonowska;pl. Matejki;10;7
                6;Zuznanna;Jabczyńska;Szewska;4;9
                7;Sebastian;Nowak;św. Anny;8;1
                8;Julia;Nowakowska;Bracka;2;2
                9;Antoni;Nowaczyński;Jagiellońska;3;3
                10;Dominik;Nowacki;Mikołajska;4;4
                """;
        CSVReader reader = new CSVReader(new StringReader(fileContent), ";", true);
        System.out.println(reader.getColumnLabels());
        while (reader.next()) {
            for (int i = 0; i < reader.getRecordLength(); i++) {
                System.out.print(reader.get(i) + " | ");
            }
            System.out.println();
        }
    }
    @Test
    public void readingFileWithoutIssuesWithOutHeaderTest() {
        String fileContent = """
                1;Jan;Kowal;Floriańska;2;4
                2;Anna;Kowalska;Grodzka;23;3
                3;Zofia;Kowalewska;Sienna;8;2
                4;Marek;Jabłoński;św. Marka;6;6
                5;Katarzyna;Jabłonowska;pl. Matejki;10;7
                6;Zuznanna;Jabczyńska;Szewska;4;9
                7;Sebastian;Nowak;św. Anny;8;1
                8;Julia;Nowakowska;Bracka;2;2
                9;Antoni;Nowaczyński;Jagiellońska;3;3
                10;Dominik;Nowacki;Mikołajska;4;4
                """;
        CSVReader reader = new CSVReader(new StringReader(fileContent), ";", true);

        while (reader.next()) {
            for (int i = 0; i < reader.getRecordLength(); i++) {
                System.out.print(reader.get(i) + " | ");
            }
            System.out.println();
        }
    }

    @Test
    public void getFromLabelNamesTest() {
        String fileContent = """
                id;imię;nazwisko;ulica;nrdomu;nrmieszkania
                1;Jan;Kowal;Floriańska;2;4
                2;Anna;Kowalska;Grodzka;23;3
                3;Zofia;Kowalewska;Sienna;8;2
                4;Marek;Jabłoński;św. Marka;6;6
                5;Katarzyna;Jabłonowska;pl. Matejki;10;7
                6;Zuznanna;Jabczyńska;Szewska;4;9
                7;Sebastian;Nowak;św. Anny;8;1
                8;Julia;Nowakowska;Bracka;2;2
                9;Antoni;Nowaczyński;Jagiellońska;3;3
                10;Dominik;Nowacki;Mikołajska;4;4
                """;
        CSVReader reader = new CSVReader(new StringReader(fileContent), ";", true);
        List<String> labelNames = reader.getColumnLabels();
        while (reader.next()) {
            for (int i = 0; i < reader.getRecordLength(); i++) {
                System.out.print(reader.get(labelNames.get(i)) + " | ");
            }
            System.out.println();
        }
    }

    @Test
    public void getDoubleTest() {
        String fileContent = """
                X;Y;Z;longitude;latitude;speed;time;label
                -0.048841707;-2.5234885;9.510963;50.0880848;20.0079909;0;518845052664;0
                -0.12545615;-2.552219;9.57688;50.0880848;20.0079909;0;518851626779;0
                -0.19249381;-2.542642;9.548631;50.0880848;20.0079909;0;518861515997;0
                -0.14460978;-2.4851813;9.57688;50.0880848;20.0079909;0;518871599956;0
                -0.106302544;-2.4947581;9.614548;50.0880848;20.0079909;0;518881664852;0
                -0.11587936;-2.5234885;9.529797;50.0880848;20.0079909;0;518891758914;0
                -0.20207062;-2.5139117;9.57688;50.0880848;20.0079909;0;518901689904;0
                -0.22122423;-2.504335;9.661632;50.0880848;20.0079909;0;518911563393;0
                -0.039264902;-2.552219;9.482713;50.0880848;20.0079909;0;518921638914;0
                -0.058418512;-2.60968;9.501546;50.0880848;20.0079909;0;518931885477;0
                -0.2882619;-2.5809495;9.586297;50.0880848;20.0079909;0;518941606570;0
                -0.2978387;-2.5713725;9.482713;50.0880848;20.0079909;0;518952110737;0
                -0.15418658;-2.6288335;9.529797;50.0880848;20.0079909;0;518961656675;0
                -0.087148935;-2.6192567;9.52038;50.0880848;20.0079909;0;518971655737;0
                -0.20207062;-2.7150247;9.341461;50.0880848;20.0079909;0;518981684904;0
                -0.31699228;-2.743755;9.473296;50.0880848;20.0079909;0;518991648289;0
                -0.21164742;-2.6671407;9.595715;50.0880848;20.0079909;0;519001599695;0
                -0.087148935;-2.7629087;9.303794;50.0880848;20.0079909;0;519011559279;0
                """;
        CSVReader reader = new CSVReader(new StringReader(fileContent), ";", true);

        while (reader.next()) {
            for (int i = 0; i < reader.getRecordLength(); i++) {
                System.out.print(reader.getDouble(i) + " | ");

            }
            System.out.println();
        }
    }

    @Test
    public void getLongFromLabelNamesTest() {
        String fileContent = """
                X;Y;Z;longitude;latitude;speed;time;label
                -0.048841707;-2.5234885;9.510963;50.0880848;20.0079909;0;518845052664;0
                -0.12545615;-2.552219;9.57688;50.0880848;20.0079909;0;518851626779;0
                -0.19249381;-2.542642;9.548631;50.0880848;20.0079909;0;518861515997;0
                -0.14460978;-2.4851813;9.57688;50.0880848;20.0079909;0;518871599956;0
                -0.106302544;-2.4947581;9.614548;50.0880848;20.0079909;0;518881664852;0
                -0.11587936;-2.5234885;9.529797;50.0880848;20.0079909;0;518891758914;0
                -0.20207062;-2.5139117;9.57688;50.0880848;20.0079909;0;518901689904;0
                -0.22122423;-2.504335;9.661632;50.0880848;20.0079909;0;518911563393;0
                -0.039264902;-2.552219;9.482713;50.0880848;20.0079909;0;518921638914;0
                -0.058418512;-2.60968;9.501546;50.0880848;20.0079909;0;518931885477;0
                -0.2882619;-2.5809495;9.586297;50.0880848;20.0079909;0;518941606570;0
                -0.2978387;-2.5713725;9.482713;50.0880848;20.0079909;0;518952110737;0
                -0.15418658;-2.6288335;9.529797;50.0880848;20.0079909;0;518961656675;0
                -0.087148935;-2.6192567;9.52038;50.0880848;20.0079909;0;518971655737;0
                -0.20207062;-2.7150247;9.341461;50.0880848;20.0079909;0;518981684904;0
                -0.31699228;-2.743755;9.473296;50.0880848;20.0079909;0;518991648289;0
                -0.21164742;-2.6671407;9.595715;50.0880848;20.0079909;0;519001599695;0
                -0.087148935;-2.7629087;9.303794;50.0880848;20.0079909;0;519011559279;0
                """;
        CSVReader reader = new CSVReader(new StringReader(fileContent), ";", true);
        var columnNames = reader.getColumnLabels();
        while (reader.next()) {
            System.out.println(reader.getLong(columnNames.get(6)));
        }
    }
}