import {FormControl, Validators} from '@angular/forms';
import {Project} from 'app/entities/project.entity';
import {BaseForm} from 'app/shared/form/base.form';

/**
 * Represent the form to create project
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class ProjectCreateForm extends BaseForm {
  /**
   * Constructor
   */
  public constructor() {
    super();

    this.form.addControl('name', new FormControl('', Validators.required));
    this.form.addControl('description', new FormControl(null));
  }

  // region Forms control

  /**
   * Get the name form control
   *
   * @return The name form control
   */
  public get nameFormControl(): FormControl {
    return this.getFormControl('name');
  }

  /**
   * Get the description form control
   *
   * @return The description form control
   */
  public get descriptionFormControl(): FormControl {
    return this.getFormControl('description');
  }

  // endregion

  // region Values

  public get name(): string {
    return this.nameFormControl.value;
  }

  public get description(): string {
    return this.descriptionFormControl.value;
  }

  // endregion

  /**
   * Get a new project from the form
   *
   * @return The created project. NULL if form is invalid
   */
  public get project(): Project {
    if (!this.isValid) {
      throw Error('The project is invalid');
    }

    return new Project(undefined, this.name, this.description);
  }
}
