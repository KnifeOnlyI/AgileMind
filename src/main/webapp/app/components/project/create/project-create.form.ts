import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Project} from 'app/entities/project.entity';

/**
 * Represent the form to create project
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class ProjectCreateForm {
  /**
   * The form group
   */
  private _form = new FormGroup({
    name: new FormControl('', Validators.required),
    description: new FormControl(''),
  });

  /**
   * Get the form
   *
   * @return The form group
   */
  get form(): FormGroup {
    return this._form;
  }

  /**
   * Check if the form is valid
   *
   * @return TRUE if is valid, FALSE otherwise
   */
  public get isValid(): boolean {
    return this._form.valid;
  }

  /**
   * Get the name
   *
   * @return The name
   */
  public get name(): string | undefined {
    return this._form.get('name')?.value;
  }

  /**
   * Get the description
   *
   * @return The description
   */
  public get description(): string | undefined {
    return this._form.get('description')?.value;
  }

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
