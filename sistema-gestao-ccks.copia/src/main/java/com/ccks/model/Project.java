package com.ccks.model;

import java.time.LocalDate;

/**
 * Classe de Modelo (POJO) que representa um Projeto.
 */
public class Project {

    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate plannedEndDate;
    private ProjectStatus status;
    private int managerId;

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(LocalDate plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    /**
     * Retorna a representação em String do objeto, que por padrão será o nome do projeto.
     * Isso é crucial para que componentes de UI como o ComboBox exibam o nome do projeto
     * em vez da representação padrão do objeto (ex: com.ccks.model.Project@123abcde).
     * @return O nome do projeto.mvn clean javafx:run
     */
    @Override
    public String toString() {
        return this.name;
    }
}
