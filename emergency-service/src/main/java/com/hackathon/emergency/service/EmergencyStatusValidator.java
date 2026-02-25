package com.hackathon.emergency.service;

import com.hackathon.emergency.entity.EmergencyStatus;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class EmergencyStatusValidator {

    private static final Map<EmergencyStatus, Set<EmergencyStatus>> allowedTransitions =
            new EnumMap<>(EmergencyStatus.class);

    static {
        allowedTransitions.put(EmergencyStatus.CREATED,
                EnumSet.of(EmergencyStatus.ASSIGNED));

        allowedTransitions.put(EmergencyStatus.ASSIGNED,
                EnumSet.of(EmergencyStatus.IN_PROGRESS));

        allowedTransitions.put(EmergencyStatus.IN_PROGRESS,
                EnumSet.of(EmergencyStatus.RESOLVED));

        allowedTransitions.put(EmergencyStatus.RESOLVED,
                EnumSet.noneOf(EmergencyStatus.class));
    }

    public static boolean isValid(EmergencyStatus current, EmergencyStatus next) {
        return allowedTransitions
                .getOrDefault(current, EnumSet.noneOf(EmergencyStatus.class))
                .contains(next);
    }
}