package com.knife.agilemind.dto.release;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO to create release
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class CreateReleaseDTO implements Serializable {
    private static final long serialVersionUID = -5028509193428357363L;

    /**
     * The name
     */
    private String name;

    /**
     * The description
     */
    private String description;

    /**
     * The release date
     */
    private Date date;

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
    public CreateReleaseDTO setName(String name) {
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
    public CreateReleaseDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the value of : date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set value of : date
     *
     * @param date The new value
     *
     * @return this
     */
    public CreateReleaseDTO setDate(Date date) {
        this.date = date;
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
    public CreateReleaseDTO setProject(Long project) {
        this.project = project;
        return this;
    }
}
