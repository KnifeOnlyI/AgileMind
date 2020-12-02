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
    this.form.addControl('assignedUserIdList', new FormControl(null));
    this.form.addControl('adminUserIdList', new FormControl(null));

    this.form.patchValue({id: project.id});
    this.form.patchValue({name: project.name});
    this.form.patchValue({description: project.description});
    this.form.patchValue({assignedUserIdList: project.assignedUserIdList});
    this.form.patchValue({adminUserIdList: project.adminUserIdList});
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
   * Get the assigned users idf form control
   *
   * @return The assigned users idf form control
   */
  public get assignedUserIdListFormControl(): FormControl {
    return this.getFormControl('assignedUserIdList');
  }

  /**
   * Get the adminUserIdList form control
   *
   * @return The adminUserIdList form control
   */
  public get adminUserIdListFormControl(): FormControl {
    return this.getFormControl('adminUserIdList');
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
   * Get the assignedUserIdList
   *
   * @return The assignedUserIdList
   */
  public get assignedUserIdList(): Array<number> {
    return this.assignedUserIdListFormControl.value;
  }

  /**
   * Get the adminUserIdList
   *
   * @return The adminUserIdList
   */
  public get adminUserIdList(): Array<number> {
    return this.adminUserIdListFormControl.value;
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
    project.assignedUserIdList = this.assignedUserIdList;
    project.adminUserIdList = this.adminUserIdList;

    return project;
  }
}
