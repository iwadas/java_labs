package reader;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.Float.NaN;

public class AdminUnitList {

    List<AdminUnit> units = new ArrayList<>();
    BoundingBox bbox = new BoundingBox();

    public AdminUnitList(){}

    public void read(){
        try {
            // Tworzenie obiektu CSVReader
            CSVReader csvReader = new CSVReader("admin-units.csv", ",", true);
            System.out.println("Dane:");

            Map<Long, AdminUnit> unitsIdToName = new HashMap<>();

            while (csvReader.next()) {

                BoundingBox bb = new BoundingBox();

                for(int i = 1; i<6; i++){
                    bb.addPoint(csvReader.getDouble("x" + i), csvReader.getDouble("y" + i));
                }

                AdminUnit newUnit = new AdminUnit(
                        csvReader.get("name"),
                        csvReader.getInt("admin_level"),
                        csvReader.getDouble("population"),
                        csvReader.getDouble("area"),
                        csvReader.getDouble("density"),
                        bb
                );


                unitsIdToName.put(csvReader.getLong("id"), newUnit);

                units.add(newUnit);
            }

            csvReader = new CSVReader("admin-units.csv", ",", true);
            int i = 0;
            while (csvReader.next()) {
                if(!csvReader.isMissing("parent")){
                    AdminUnit parent = unitsIdToName.get(csvReader.getLong("parent"));
                    if(parent != null){
                        units.get(i).setParent(parent);
                        parent.addChildren(units.get(i));
                    }
                }
                i++;
            }

        } catch (RuntimeException e) {
            System.err.println("Błąd podczas przetwarzania pliku: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void addUnit(AdminUnit unit){
        units.add(unit);
    }

    public void list(PrintStream out, int offset, int limit){
        for (int i = offset; i < offset + limit; i++) {
            if(i >= units.size()) break;
            out.println(units.get(i).toString());
        }
    }

    public void list(PrintStream out){
        this.list(out, 0, 100);
    }

    public AdminUnit getUnit (int index){
        return units.get(index);
    }


    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList result = new AdminUnitList();
        if(regex){
            for (AdminUnit unit : units){
                if (unit.name.matches(pattern)) {
                    result.addUnit(unit);
                }
            }
        } else {
            for (AdminUnit unit : units){
                if (unit.name.contains(pattern)) {
                    result.addUnit(unit);
                }
            }
        }
        return result;
    }

    public AdminUnitList getNeighbors(AdminUnit unit, double maxdistance){
        AdminUnitList optionalNeighbors = this.selectAdminLevel(unit.adminLevel);
        AdminUnitList neighbors = new AdminUnitList();
        for (AdminUnit neighbor : optionalNeighbors.units){
            if(neighbor == unit) continue;
            if (unit.bbox.distanceTo(neighbor.bbox) <= maxdistance){
                neighbors.addUnit(neighbor);
            }
        }
        return neighbors;
    }


    public void fixMissingValues(){
        for (AdminUnit unit : units){
            if(Float.isNaN((float) unit.density) || Float.isNaN((float) unit.population) || unit.population * unit.density == 0) {
                unit.fixMissingValues();
            }
        }
    }

    public AdminUnitList selectAdminLevel(int data){
        AdminUnitList result = new AdminUnitList();
        for (AdminUnit unit : units){
            if (unit.adminLevel == data) {
                result.addUnit(unit);
            }
        }
        return result;
    }

    public AdminUnitList sortInplace(Comparator<AdminUnit> cmp){
        this.units.sort(cmp);
        return this;
    }

    public AdminUnitList sort(Comparator<AdminUnit> cmp){
        AdminUnitList result = new AdminUnitList();
//        copy all units
        result.units = new ArrayList<>(this.units);
        return result.sortInplace(cmp);
    }

    public AdminUnitList sortInplaceByName() {
        class NameComparator implements Comparator<AdminUnit> {
            @Override
            public int compare(AdminUnit o1, AdminUnit o2) {
                return o1.name.compareTo(o2.name);
            }
        }
        return this.sortInplace(new NameComparator());
    }

    public AdminUnitList sortInplaceByArea(){
        return this.sortInplace(new Comparator<AdminUnit>() {
            @Override
            public int compare(AdminUnit o1, AdminUnit o2) {
                return Double.compare(o1.area, o2.area);
            }
        });
    }

    public AdminUnitList sortInplaceByPopulation(){
        return this.sortInplace((o1, o2) -> Double.compare(o1.population, o2.population));
    }


    AdminUnitList filter(Predicate<AdminUnit> pred){
        return this.filter(pred,0, Integer.MAX_VALUE);
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit){
        AdminUnitList result = new AdminUnitList();
        int skipped = 0;
        int added = 0;
        for (AdminUnit unit : units) {
            if (pred.test(unit)) {
                if (skipped < offset) {
                    skipped++;
                    continue; // Skip until offset is reached
                }
                if (added < limit) {
                    result.addUnit(unit);
                    added++;
                } else {
                    break; // Stop if limit is reached
                }
            }
        }
        return result;
    }

    public void selectNameMatches(String pattern){}
    public void selectInside(double x1, double y1, double x2, double y2){}


}
