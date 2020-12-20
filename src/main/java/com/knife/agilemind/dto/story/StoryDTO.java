package com.knife.agilemind.dto.story;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for view and edit story
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class StoryDTO implements Serializable {
    private static final long serialVersionUID = -71044270757697742L;

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
     * The story points
     */
    private Double points;

    /**
     * The business value
     */
    private Long businessValue;

    /**
     * The status id
     */
    private Long status;

    /**
     * The type id
     */
    private Long type;

    /**
     * The assigned user id
     */
    private Long assignedUser;

    /**
     * The associated release id
     */
    private Long release;

    /**
     * The associated project id
     */
    private Long project;

    /**
     * The task id list
     */
    private Set<Long> tasks = new HashSet<>();

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
    public StoryDTO setId(Long id) {
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
    public StoryDTO setName(String name) {
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
    public StoryDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the value of : points
     *
     * @return points
     */
    public Double getPoints() {
        return points;
    }

    /**
     * Set value of : points
     *
     * @param points The new value
     *
     * @return this
     */
    public StoryDTO setPoints(Double points) {
        this.points = points;
        return this;
    }

    /**
     * Get the value of : businessValue
     *
     * @return businessValue
     */
    public Long getBusinessValue() {
        return businessValue;
    }

    /**
     * Set value of : businessValue
     *
     * @param businessValue The new value
     *
     * @return this
     */
    public StoryDTO setBusinessValue(Long businessValue) {
        this.businessValue = businessValue;
        return this;
    }

    /**
     * Get the value of : status
     *
     * @return status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * Set value of : status
     *
     * @param status The new value
     *
     * @return this
     */
    public StoryDTO setStatus(Long status) {
        this.status = status;
        return this;
    }

    /**
     * Get the value of : type
     *
     * @return type
     */
    public Long getType() {
        return type;
    }

    /**
     * Set value of : type
     *
     * @param type The new value
     *
     * @return this
     */
    public StoryDTO setType(Long type) {
        this.type = type;
        return this;
    }

    /**
     * Get the value of : assignedUser
     *
     * @return assignedUser
     */
    public Long getAssignedUser() {
        return assignedUser;
    }

    /**
     * Set value of : assignedUser
     *
     * @param assignedUser The new value
     *
     * @return this
     */
    public StoryDTO setAssignedUser(Long assignedUser) {
        this.assignedUser = assignedUser;
        return this;
    }

    /**
     * Get the value of : release
     *
     * @return release
     */
    public Long getRelease() {
        return release;
    }

    /**
     * Set value of : release
     *
     * @param release The new value
     *
     * @return this
     */
    public StoryDTO setRelease(Long release) {
        this.release = release;
        return this;
    }

    /**
     * Get the value of : project
     *
     * @return project
     */
    public Long getProject() {
        return project;
    }

    /**
     * Set value of : project
     *
     * @param project The new value
     *
     * @return this
     */
    public StoryDTO setProject(Long project) {
        this.project = project;
        return this;
    }

    /**
     * Get the value of : tasks
     *
     * @return tasks
     */
    public Set<Long> getTasks() {
        return tasks;
    }

    /**
     * Set value of : tasks
     *
     * @param tasks The new value
     *
     * @return this
     */
    public StoryDTO setTasks(Set<Long> tasks) {
        this.tasks = tasks;
        return this;
    }
}
