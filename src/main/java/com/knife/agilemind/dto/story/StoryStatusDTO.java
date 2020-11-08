package com.knife.agilemind.dto.story;

import java.io.Serializable;

/**
 * Represent the DTO for StoryStatus
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
public class StoryStatusDTO implements Serializable {
    private static final long serialVersionUID = 7090279478512334241L;

    /**
     * The ID
     */
    private Long id;

    /**
     * The name key
     */
    private String key;

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
    public StoryStatusDTO setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get the value of : key
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Set value of : key
     *
     * @param key The new value
     *
     * @return this
     */
    public StoryStatusDTO setKey(String key) {
        this.key = key;
        return this;
    }
}
