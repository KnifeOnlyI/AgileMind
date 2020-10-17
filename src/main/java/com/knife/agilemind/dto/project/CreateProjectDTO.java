package com.knife.agilemind.dto.project;

import java.io.Serializable;

/**
 * DTO to create project
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class CreateProjectDTO implements Serializable {
    private static final long serialVersionUID = 158300455889798573L;

    /**
     * The name
     */
    private String name;

    /**
     * The description
     */
    private String description;

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
    public CreateProjectDTO setName(String name) {
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
    public CreateProjectDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
