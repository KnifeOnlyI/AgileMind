package com.knife.agilemind.dto.story;

import java.io.Serializable;

/**
 * DTO to create story
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class CreateStoryDTO implements Serializable {
    private static final long serialVersionUID = -4719680673594901212L;

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
     * The assignated user id
     */
    private Long assignatedUserId;

    /**
     * The associated project id
     */
    private Long projectId;

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
    public CreateStoryDTO setName(String name) {
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
    public CreateStoryDTO setDescription(String description) {
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
    public CreateStoryDTO setPoints(Long points) {
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
    public CreateStoryDTO setBusinessValue(Long businessValue) {
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
    public CreateStoryDTO setStatusId(Long statusId) {
        this.statusId = statusId;
        return this;
    }

    /**
     * Get the value of : assignatedUserId
     *
     * @return assignatedUserId
     */
    public Long getAssignatedUserId() {
        return assignatedUserId;
    }

    /**
     * Set value of : assignatedUserId
     *
     * @param assignatedUserId The new value
     *
     * @return this
     */
    public CreateStoryDTO setAssignatedUserId(Long assignatedUserId) {
        this.assignatedUserId = assignatedUserId;
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
    public CreateStoryDTO setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }
}
