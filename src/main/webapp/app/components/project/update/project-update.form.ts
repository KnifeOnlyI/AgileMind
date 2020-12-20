import {Project} from 'app/entities/project.entity';
import {ProjectCreateForm} from 'app/components/project/create/project-create.form';
import {FormControl, Validators} from '@angular/forms';

/**
 * Represent the form to create project
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class ProjectUpdateForm extends ProjectCreateForm {
  /**
   * Constructor
   *
   * @param project The project parameter
   */
  public constructor(project: Project) {
    super();

    if (!project.id || !project || !project.name) {
      throw Error('The project is invalid');
    }

    this.form.addControl('id', new FormControl(null, Validators.required));
    this.form.addControl('assignedUsers', new FormControl(null));
    this.form.addControl('adminUsers', new FormControl(null));

    this.form.patchValue({id: project.id});
    this.form.patchValue({name: project.name});
    this.form.patchValue({description: project.description});
    this.form.patchValue({assignedUsers: project.assignedUsers});
    this.form.patchValue({adminUsers: project.adminUsers});
  }

  // region Forms control

  /**
   * Get the id form control
   *
   * @return The id form control
   */
  public get idFormControl(): FormControl {
    return this.getFormControl('id');
  }

  /**
   * Get the assignedUsers form control
   *
   * @return The assignedUsers id form control
   */
  public get assignedUsersFormControl(): FormControl {
    return this.getFormControl('assignedUsers');
  }

  /**
   * Get the adminUsers form control
   *
   * @return The adminUsers form control
   */
  public get adminUsersFormControl(): FormControl {
    return this.getFormControl('adminUsers');
  }

  // endregion

  // region Values

  /**
   * Get the ID
   *
   * @return The ID
   */
  public get id(): number {
    return this.idFormControl.value;
  }


  /**
   * Get the assignedUsers
   *
   * @return The assignedUsers
   */
  public get assignedUsers(): Array<number> {
    return this.assignedUsersFormControl.value;
  }

  /**
   * Get the adminUsers
   *
   * @return The adminUsers
   */
  public get adminUsers(): Array<number> {
    return this.adminUsersFormControl.value;
  }

  // endregion

  /**
   * Get a updated project from the form
   *
   * @return The created project. NULL if form is invalid
   */
  public get project(): Project {
    const project = super.project;

    project.id = this.id;
    project.assignedUsers = this.assignedUsers;
    project.adminUsers = this.adminUsers;

    return project;
  }
}
