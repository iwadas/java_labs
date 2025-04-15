package reader;

import java.io.FileNotFoundException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {


        AdminUnitList adminUnitList = new AdminUnitList();
        adminUnitList.read();

////        LAB 7
////        selectByName
////        (the output has parent element and children elements)
//        adminUnitList.selectByName("Kraków", false).list(System.out);
////        fixMissingValues
////        before (Bębło has missing population
//        adminUnitList.selectByName("Bębło", false).list(System.out, 0, 1);
//        adminUnitList.fixMissingValues();
////        after (Bębło has population)
//        adminUnitList.selectByName("Bębło", false).list(System.out, 0, 1);
//
////        LAB 8
//        AdminUnit Krakow = adminUnitList.selectAdminLevel(6).selectByName("Kraków", false).getUnit(0);
//        AdminUnit Wieliczka = adminUnitList.selectByName("Wieliczka", false).selectAdminLevel(8).getUnit(0);
////        checking intersection
//        System.out.println("Does Krakow intersects with Wieliczka?: " + Krakow.getBbox().intersects(Wieliczka.getBbox()));
////        checking  distance
//        System.out.println("Distance between Krakow and Wieliczka: " + Krakow.getBbox().distanceTo(Wieliczka.getBbox()));
////        neighbors
//        AdminUnitList neighbors = adminUnitList.getNeighbors(Krakow, 30);
//        neighbors.list(System.out, 0, 5);
//
////        LAB 9
////        checking filter and sortInPlaceByArea
//        adminUnitList.filter(a->a.name.startsWith("Ż")).sortInplaceByArea().list(System.out, 0, 10);
////        checking filter sortInPlaceByPopulation
//        adminUnitList.fixMissingValues();
//        adminUnitList.filter(a->a.name.startsWith("K"), 0, 10).sortInplaceByPopulation().list(System.out);
//        adminUnitList.filter(a->a.adminLevel == 6 && a.parent != null && a.parent.name.equals("województwo małopolskie") && a.name.startsWith("K")).list(System.out);
////        checking sort funciton
//        adminUnitList.sort((a,b)->Double.compare(a.area,b.area)).list(System.out, 0, 10);
////        AdminUnitQuery
//        System.out.println("Query 1:");
//        AdminUnitQuery query = new AdminUnitQuery()
//                .selectFrom(adminUnitList)
//                .where(a->a.area>1001)
//                .or(a->a.name.startsWith("Sz"))
//                .sort((a,b)->Double.compare(a.area,b.area))
//                .limit(10);
//        query.execute().list(System.out);
//
//        System.out.println("Query 2:");
//        AdminUnitQuery query2 = new AdminUnitQuery()
//                .selectFrom(adminUnitList)
//                .where(a->a.population>100000)
//                .and(a->a.parent!=null && a.parent.name.equals("województwo śląskie"))
//                .sort((a,b)->Double.compare(a.population,b.population))
//                .limit(10)
//                .offset(1);
//        query2.execute().list(System.out);
//
//        System.out.println("Query 3:");
//        AdminUnitQuery query3 = new AdminUnitQuery()
//                .selectFrom(adminUnitList)
//                .where(a->a.children.isEmpty())
//                .sort((a,b)->a.name.compareTo(b.name))
//                .offset(10)
//                .limit(1);
//        query3.execute().list(System.out);
//
//        System.out.println("Query 4:");
//        AdminUnitQuery query4 = new AdminUnitQuery()
//                .selectFrom(adminUnitList)
//                .where(a->a.name.matches("^wojew.*"))
//                .limit(3);
//
//        query4.execute().list(System.out);



    }
}
