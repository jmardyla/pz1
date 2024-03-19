package adminUnits;

import java.util.Comparator;

public class AdminUnitNameComparator implements Comparator<AdminUnit> {

    @Override
    public int compare(AdminUnit o1, AdminUnit o2) {
        return o1.name.compareTo(o2.name);
    }
}
