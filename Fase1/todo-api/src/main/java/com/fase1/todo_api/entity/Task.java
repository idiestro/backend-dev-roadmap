package com.fase1.todo_api.entity;

import jakarta.persistence.*;

@Entity                       // "esta clase es una tabla"
@Table(name = "tasks")        // opcional: nombre explícito de la tabla
public class Task {

    @Id                                                   // clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // la BD genera el id
    private Long id;

    private String title;
    private String description;
    private boolean completed;

    protected Task() {}       // JPA lo necesita (ver pregunta 1)

    //Create tasks
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }


    /*
     * getters y setters de title, description y completed; solo getter de id
     */

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}