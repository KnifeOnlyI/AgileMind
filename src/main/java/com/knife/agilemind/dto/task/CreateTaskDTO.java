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
    private Long statusId;

    /**
     * The assigned user id
     */
    private Long assignedUserId;

    /**
     * The associated story id
     */
    private Long storyId;

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
     * Get the value of : statusId
     *
     * @return statusId
     */
    public Long getStatusId() {
        return statusId;
    }

    /**
     * Set value of : statusId
     *
     * @param statusId The new value
     *
     * @return this
     */
    public CreateTaskDTO setStatusId(Long statusId) {
        this.statusId = statusId;
        return this;
    }

    /**
     * Get the value of : assignedUserId
     *
     * @return assignedUserId
     */
    public Long getAssignedUserId() {
        return assignedUserId;
    }

    /**
     * Set value of : assignedUserId
     *
     * @param assignedUserId The new value
     *
     * @return this
     */
    public CreateTaskDTO setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
        return this;
    }

    /**
     * Get the value of : storyId
     *
     * @return storyId
     */
    public Long getStoryId() {
        return storyId;
    }

    /**
     * Set value of : storyId
     *
     * @param storyId The new value
     *
     * @return this
     */
    public CreateTaskDTO setStoryId(Long storyId) {
        this.storyId = storyId;
        return this;
    }
}
