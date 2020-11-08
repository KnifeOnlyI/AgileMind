package com.knife.agilemind.domain.story;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Represent a story status entity
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Entity
@Table(name = "story_status")
public class StoryStatusEntity implements Serializable {
    private static final long serialVersionUID = -7165996438526714842L;

    /**
     * The ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_status_seq")
    @SequenceGenerator(name = "story_status_seq", allocationSize = 1)
    private Long id;

    /**
     * The name key
     */
    @Column(name = "key", nullable = false)
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
    public StoryStatusEntity setId(Long id) {
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
    public StoryStatusEntity setKey(String key) {
        this.key = key;
        return this;
    }
}
