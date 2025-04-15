package reader;

public class BoundingBox {
//    max
    double xmin=Double.MAX_VALUE;
    double ymin=Double.MAX_VALUE;
//    min
    double xmax;
    double ymax;

    public BoundingBox(){};
    public void addPoint(double x, double y){
        if(xmax < x) xmax = x;
        else if(xmin > x) xmin = x;
        if(ymax < y) ymax = y;
        else if(ymin > y) ymin = y;
    }

    public String toString(){
        return "{xmin=" + xmin +", ymin=" + ymin +", xmax=" + xmax +", ymax=" + ymax +'}';
    }

    public boolean isEmpty(){
        return xmin == Double.MAX_VALUE || ymin == Double.MAX_VALUE || xmax * ymax == 0;
    }

    public boolean equals(Object o){
        return o instanceof BoundingBox && ((BoundingBox) o).xmin == xmin && ((BoundingBox) o).ymin == ymin && ((BoundingBox) o).xmax == xmax && ((BoundingBox) o).ymax == ymax;
    }

    public double getCenterX(){
        if(isEmpty())throw new RuntimeException("Values not provided");
        else return (xmin + xmax) / 2;
    }

    public double getCenterY(){
        if(isEmpty())throw new RuntimeException("Values not provided");
        else return (ymin + ymax) / 2;
    }

    public double distanceTo(BoundingBox bbx){
        if(isEmpty() || bbx.isEmpty())throw new RuntimeException("Values not provided");
        else {
//           using Haversine formula
            return haversine(getCenterX(), getCenterY(), bbx.getCenterX(), bbx.getCenterY());
        }
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6372.8;
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double dLat = lat2 - lat1;
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    public boolean intersects(BoundingBox other){
        return !(xmax < other.xmin || xmin > other.xmax || ymax < other.ymin || ymin > other.ymax);
    }

    public boolean contains(double x, double y){
        return x<=xmax && x>=xmin && y<=ymax && y>=ymin;
    }

    public boolean contains(BoundingBox other){
        return contains(other.xmax, other.ymax) && contains(other.ymin, other.ymax);
    }

}



