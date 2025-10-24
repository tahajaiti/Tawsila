package com.kyojin.tawsila.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DistanceCalculatorTest {


    @Test
    void testDistanceBetweenTwoPoints() {
        // tokyo 35.6764° N, 139.6500° E
        // amsterdam 52.3676° N, 4.9041° E

        var tokyoLat = 35.6764;
        var tokyoLon = 139.6500;
        var amsterdamLat = 52.3676;
        var amsterdamLon = 4.9041;


        double distance = DistanceCalculator.calculateDistance(tokyoLat, tokyoLon, amsterdamLat, amsterdamLon);

        // distance is approximately 9260 km
        assertThat(distance).isBetween(9200.0, 9350.0);
    }

    @Test
    void testDistanceBetweenSamePoints() {
        var lat = 12.4354;
        var lon = -43.0354;

        double distance = DistanceCalculator.calculateDistance(lat, lon, lat, lon);

        // distance should be 0 km
        assertThat(distance).isEqualTo(0.0);
    }

    @Test
    void testDistanceBetweenEquatorPoints() {
        // point1 0° N, 0° E
        // point2 0° N, 90° E

        var point1Lat = 0.0;
        var point1Lon = 0.0;
        var point2Lat = 0.0;
        var point2Lon = 90.0;

        double distance = DistanceCalculator.calculateDistance(point1Lat, point1Lon, point2Lat, point2Lon);

        // distance is approximately 10007 km
        assertThat(distance).isBetween(10000.0, 10015.0);
    }
}
