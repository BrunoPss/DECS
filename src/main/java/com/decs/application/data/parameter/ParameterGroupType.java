package com.decs.application.data.parameter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ParameterGroupType {
    EC, SIMPLE, KOZA, ANT;

    @Override
    public String toString() {
        return switch (this) {
            case EC -> "EC";
            case SIMPLE -> "Simple";
            case KOZA -> "Koza";
            case ANT -> "Ant";
        };
    }

    public static List<String> getValueList() {
        return Arrays.stream(ParameterGroupType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}