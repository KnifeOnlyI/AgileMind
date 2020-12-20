package com.knife.agilemind.domain.release;

import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.story.StoryEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Represent a release
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Entity
@Table(name = "release")
public class ReleaseEntity implements Serializable {
    private static final long serialVersionUID = 5768355829550312355L;

    /**
     * The ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_seq")
    @SequenceGenerator(name = "release_seq", allocationSize = 1)
    private Long id;

    /**
     * The name
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The description
     */
    @Column(name = "description")
    private String description;

    /**
     * The release date
     */
    @Column(name = "date", columnDefinition = "DATE")
    private Date date;

    /**
     * The stories
     */
    @OneToMany(mappedBy = "release")
    private Set<StoryEntity> stories = new HashSet<>();

    /**
     * The project
     */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

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
    public ReleaseEntity setId(Long id) {
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
    public ReleaseEntity setName(String name) {
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
    public ReleaseEntity setDescription(String description) {
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
    public ReleaseEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Get the value of : stories
     *
     * @return stories
     */
    public Set<StoryEntity> getStories() {
        return stories;
    }

    /**
     * Set value of : stories
     *
     * @param stories The new value
     *
     * @return this
     */
    public ReleaseEntity setStories(Set<StoryEntity> stories) {
        this.stories = stories;
        return this;
    }

    /**
     * Get the value of : project
     *
     * @return project
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * Set value of : project
     *
     * @param project The new value
     *
     * @return this
     */
    public ReleaseEntity setProject(ProjectEntity project) {
        this.project = project;
        return this;
    }
}
