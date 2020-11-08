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
    this.form.addControl('assignatedUserIdList', new FormControl(null));

    this.form.patchValue({id: project.id});
    this.form.patchValue({name: project.name});
    this.form.patchValue({description: project.description});
    this.form.patchValue({assignatedUserIdList: project.assignatedUserIdList});
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
   * Get the assignated users idf form control
   *
   * @return The assignated users idf form control
   */
  public get assignatedUserIdListFormControl(): FormControl {
    return this.getFormControl('assignatedUserIdList');
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
   * Get the assignatedUserIdList
   *
   * @return The assignatedUserIdList
   */
  public get assignatedUserIdList(): Array<number> {
    return this.assignatedUserIdListFormControl.value;
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
    project.assignatedUserIdList = this.assignatedUserIdList;

    return project;
  }
}
