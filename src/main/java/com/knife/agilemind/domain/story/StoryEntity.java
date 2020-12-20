package com.knife.agilemind.domain.story;

import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.release.ReleaseEntity;
import com.knife.agilemind.domain.task.TaskEntity;
import com.knife.agilemind.domain.user.UserEntity;

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
import java.util.HashSet;
import java.util.Set;

/**
 * Represent a story entity
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Entity
@Table(name = "story")
public class StoryEntity implements Serializable {
    private static final long serialVersionUID = -6760681991131405269L;

    /**
     * The ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_seq")
    @SequenceGenerator(name = "story_seq", allocationSize = 1)
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
     * The story points
     */
    @Column(name = "points")
    private Double points;

    /**
     * The business value
     */
    @Column(name = "business_value")
    private Long businessValue;

    /**
     * The status
     */
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StoryStatusEntity status;

    /**
     * The type
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    private StoryTypeEntity type;

    /**
     * The assigned user
     */
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private UserEntity assignedUser;

    /**
     * The tasks
     */
    @OneToMany(mappedBy = "story", orphanRemoval = true)
    private Set<TaskEntity> tasks = new HashSet<>();

    /**
     * The release
     */
    @ManyToOne
    @JoinColumn(name = "release_id")
    private ReleaseEntity release;

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
    public StoryEntity setId(Long id) {
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
    public StoryEntity setName(String name) {
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
    public StoryEntity setDescription(String description) {
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
    public StoryEntity setPoints(Double points) {
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
    public StoryEntity setBusinessValue(Long businessValue) {
        this.businessValue = businessValue;
        return this;
    }

    /**
     * Get the value of : status
     *
     * @return status
     */
    public StoryStatusEntity getStatus() {
        return status;
    }

    /**
     * Set value of : status
     *
     * @param status The new value
     *
     * @return this
     */
    public StoryEntity setStatus(StoryStatusEntity status) {
        this.status = status;
        return this;
    }

    /**
     * Get the value of : type
     *
     * @return type
     */
    public StoryTypeEntity getType() {
        return type;
    }

    /**
     * Set value of : type
     *
     * @param type The new value
     *
     * @return this
     */
    public StoryEntity setType(StoryTypeEntity type) {
        this.type = type;
        return this;
    }

    /**
     * Get the value of : assignedUser
     *
     * @return assignedUser
     */
    public UserEntity getAssignedUser() {
        return assignedUser;
    }

    /**
     * Set value of : assignedUser
     *
     * @param assignedUser The new value
     *
     * @return this
     */
    public StoryEntity setAssignedUser(UserEntity assignedUser) {
        this.assignedUser = assignedUser;
        return this;
    }

    /**
     * Get the value of : tasks
     *
     * @return tasks
     */
    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    /**
     * Set value of : tasks
     *
     * @param tasks The new value
     *
     * @return this
     */
    public StoryEntity setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
        return this;
    }

    /**
     * Get the value of : release
     *
     * @return release
     */
    public ReleaseEntity getRelease() {
        return release;
    }

    /**
     * Set value of : release
     *
     * @param release The new value
     *
     * @return this
     */
    public StoryEntity setRelease(ReleaseEntity release) {
        this.release = release;
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
    public StoryEntity setProject(ProjectEntity project) {
        this.project = project;
        return this;
    }
}
