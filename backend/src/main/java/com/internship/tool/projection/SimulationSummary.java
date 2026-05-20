package com.internship.tool.projection;

// Day 13: A closed projection interface to fetch only targeted columns
public interface SimulationSummary {
    Long getId();
    String getName();
    String getStatus();
    String getTargetDepartment();
}