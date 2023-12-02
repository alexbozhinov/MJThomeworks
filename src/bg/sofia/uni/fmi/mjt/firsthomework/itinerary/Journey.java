package bg.sofia.uni.fmi.mjt.firsthomework.itinerary;

import bg.sofia.uni.fmi.mjt.firsthomework.itinerary.vehicle.VehicleType;

import java.math.BigDecimal;

public record Journey(VehicleType vehicleType, City from, City to, BigDecimal price) {
    @Override
    public String toString() {
        return "Journey{" +
            vehicleType +
            ", from=" + from +
            ", to=" + to +
            ", price=" + price +
            '}';
    }
}
