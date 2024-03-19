package adminUnits;


import java.util.Locale;

public class BoundingBox {
    double xmin = Double.NaN;
    double ymin = Double.NaN;
    double xmax = Double.NaN;
    double ymax = Double.NaN;


    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     * Jeżeli był wcześniej pusty - wówczas ma zawierać wyłącznie ten punkt
     * @param x - współrzędna x
     * @param y - współrzędna y
     */
    void addPoint(double x, double y){
        if (this.contains(x, y)) return;

        if (xmin==xmax && ymin==ymax) {
            if (x<=xmax) {
                if (y<=ymax) {
                    xmin=x; ymin=y;
                }
                else {
                    xmin=x; ymax=y;
                }
            }
            else {
                if (y>=ymax) {
                    ymax=y; xmax=x;
                }
                else {
                    xmax=x; ymin=y;
                }
            }
        }
        else if (this.isEmpty()) {
            xmin=x; xmax=x; ymin=y; ymax=y;
        }
        else {
            xmax=Math.max(xmax, x);
            ymax=Math.max(ymax, y);
            xmin=Math.min(xmin, x);
            ymin=Math.min(ymin, y);
        }
    }

    /**
     * Sprawdza, czy BB zawiera punkt (x,y)
     * @param x
     * @param y
     * @return
     */
    boolean contains(double x, double y){
        return (x<=xmax && y<=ymax && x>=xmin && y>=ymin);
    }

    /**
     * Sprawdza czy dany BB zawiera bb
     * @param bb
     * @return
     */
    boolean contains(BoundingBox bb){
        return (bb.xmin>=xmin && bb.ymin>=ymin && bb.xmax<=xmax && bb.ymax<=ymax);
    }

    /**
     * Sprawdza, czy dany BB przecina się z bb
     * @param bb
     * @return
     */
    boolean intersects(BoundingBox bb){
        return (bb.xmax>=xmin && bb.ymax>=ymin && bb.xmin<=xmax && bb.ymin<=ymax);
    }
    /**
     * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
     * Jeżeli był pusty - po wykonaniu operacji ma być równy bb
     * @param bb
     * @return
     */
    BoundingBox add(BoundingBox bb){
        this.addPoint(bb.xmax, bb.ymax);
        this.addPoint(bb.xmin, bb.ymin);
        return this;
    }
    /**
     * Sprawdza czy BB jest pusty
     * @return
     */
    boolean isEmpty(){
        return (Double.isNaN(xmin) && Double.isNaN(ymin) && Double.isNaN(xmax) && Double.isNaN(ymax));
    }

    /**
     * Sprawdza czy
     * 1) typem o jest BoundingBox
     * 2) this jest równy bb
     * @return
     */
    public boolean equals(Object o){
        if (!o.getClass().equals(BoundingBox.class)) return false;

        BoundingBox other = (BoundingBox) o;
        return (other.xmin==xmin && other.ymin==ymin && other.xmax==xmax && other.ymax==ymax);
    }

    /**
     * Oblicza i zwraca współrzędną x środka
     * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterX(){
        if (isEmpty()) throw new IllegalStateException("Bounding box is empty");
        return (ymin+ymax)/2;
    }
    /**
     * Oblicza i zwraca współrzędną y środka
     * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterY(){
        if (isEmpty()) throw new IllegalStateException("Bounding box is empty");
        return (xmin+xmax)/2;
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
     * @param bbx prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, zamiast odległości użyj wzoru haversine
     * (ang. haversine formula)
     *
     * Gotowy kod można znaleźć w Internecie...
     */
    double distanceTo(BoundingBox bbx){
        return haversine(getCenterX(), getCenterY(), bbx.getCenterX(), bbx.getCenterY());
    }

    static double haversine(double lat1, double lon1,
                            double lat2, double lon2)
    {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    public String getWKT() {
        return String.format(Locale.US,"LINESTRING(%f %f, %f %f, %f %f, %f %f, %f %f)", xmin, ymin, xmax, ymin, xmax, ymax, xmin, ymax, xmin, ymin);
    }

    public String toString() {
        return String.format("(%f, %f), (%f, %f)", xmin, ymin, xmax, ymax);
    }

}