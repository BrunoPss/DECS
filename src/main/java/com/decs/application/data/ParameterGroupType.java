package com.decs.application.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ParameterGroupType {
    EC, SIMPLE, KOZA;

    @Override
    public String toString() {
        return switch (this) {
            case EC -> "EC";
            case SIMPLE -> "Simple";
            case KOZA -> "Koza";
        };
    }

    public static List<String> getValueList() {
        return Arrays.stream(ParameterGroupType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}