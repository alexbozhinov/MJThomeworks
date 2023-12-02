package bg.sofia.uni.fmi.mjt.firsthomework.itinerary.utils;

import bg.sofia.uni.fmi.mjt.firsthomework.itinerary.City;

import java.math.BigDecimal;

public record CityNode(City city, CityNode parent, BigDecimal cost) {
}
