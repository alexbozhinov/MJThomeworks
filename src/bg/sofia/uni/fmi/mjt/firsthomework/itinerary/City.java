package bg.sofia.uni.fmi.mjt.firsthomework.itinerary;

public record City(String name, Location location) {
    @Override
    public String toString() {
        return name;
    }
}
