package bg.sofia.uni.fmi.mjt.firsthomework.itinerary;

import bg.sofia.uni.fmi.mjt.firsthomework.itinerary.exception.CityNotKnownException;
import bg.sofia.uni.fmi.mjt.firsthomework.itinerary.exception.NoPathToDestinationException;
import bg.sofia.uni.fmi.mjt.firsthomework.itinerary.utils.CityNode;
import bg.sofia.uni.fmi.mjt.firsthomework.itinerary.utils.CityNodeComparator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SequencedCollection;
import java.util.Set;

public class RideRight implements ItineraryPlanner {

    private final List<Journey> schedule;

    public RideRight(List<Journey> schedule) {
        this.schedule = new ArrayList<>(schedule);
    }

    @Override
    public SequencedCollection<Journey> findCheapestPath(City start, City destination, boolean allowTransfer)
        throws CityNotKnownException, NoPathToDestinationException {
        validateCities(start, destination);
        Set<City> visited = new HashSet<>();
        PriorityQueue<CityNode> priorityQueue = new PriorityQueue<>(new CityNodeComparator());
        CityNode startNode = new CityNode(start, null, BigDecimal.ZERO);
        priorityQueue.offer(startNode);
        while (!priorityQueue.isEmpty()) {
            CityNode current = priorityQueue.poll();
            City currentCity = current.city();

            if (currentCity.equals(destination)) {
                return new ArrayList<>(buildPath(current)) {
                };
            }
            visited.add(currentCity);

            for (Journey journey : findDirectJourneys(currentCity)) {
                if (!visited.contains(journey.to())) {
                    BigDecimal cost = calculateCost(current, journey, destination);
                    CityNode nextNode = new CityNode(journey.to(), current, cost);

                    if (allowTransfer || journey.from().equals(currentCity)) {
                        priorityQueue.offer(nextNode);
                    }
                }
            }
        }
        throw new NoPathToDestinationException(String.format("No path from city %s to city %s%n!", start.name(),
            destination.name()));
    }

    private List<Journey> findDirectJourneys(City from) {
        List<Journey> directJourneys = new ArrayList<>();
        for (Journey journey : schedule) {
            if (journey.from().equals(from)) {
                directJourneys.add(journey);
            }
        }
        return directJourneys;
    }

    private BigDecimal calculateCost(CityNode current, Journey journey, City destination) {
        BigDecimal basePrice = journey.price();
        BigDecimal greenTax = journey.vehicleType().getGreenTax();
        BigDecimal distance = calculateDistance(current.city().location(), journey.to().location());
        BigDecimal heuristic = calculateManhattanHeuristic(journey.to().location(), destination.location());
        return basePrice.add(basePrice.multiply(greenTax)).add(distance.multiply(new BigDecimal("20"))).add(heuristic);
    }

    private BigDecimal calculateManhattanHeuristic(Location from, Location to) {
        int deltaX = Math.abs(from.x() - to.x());
        int deltaY = Math.abs(from.y() - to.y());
        return new BigDecimal(deltaX + deltaY);
    }

    private BigDecimal calculateDistance(Location from, Location to) {
        int deltaX = Math.abs(from.x() - to.x());
        int deltaY = Math.abs(from.y() - to.y());

        return new BigDecimal(deltaX + deltaY);
    }

    private List<Journey> buildPath(CityNode destinationNode) {
        List<Journey> path = new ArrayList<>();
        CityNode current = destinationNode;

        while (current != null) {
            if (current.parent() != null) {
                for (Journey journey : schedule) {
                    if (journey.from().equals(current.parent().city()) &&
                        journey.to().equals(current.city())) {
                        path.add(journey);
                        break;
                    }
                }
            }
            current = current.parent();
        }

        Collections.reverse(path);
        return path;
    }

    private void validateCities(City start, City destination) throws CityNotKnownException {
        if (containsCity(start) || containsCity(destination)) {
            throw new CityNotKnownException(String.format("City %s or city %s is not known!", start.name(),
                destination.name()));
        }
    }

    private boolean containsCity(City city) {
        for (Journey journey : schedule) {
            if (journey.from().equals(city) || journey.to().equals(city)) {
                return false;
            }
        }
        return true;
    }
}
