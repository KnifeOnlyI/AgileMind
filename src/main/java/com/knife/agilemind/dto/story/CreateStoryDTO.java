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
     * The release id
     */
    private Long release;

    /**
     * The associated project id
     */
    private Long project;

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
    public CreateStoryDTO setPoints(Double points) {
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
    public CreateStoryDTO setStatus(Long status) {
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
    public CreateStoryDTO setType(Long type) {
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
    public CreateStoryDTO setAssignedUser(Long assignedUser) {
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
    public CreateStoryDTO setRelease(Long release) {
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
    public CreateStoryDTO setProject(Long project) {
        this.project = project;
        return this;
    }
}
