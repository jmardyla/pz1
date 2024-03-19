import adminUnits.AdminUnit;
import adminUnits.AdminUnitList;
import adminUnits.AdminUnitQuery;
import reader.CSVReader;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {


        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        CSVReader reader = null;
        try {
            reader = new CSVReader("titanic.csv", ",", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(reader.getColumnLabels());
        for(int j=0; j<15256; j++){
            reader.next();
            for(int i=0; i<reader.getRecordLength(); i++) {
                System.out.print(reader.get(i) + " | ");

            }
            System.out.println();
        }

//        var adminList = new AdminUnitList();
//
//        adminList.read("admin-units.csv");
        //adminList.list(System.out);

//        var wojeList = adminList.selectByName("województwo", false);
//        wojeList.list(System.out);
//        double t1 = System.nanoTime()/1e6;
//        var selected = adminList.selectByName("Augustów", true);
//        var neighbours = adminList.getNeighbors(selected.getUnits().get(0), 15);
//        neighbours.list(System.out);
//        double t2 = System.nanoTime()/1e6;
//        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        //selected.list(System.out);

//        adminList.filter((o)->o.getName().startsWith("K")).sortInPlaceByName().list(System.out);
//        System.out.println();
//        adminList.filter((o)->o.getParentName().equals("województwo małopolskie")).list(System.out);
//        System.out.println();
//        adminList.filter((o)->o.getPopulation()>500_000, 50).sortInPlaceByPopulation().list(System.out);
//        System.out.println();
//        adminList.filter((o)->o.getArea()>10_000).sortInPlaceByArea().list(System.out);
//        System.out.println();
//        adminList.filter(
//            new Predicate<AdminUnit>() {
//                @Override
//                public boolean test(AdminUnit adminUnit) {
//                    return adminUnit.getName().length() < 30;
//                }
//            }
//            .negate().and(o->o.getArea()<10_000)).sortInPlaceByArea().list(System.out);
//        System.out.println();
//
//        (new AdminUnitQuery())
//                .where(o->(o.getParentName().contains("gmina")))
//                .and(o->(o.getArea()<0.25))
//                .selectFrom(adminList)
//                .sort(Comparator.comparingDouble(AdminUnit::getArea))
//                .execute()
//                .list(System.out);
//        System.out.println();
//
//        (new AdminUnitQuery())
//                .selectFrom(adminList)
//                .where(o->(o.getName().endsWith("ówka")))
//                .sort((Comparator.comparing(AdminUnit::getName)))
//                .limit(25)
//                .execute()
//                .list(System.out);
//        System.out.println();
//
//        (new AdminUnitQuery())
//                .selectFrom(adminList)
//                .where(o->(o.getName().startsWith("Ż")))
//                .and(o1->o1.getPopulation()<25_000)
//                .sort((o1, o2) -> o1.getName().compareTo(o2.getName()) != 0
//                        ? o1.getName().compareTo(o2.getName())
//                        : Double.compare(o1.getPopulation(), o2.getPopulation()))
//                .limit(10)
//                .execute()
//                .list(System.out);
//    }

}}
