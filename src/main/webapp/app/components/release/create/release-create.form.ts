import {FormControl, Validators} from '@angular/forms';
import {BaseForm} from 'app/shared/form/base.form';
import {Release} from 'app/entities/release.entity';

/**
 * Represent the form to create release
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class ReleaseCreateForm extends BaseForm {
  /**
   * Constructor
   */
  public constructor(projectId: number) {
    super();

    this.form.addControl('name', new FormControl('', Validators.required));
    this.form.addControl('description', new FormControl(null));
    this.form.addControl('date', new FormControl(null));
    this.form.addControl('project', new FormControl(projectId, Validators.required));
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

  /**
   * Get the date form control
   *
   * @return The date form control
   */
  public get dateFormControl(): FormControl {
    return this.getFormControl('date');
  }

  /**
   * Get the project form control
   *
   * @return The project form control
   */
  public get projectFormControl(): FormControl {
    return this.getFormControl('project');
  }

  // endregion

  // region Values

  public get name(): string {
    return this.nameFormControl.value;
  }

  public get description(): string {
    return this.descriptionFormControl.value;
  }

  /**
   * Get the date
   *
   * @return The date
   */
  public get date(): Date {
    return this.dateFormControl.value;
  }

  /**
   * Get the project
   *
   * @return The project
   */
  public get project(): number {
    return this.projectFormControl.value;
  }

  // endregion

  /**
   * Get a new release from the form
   *
   * @return The created release. NULL if form is invalid
   */
  public get release(): Release {
    if (!this.isValid) {
      throw Error('The release is invalid');
    }

    return new Release(undefined, this.name, this.description, this.date, undefined, this.project);
  }
}
