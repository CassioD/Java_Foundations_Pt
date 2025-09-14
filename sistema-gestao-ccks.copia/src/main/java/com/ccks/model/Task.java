package com.ccks.model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private int projectId;
    private int responsibleId;
    private TaskStatus status;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getProjectId() {
        return projectId;
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public int getResponsibleId() {
        return responsibleId;
    }
    public void setResponsibleId(int responsibleId) {
        this.responsibleId = responsibleId;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public LocalDate getPlannedStartDate() {
        return plannedStartDate;
    }
    public void setPlannedStartDate(LocalDate plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }
    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }
    public void setPlannedEndDate(LocalDate plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }
    public LocalDate getActualStartDate() {
        return actualStartDate;
    }
    public void setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
    }
    public LocalDate getActualEndDate() {
        return actualEndDate;
    }
    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }
}
