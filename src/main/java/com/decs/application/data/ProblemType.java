package com.decs.application.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProblemType {
    TWO_BOX (new ParameterGroupType[]{
            ParameterGroupType.EC, ParameterGroupType.SIMPLE, ParameterGroupType.KOZA
    }),
    ARTIFICIAL_ANT_SANTA_FE (new ParameterGroupType[]{
            ParameterGroupType.EC, ParameterGroupType.SIMPLE, ParameterGroupType.KOZA, ParameterGroupType.ANT
    }),
    LAWN_MOWER_8X8 (new ParameterGroupType[]{
            ParameterGroupType.EC, ParameterGroupType.SIMPLE, ParameterGroupType.KOZA
    }),
    BOOL_11_MULTIPLEXER_FAST (new ParameterGroupType[]{
            ParameterGroupType.EC, ParameterGroupType.SIMPLE, ParameterGroupType.KOZA
    });

    private final ParameterGroupType[] parameterGroupList;

    ProblemType(ParameterGroupType[] parameterGroupList) {
        this.parameterGroupList = parameterGroupList;
    }

    @Override
    public String toString() {
        return switch (this) {
            case TWO_BOX -> "Two Box";
            case ARTIFICIAL_ANT_SANTA_FE -> "Artificial Ant, Santa Fe Trail";
            case LAWN_MOWER_8X8 -> "Lawnmower";
            case BOOL_11_MULTIPLEXER_FAST -> "Boolean 11 Multiplexer";
        };
    }

    public ParameterGroupType[] getParameterGroups() {
        return this.parameterGroupList;
    }

    public static List<String> getValueList() {
        return Arrays.stream(ProblemType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}