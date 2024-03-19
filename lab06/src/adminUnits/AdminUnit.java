package adminUnits;

import java.util.List;

public class AdminUnit {
    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    List<AdminUnit> children;
    BoundingBox bbox = new BoundingBox();

    public AdminUnit(String name, int adminLevel, double population, double area, double density) {
        this.name = name;
        this.adminLevel = adminLevel;
        this.population = population;
        this.area = area;
        this.density = density;
    }

    public String toString() {
        return name + ", level: " + adminLevel +
                ", population: " + population + ", area: " + area + ", density: " + density
                + ", parent name: " + (parent!=null ? parent.name : "null")
                + ", WKT representation: " + bbox.getWKT();
    }

    public String getName() {
        return name;
    }

    Double fixMissingDensity() {
        if (parent==null) return density;
        if (Double.isNaN(density)) {
            return parent.fixMissingDensity();
        } else {
            return density;
        }
    }

    Double fixMissingPopulation() {
        if (parent==null) return population;
        if (Double.isNaN(population)) {
            return parent.fixMissingPopulation();
        } else {
            return population;
        }
    }

    public String getParentName() {
        if (parent == null) return "";
        return parent.name;
    }

    public double getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }
}