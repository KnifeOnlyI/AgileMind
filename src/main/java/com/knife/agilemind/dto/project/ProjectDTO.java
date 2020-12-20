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
     * The assigned user id list
     */
    private Set<Long> assignedUsers = new HashSet<>();

    /**
     * The project administrator user id list
     */
    private Set<Long> adminUsers = new HashSet<>();

    /**
     * The story id list
     */
    private Set<Long> stories = new HashSet<>();

    /**
     * The release id list
     */
    private Set<Long> releases = new HashSet<>();

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
     * Get the value of : assignedUsers
     *
     * @return assignedUsers
     */
    public Set<Long> getAssignedUsers() {
        return assignedUsers;
    }

    /**
     * Set value of : assignedUsers
     *
     * @param assignedUsers The new value
     *
     * @return this
     */
    public ProjectDTO setAssignedUsers(Set<Long> assignedUsers) {
        this.assignedUsers = assignedUsers;
        return this;
    }

    /**
     * Get the value of : adminUsers
     *
     * @return adminUsers
     */
    public Set<Long> getAdminUsers() {
        return adminUsers;
    }

    /**
     * Set value of : adminUsers
     *
     * @param adminUsers The new value
     *
     * @return this
     */
    public ProjectDTO setAdminUsers(Set<Long> adminUsers) {
        this.adminUsers = adminUsers;
        return this;
    }

    /**
     * Get the value of : stories
     *
     * @return stories
     */
    public Set<Long> getStories() {
        return stories;
    }

    /**
     * Set value of : stories
     *
     * @param stories The new value
     *
     * @return this
     */
    public ProjectDTO setStories(Set<Long> stories) {
        this.stories = stories;
        return this;
    }

    /**
     * Get the value of : releases
     *
     * @return releases
     */
    public Set<Long> getReleases() {
        return releases;
    }

    /**
     * Set value of : releases
     *
     * @param releases The new value
     *
     * @return this
     */
    public ProjectDTO setReleases(Set<Long> releases) {
        this.releases = releases;
        return this;
    }
}
