package reader;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.NaN;

public class AdminUnit {

    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    BoundingBox bbox;
    List<AdminUnit> children = new ArrayList<>();

    public AdminUnit(String name, int adminLevel, double population, double area, double density, BoundingBox bbox) {
        this.name = name;
        this.adminLevel = adminLevel;
        this.population = population;
        this.area = area;
        this.density = density;
        this.bbox = bbox;
        this.parent = null;
    }

    public void addChildren(AdminUnit child){
        children.add(child);
    }

    public String toString() {

        StringBuilder sb = new StringBuilder("{" +
                "\n\t" + "name=" + name +
                "\n\t" + "adminLevel=" + adminLevel +
                "\n\t" + "population=" + population +
                "\n\t" + "area=" + area +
                "\n\t" + "density=" + density +
                "\n\t" + "bbox=" + bbox.toString()
            );

        if(parent != null) {
            sb.append("\n\t" + "parent=" + parent.name);
        }

        if(children.size() > 0){
            sb.append("\n\t" + "children=[");
            for(AdminUnit child : children){
                sb.append("\n\t\t" + child.name);
            }
            sb.append("\n\t]");
        }
        sb.append("\n}");

        return sb.toString();
    }

    public void setParent(AdminUnit parent) {
        this.parent = parent;
    }

    private boolean missingDensityOrPopulation(){
        return Float.isNaN((float) population) || Float.isNaN((float) density) || population * density == 0;
    }

    public BoundingBox getBbox() {
        return bbox;
    }

    public void fixMissingValues(){
//        check if parent has values
        if(!missingDensityOrPopulation()) return;
        if(parent == null) return;
        if(parent.missingDensityOrPopulation()) parent.fixMissingValues();
//        fill missing values with parent's values
        if(population == 0 || Float.isNaN((float) population)){
            if(parent.population != 0) population = Math.floor(parent.area * parent.density);
        }
        if(density == 0 || Float.isNaN((float) density)){
            if(parent.density != 0) density = parent.density;
        }
    }

}
