package com.knife.agilemind.dto.release;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for view and edit release
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class ReleaseDTO implements Serializable {
    private static final long serialVersionUID = -424273200292234981L;

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
     * The release date
     */
    private Date date;

    /**
     * The story id list
     */
    private Set<Long> stories = new HashSet<>();

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
    public ReleaseDTO setId(Long id) {
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
    public ReleaseDTO setName(String name) {
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
    public ReleaseDTO setDescription(String description) {
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
    public ReleaseDTO setDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Get the value of : stories
     *
     * @return stories
     */
    public Set<Long> getStories() {
        return stories;
    }

    /**
     * Set value of : stories
     *
     * @param stories The new value
     *
     * @return this
     */
    public ReleaseDTO setStories(Set<Long> stories) {
        this.stories = stories;
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
    public ReleaseDTO setProject(Long project) {
        this.project = project;
        return this;
    }
}
