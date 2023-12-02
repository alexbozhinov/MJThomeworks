package bg.sofia.uni.fmi.mjt.firsthomework.itinerary.utils;

import java.util.Comparator;

public class CityNodeComparator implements Comparator<CityNode> {

    @Override
    public int compare(CityNode node1, CityNode node2) {
        return Integer.compare(node1.cost().intValue(), node2.cost().intValue());
    }
}
