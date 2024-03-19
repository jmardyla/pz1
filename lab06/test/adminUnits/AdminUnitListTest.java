package adminUnits;

import org.junit.Test;

import java.util.Locale;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class AdminUnitListTest {
    AdminUnitList adminList;
    public AdminUnitListTest() {
        adminList = new AdminUnitList();
        adminList.read("admin-units.csv");
    }

    @Test
    public void selectByNameGetNeighboursAndListForCityTest() {
        final int goToTime = 100;

        var adminList = new AdminUnitList();
        adminList.read("admin-units.csv");
        //adminList.list(System.out);

        double t1 = System.nanoTime()/1e6;
        var selected = adminList.selectByName("Augustów", true);
        var neighbours = adminList.getNeighbors(selected.getUnits().get(0), 15);
        neighbours.list(System.out);
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        assertTrue("Unacceptable time of procedure", t2-t1<goToTime);
    }

    @Test
    public void selectByNameGetNeighboursAndListForGminaTest() {
        final int goToTime = 100;

        var adminList = new AdminUnitList();
        adminList.read("admin-units.csv");
        //adminList.list(System.out);

        double t1 = System.nanoTime()/1e6;
        var selected = adminList.selectByName("Kraków", true);
        var neighbours = adminList.getNeighbors(selected.getUnits().get(0), 15);
        neighbours.list(System.out);
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        assertTrue("Unacceptable time of procedure", t2-t1<goToTime);
    }

    @Test
    public void filterTest1() {
        adminList.filter((o)->o.getParentName().equals("województwo małopolskie")).list(System.out);
        System.out.println();

        adminList.filter((o)->o.getName().startsWith("K")).sortInPlaceByName().list(System.out);
        System.out.println();
    }

    @Test
    public void filterTest2() {
        adminList.filter((o)->o.getPopulation()>500_000, 50).sortInPlaceByPopulation().list(System.out);
        System.out.println();

        adminList.filter((o)->o.getArea()>10_000).sortInPlaceByArea().list(System.out);
        System.out.println();
    }

    @Test
    public void filterTest3() {
        adminList.filter(
                new Predicate<AdminUnit>() {
                    @Override
                    public boolean test(AdminUnit adminUnit) {
                        return adminUnit.getName().length() < 30;
                    }
                }
                        .negate().and(o->o.getArea()<10_000)).sortInPlaceByArea().list(System.out);
        System.out.println();
    }
}