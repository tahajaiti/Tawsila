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

    public static <T extends Enum<T>> T parseType(String type, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(
                        "Invalid type: " + type +
                                ". Accepted: " + String.join(", ",
                                Arrays.stream(enumClass.getEnumConstants())
                                        .map(Enum::name)
                                        .toList())
                ));
    }
}
