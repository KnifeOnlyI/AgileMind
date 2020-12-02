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
    private Set<Long> assignedUserIdList = new HashSet<>();

    /**
     * The project administrator user id list
     */
    private Set<Long> adminUserIdList = new HashSet<>();

    /**
     * The story id list
     */
    private Set<Long> storyIdList = new HashSet<>();

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
     * Get the value of : assignedUserIdList
     *
     * @return assignedUserIdList
     */
    public Set<Long> getAssignedUserIdList() {
        return assignedUserIdList;
    }

    /**
     * Set value of : assignedUserIdList
     *
     * @param assignedUserIdList The new value
     *
     * @return this
     */
    public ProjectDTO setAssignedUserIdList(Set<Long> assignedUserIdList) {
        this.assignedUserIdList = assignedUserIdList;
        return this;
    }

    /**
     * Get the value of : adminUserIdList
     *
     * @return adminUserIdList
     */
    public Set<Long> getAdminUserIdList() {
        return adminUserIdList;
    }

    /**
     * Set value of : adminUserIdList
     *
     * @param adminUserIdList The new value
     *
     * @return this
     */
    public ProjectDTO setAdminUserIdList(Set<Long> adminUserIdList) {
        this.adminUserIdList = adminUserIdList;
        return this;
    }

    /**
     * Get the value of : storyIdList
     *
     * @return storyIdList
     */
    public Set<Long> getStoryIdList() {
        return storyIdList;
    }

    /**
     * Set value of : storyIdList
     *
     * @param storyIdList The new value
     *
     * @return this
     */
    public ProjectDTO setStoryIdList(Set<Long> storyIdList) {
        this.storyIdList = storyIdList;
        return this;
    }
}
