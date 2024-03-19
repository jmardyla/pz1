package adminUnits;

import reader.CSVReader;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();

    /**
     * Czyta rekordy pliku i dodaje do listy
     * @param filename nazwa pliku
     */

    public void read(String filename) {
        Map<Long, AdminUnit> longToUnit = new HashMap<>();
        Map<AdminUnit, Long> unitToParentLong = new HashMap<>();
        Map<Long, List<AdminUnit>> longToChildrenUnits = new HashMap<>();
        Map<AdminUnit, Long> unitToID = new HashMap<>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(filename, ",", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (reader.next()) {
            var population = !reader.isMissing("population") ? reader.getDouble("population") : Double.NaN;
            var density = !reader.isMissing("density") ? reader.getDouble("density") : Double.NaN;
            var area = !reader.isMissing("area") ? reader.getDouble("area") : Double.NaN;
            var adminLevel = !reader.isMissing("admin_level") ? reader.getInt("admin_level") : 11;
            var unit = new AdminUnit(reader.get("name"),
                    adminLevel,
                    population,
                    area,
                    density
            );
            if (!reader.isMissing(7)) {
                unit.bbox.addPoint(reader.getDouble(7), reader.getDouble(8));
                unit.bbox.addPoint(reader.getDouble(9), reader.getDouble(10));
                unit.bbox.addPoint(reader.getDouble(11), reader.getDouble(12));
                unit.bbox.addPoint(reader.getDouble(13), reader.getDouble(14));
                unit.bbox.addPoint(reader.getDouble(15), reader.getDouble(16));
            }
            unitToID.put(unit, reader.getLong(0));
            longToUnit.put(reader.getLong(0), unit);
            unitToParentLong.put(unit, !reader.isMissing(1) ? reader.getLong(1) : null);
            units.add(unit);

            if (!reader.isMissing(1)) {
                long parentID = reader.getLong(1);
                if (longToChildrenUnits.containsKey(parentID)) {
                    longToChildrenUnits.get(parentID).add(unit);
                } else {
                    longToChildrenUnits.put(parentID, new ArrayList<>(List.of(unit)));
                }
            }
        }
        for (var unit :
            units) {
            unit.parent = unitToParentLong.get(unit)!=null ? longToUnit.get(unitToParentLong.get(unit)) : null;
            unit.children = longToChildrenUnits.getOrDefault(unitToID.get(unit), null);
        }

        for (var unit :
                units) {
            fixMissingValues(unit);
        }
    }

    /**
     * Wypisuje co najwyżej limit elementów począwszy od elementu o indeksie offset
     * @param out - strumień wyjsciowy
     * @param offset - od którego elementu rozpocząć wypisywanie
     * @param limit - ile (maksymalnie) elementów wypisać
     */
    public void list(PrintStream out, int offset, int limit ){
        if (offset<0 || offset>=units.size()) {
            throw new IllegalArgumentException("offset has to be in the range of units list's size");
        }
        for(int i=offset; i<offset+limit || i<units.size(); i++) {
            out.println(units.get(i).toString());
        }
    }
    /**
     * Wypisuje zawartość korzystając z AdminUnit.toString()
     * @param out
     */
    public void list(PrintStream out){
        this.list(out, 0, units.size()-1);
    }

    /**
     * Zwraca nową listę zawierającą te obiekty AdminUnit, których nazwa pasuje do wzorca
     * @param pattern - wzorzec dla nazwy
     * @param regex - jeśli regex=true, użyj finkcji String matches(); jeśli false użyj funkcji contains()
     * @return podzbiór elementów, których nazwy spełniają kryterium wyboru
     */
    public AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        // przeiteruj po zawartości units
        // jeżeli nazwa jednostki pasuje do wzorca dodaj do ret
        if (regex) {
            for (var unit :
                    units) {
                if (unit.getName().matches(pattern)) {
                    ret.units.add(unit);
                }
            }
        }
        else {
            for (var unit :
                    units) {
                if (unit.getName().contains(pattern)) {
                    ret.units.add(unit);
                }
            }
        }
        return ret;
    }

    private void fixMissingValues(AdminUnit au) {
        for (var unit :
                units) {
            au.density = au.fixMissingDensity();
            au.population = au.fixMissingPopulation();
        }
    }

    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * Czyli sąsiadami wojweództw są województwa, powiatów - powiaty, gmin - gminy, miejscowości - inne miejscowości
     * @param unit - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxdistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    public AdminUnitList getNeighbors(AdminUnit unit, double maxdistance){
        AdminUnitList neighbors = new AdminUnitList();
        int chosenAdminLevel = unit.adminLevel;
        for (var adminUnit :
                units) {
            if (chosenAdminLevel>=8 && adminUnit.adminLevel==chosenAdminLevel && adminUnit.bbox.distanceTo(unit.bbox)<maxdistance) {
                neighbors.units.add(adminUnit);
            }
            else if (!adminUnit.equals(unit) && adminUnit.adminLevel==chosenAdminLevel && adminUnit.bbox.intersects(unit.bbox)) {
                neighbors.units.add(adminUnit);
            }
        }
        return neighbors;
    }

    public List<AdminUnit> getUnits() {
        return units;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    public AdminUnitList sortInPlaceByName(){
        units.sort(new AdminUnitNameComparator());
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    public AdminUnitList sortInPlaceByArea() {
        units.sort(new Comparator<AdminUnit>() {
            @Override
            public int compare(AdminUnit o1, AdminUnit o2) {
                return Double.compare(o1.area, o2.area);
            }
        });
        return this;
    }
    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    public AdminUnitList sortInPlaceByPopulation() {
        units.sort((o1,o2)->Double.compare(o1.population, o2.population));
        return this;
    }

    public AdminUnitList sortInplace(Comparator<AdminUnit> cmp){
        units.sort(cmp);
        return this;
    }

    public AdminUnitList sort(Comparator<AdminUnit> cmp){
        AdminUnitList copy = new AdminUnitList();
        copy.units.addAll(units);
        return copy.sortInplace(cmp);
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred
     * @param pred - predykat
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    public AdminUnitList filter(Predicate<AdminUnit> pred, int limit){
        AdminUnitList resultList = new AdminUnitList();
        for(int i=0, count=0; count<limit && i<units.size(); i++) {
            if (pred.test(units.get(i))) {
                resultList.units.add(units.get(i));
                count++;
            }
        }
        return resultList;
    }

    /**
     *
     * @param pred referencja do interfejsu Predicate
     * @return nową listę, na której pozostawiono tylko te jednostki,
     * dla których metoda test() zwraca true
     */
    public AdminUnitList filter(Predicate<AdminUnit> pred){
        return filter(pred, units.size());
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred począwszy od offset
     * Offest jest obliczany po przefiltrowaniu
     * @param pred - predykat
     * @param offset od którego elementu
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    public AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit){
        AdminUnitList filtered = filter(pred);
        if (offset>=filtered.units.size() || offset<0) {
            throw new IndexOutOfBoundsException("Offset is either too high or less than 0");
        }
        filtered.units = filtered.units.subList(offset, (Math.min(offset + limit, filtered.units.size())));
        return filtered;
    }

}