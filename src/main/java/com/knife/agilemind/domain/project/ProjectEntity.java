package com.knife.agilemind.domain.project;

import com.knife.agilemind.domain.user.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = -5698107925604071104L;

    /**
     * The ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name = "project_seq", allocationSize = 1)
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
     * The assignated users list
     */
    @ManyToMany
    @JoinTable(
        name = "project_user",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> assignatedUsers = new HashSet<>();

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
    public ProjectEntity setId(Long id) {
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
    public ProjectEntity setName(String name) {
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
    public ProjectEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Récupère : assignatedUsers.
     *
     * @return assignatedUsers.
     */
    public Set<UserEntity> getAssignatedUsers() {
        return assignatedUsers;
    }

    /**
     * Défini la nouvelle valeur de : assignatedUsers.
     *
     * @param assignatedUsers La nouvelle valeur.
     *
     * @return L'instance (Pattern fluent)
     */
    public ProjectEntity setAssignatedUsers(Set<UserEntity> assignatedUsers) {
        this.assignatedUsers = assignatedUsers;
        return this;
    }
}
