package com.knife.agilemind.dto.release;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for view and edit release
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class UpdateReleaseDTO implements Serializable {
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
    public UpdateReleaseDTO setId(Long id) {
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
    public UpdateReleaseDTO setName(String name) {
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
    public UpdateReleaseDTO setDescription(String description) {
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
    public UpdateReleaseDTO setDate(Date date) {
        this.date = date;
        return this;
    }
}
