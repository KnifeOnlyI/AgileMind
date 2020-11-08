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
     * The assignated user id list
     */
    private Set<Long> assignatedUserIdList = new HashSet<>();

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
     * Get the value of : assignatedUserIdList
     *
     * @return assignatedUserIdList
     */
    public Set<Long> getAssignatedUserIdList() {
        return assignatedUserIdList;
    }

    /**
     * Set value of : assignatedUserIdList
     *
     * @param assignatedUserIdList The new value
     *
     * @return this
     */
    public ProjectDTO setAssignatedUserIdList(Set<Long> assignatedUserIdList) {
        this.assignatedUserIdList = assignatedUserIdList;
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
