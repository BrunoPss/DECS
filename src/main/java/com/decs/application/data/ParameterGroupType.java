package com.decs.application.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ParameterGroupType {
    SIMPLE, KOZA, ANT;

    @Override
    public String toString() {
        return switch (this) {
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