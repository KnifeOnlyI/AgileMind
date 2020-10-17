package com.knife.agilemind.dto.project;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for view and edit project
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class ProjectDTO implements Serializable {
    private static final long serialVersionUID = -3434903606748710493L;

    /**
     * The ID
     */
    private Long id;

    /**
     * The name
     */
    private String name;

    /**
     * The description
     */
    private String description;

    /**
     * The assignated users ID list
     */
    private Set<Long> assignatedUsers = new HashSet<>();

    /**
     * Get the value of : serialVersionUID
     *
     * @return serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Get the value of : id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set value of : id
     *
     * @param id The new value
     *
     * @return this
     */
    public ProjectDTO setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get the value of : name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set value of : name
     *
     * @param name The new value
     *
     * @return this
     */
    public ProjectDTO setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the value of : description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set value of : description
     *
     * @param description The new value
     *
     * @return this
     */
    public ProjectDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Récupère : assignatedUsers.
     *
     * @return assignatedUsers.
     */
    public Set<Long> getAssignatedUsers() {
        return assignatedUsers;
    }

    /**
     * Défini la nouvelle valeur de : assignatedUsers.
     *
     * @param assignatedUsers La nouvelle valeur.
     *
     * @return L'instance (Pattern fluent)
     */
    public ProjectDTO setAssignatedUsers(Set<Long> assignatedUsers) {
        this.assignatedUsers = assignatedUsers;
        return this;
    }
}
