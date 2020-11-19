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
    private Long points;

    /**
     * The business value
     */
    private Long businessValue;

    /**
     * The status id
     */
    private Long statusId;

    /**
     * The assigned user id
     */
    private Long assignedUserId;

    /**
     * The associated project id
     */
    private Long projectId;

    /**
     * The task id list
     */
    private Set<Long> taskIdList = new HashSet<>();

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
    public Long getPoints() {
        return points;
    }

    /**
     * Set value of : points
     *
     * @param points The new value
     *
     * @return this
     */
    public StoryDTO setPoints(Long points) {
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
    public StoryDTO setStatusId(Long statusId) {
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
    public StoryDTO setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
        return this;
    }

    /**
     * Get the value of : projectId
     *
     * @return projectId
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Set value of : projectId
     *
     * @param projectId The new value
     *
     * @return this
     */
    public StoryDTO setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * Get the value of : taskIdList
     *
     * @return taskIdList
     */
    public Set<Long> getTaskIdList() {
        return taskIdList;
    }

    /**
     * Set value of : taskIdList
     *
     * @param taskIdList The new value
     *
     * @return this
     */
    public StoryDTO setTaskIdList(Set<Long> taskIdList) {
        this.taskIdList = taskIdList;
        return this;
    }
}
