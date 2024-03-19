package adminUnits;

import java.util.Comparator;
import java.util.function.Predicate;

public class AdminUnitQuery {
    AdminUnitList src;
    Predicate<AdminUnit> p = a->true;
    Comparator<AdminUnit> cmp = null;
    int limit = Integer.MAX_VALUE;
    int offset = 0;

    /**
     * Ustawia listę jako przetwarzane źródło
     * @param src
     * @return this
     */
    public AdminUnitQuery selectFrom(AdminUnitList src){
        this.src = src;
        return this;
    }

    /**
     *
     * @param pred - ustawia predykat p
     * @return this
     */
    public AdminUnitQuery where(Predicate<AdminUnit> pred){
        this.p = pred;
        return this;
    }

    /**
     * Wykonuje operację p = p and pred
     * @param pred
     * @return this
     */
    public AdminUnitQuery and(Predicate<AdminUnit> pred){
        this.p = p.and(pred);
        return this;
    }
    /**
     * Wykonuje operację p = p or pred
     * @param pred
     * @return this
     */
    public AdminUnitQuery or(Predicate<AdminUnit> pred){
        this.p = p.or(pred);
        return this;
    }

    /**
     * Ustawia komparator cmp
     * @param cmp
     * @return this
     */
    public AdminUnitQuery sort(Comparator<AdminUnit> cmp){
        this.cmp = cmp;
        return this;
    }

    /**
     * Ustawia limit
     * @param limit
     * @return this
     */
    public AdminUnitQuery limit(int limit){
        this.limit = limit;
        return this;
    }
    /**
     * Ustawia offset
     * @param offset
     * @return this
     */
    public AdminUnitQuery offset(int offset){
        this.offset = offset;
        return this;
    }

    /**
     * Wykonuje zapytanie i zwraca wynikową listę
     * @return przefiltrowana i opcjonalnie posortowana lista (uwzględniamy także offset/limit)
     */
    public AdminUnitList execute(){
        if (cmp==null) return src.filter(p, offset, limit);
        return src.filter(p, offset, limit).sortInplace(cmp);
    }
}