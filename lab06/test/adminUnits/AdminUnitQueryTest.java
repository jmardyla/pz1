package adminUnits;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class AdminUnitQueryTest {
    AdminUnitList adminList;
    public AdminUnitQueryTest() {
        adminList = new AdminUnitList();
        adminList.read("admin-units.csv");
    }
    @Test
    public void queryTest1() {
        (new AdminUnitQuery())
                .where(o->(o.getParentName().contains("gmina")))
                .and(o->(o.getArea()<0.25))
                .selectFrom(adminList)
                .sort(Comparator.comparingDouble(AdminUnit::getArea))
                .execute()
                .list(System.out);
        System.out.println();
    }
    @Test
    public void queryTest2() {
        (new AdminUnitQuery())
                .selectFrom(adminList)
                .where(o->(o.getName().endsWith("ówka")))
                .sort((Comparator.comparing(AdminUnit::getName)))
                .limit(25)
                .execute()
                .list(System.out);
        System.out.println();
    }
    @Test
    public void queryTest3() {
        (new AdminUnitQuery())
                .selectFrom(adminList)
                .where(o->(o.getName().startsWith("Ż")))
                .and(o1->o1.getPopulation()<25_000)
                .sort((o1, o2) -> o1.getName().compareTo(o2.getName()) != 0
                        ? o1.getName().compareTo(o2.getName())
                        : Double.compare(o1.getPopulation(), o2.getPopulation()))
                .limit(10)
                .execute()
                .list(System.out);
    }

}