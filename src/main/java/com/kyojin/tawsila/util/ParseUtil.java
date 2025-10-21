package com.kyojin.tawsila.util;

import com.kyojin.tawsila.enums.VehicleType;
import com.kyojin.tawsila.exception.BadRequestException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ParseUtil {

    private  ParseUtil() {
    }

    public static VehicleType parseVehicleType(String type) {
        return Arrays.stream(VehicleType.values())
                .filter(v -> v.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(
                        "Invalid vehicle type: " + type + ". Accepted types: " +
                                Arrays.stream(VehicleType.values())
                                        .map(Enum::name)
                                        .collect(Collectors.joining(", "))
                ));
    }
}
