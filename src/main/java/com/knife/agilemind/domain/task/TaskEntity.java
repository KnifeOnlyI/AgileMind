package com.knife.agilemind.domain.task;

import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.domain.user.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Represent a task entity
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Entity
@Table(name = "task")
public class TaskEntity implements Serializable {
    private static final long serialVersionUID = 6427208271832501413L;

    /**
     * The ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", allocationSize = 1)
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
     * The estimated time (in minutes)
     */
    @Column(name = "estimated_time")
    private Long estimatedTime;

    /**
     * The logged time (in minutes)
     */
    @Column(name = "logged_time")
    private Long loggedTime;

    /**
     * The status
     */
    @ManyToOne
    @JoinColumn(name = "status_id")
    private TaskStatusEntity status;

    /**
     * The assigned user
     */
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private UserEntity assignedUser;

    /**
     * The project
     */
    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private StoryEntity story;

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
    public TaskEntity setId(Long id) {
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
    public TaskEntity setName(String name) {
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
    public TaskEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the value of : estimatedTime
     *
     * @return estimatedTime
     */
    public Long getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Set value of : estimatedTime
     *
     * @param estimatedTime The new value
     *
     * @return this
     */
    public TaskEntity setEstimatedTime(Long estimatedTime) {
        this.estimatedTime = estimatedTime;
        return this;
    }

    /**
     * Get the value of : loggedTime
     *
     * @return loggedTime
     */
    public Long getLoggedTime() {
        return loggedTime;
    }

    /**
     * Set value of : loggedTime
     *
     * @param loggedTime The new value
     *
     * @return this
     */
    public TaskEntity setLoggedTime(Long loggedTime) {
        this.loggedTime = loggedTime;
        return this;
    }

    /**
     * Get the value of : status
     *
     * @return status
     */
    public TaskStatusEntity getStatus() {
        return status;
    }

    /**
     * Set value of : status
     *
     * @param status The new value
     *
     * @return this
     */
    public TaskEntity setStatus(TaskStatusEntity status) {
        this.status = status;
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
    public TaskEntity setAssignedUser(UserEntity assignedUser) {
        this.assignedUser = assignedUser;
        return this;
    }

    /**
     * Get the value of : story
     *
     * @return story
     */
    public StoryEntity getStory() {
        return story;
    }

    /**
     * Set value of : story
     *
     * @param story The new value
     *
     * @return this
     */
    public TaskEntity setStory(StoryEntity story) {
        this.story = story;
        return this;
    }
}
