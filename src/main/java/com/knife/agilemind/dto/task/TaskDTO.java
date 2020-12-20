package com.knife.agilemind.dto.task;

import java.io.Serializable;

/**
 * DTO for view and edit task
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class TaskDTO implements Serializable {
    private static final long serialVersionUID = 4730635817459416352L;

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
     * The estimated time (in minutes)
     */
    private Long estimatedTime;

    /**
     * The logged time (in minutes)
     */
    private Long loggedTime;

    /**
     * The status id
     */
    private Long status;

    /**
     * The assigned user id
     */
    private Long assignedUser;

    /**
     * The associated story id
     */
    private Long story;

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
    public TaskDTO setId(Long id) {
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
    public TaskDTO setName(String name) {
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
    public TaskDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the value of : estimatedTime
     *
     * @return estimatedTime
     */
    public Long getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Set value of : estimatedTime
     *
     * @param estimatedTime The new value
     *
     * @return this
     */
    public TaskDTO setEstimatedTime(Long estimatedTime) {
        this.estimatedTime = estimatedTime;
        return this;
    }

    /**
     * Get the value of : loggedTime
     *
     * @return loggedTime
     */
    public Long getLoggedTime() {
        return loggedTime;
    }

    /**
     * Set value of : loggedTime
     *
     * @param loggedTime The new value
     *
     * @return this
     */
    public TaskDTO setLoggedTime(Long loggedTime) {
        this.loggedTime = loggedTime;
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
    public TaskDTO setStatus(Long status) {
        this.status = status;
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
    public TaskDTO setAssignedUser(Long assignedUser) {
        this.assignedUser = assignedUser;
        return this;
    }

    /**
     * Get the value of : story
     *
     * @return story
     */
    public Long getStory() {
        return story;
    }

    /**
     * Set value of : story
     *
     * @param story The new value
     *
     * @return this
     */
    public TaskDTO setStory(Long story) {
        this.story = story;
        return this;
    }
}
