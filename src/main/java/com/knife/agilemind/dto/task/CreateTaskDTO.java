package com.knife.agilemind.dto.task;

import java.io.Serializable;

/**
 * DTO to create task
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class CreateTaskDTO implements Serializable {
    private static final long serialVersionUID = 1386376520891928371L;

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
    public CreateTaskDTO setName(String name) {
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
    public CreateTaskDTO setDescription(String description) {
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
    public CreateTaskDTO setEstimatedTime(Long estimatedTime) {
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
    public CreateTaskDTO setLoggedTime(Long loggedTime) {
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
    public CreateTaskDTO setStatus(Long status) {
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
    public CreateTaskDTO setAssignedUser(Long assignedUser) {
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
    public CreateTaskDTO setStory(Long story) {
        this.story = story;
        return this;
    }
}
