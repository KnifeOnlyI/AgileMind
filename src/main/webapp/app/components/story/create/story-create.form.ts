import {FormControl, Validators} from '@angular/forms';
import {Story} from 'app/entities/story.entity';
import {BaseForm} from 'app/shared/form/base.form';

/**
 * Represent the form to create story
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class StoryCreateForm extends BaseForm {
  /**
   * Create new create story form
   *
   * @param projectId The project ID
   */
  public constructor(projectId: number) {
    super();

    this.form.addControl('name', new FormControl(null, Validators.required));
    this.form.addControl('description', new FormControl(null));
    this.form.addControl('points', new FormControl(0, [Validators.min(0)]));
    this.form.addControl('businessValue', new FormControl(0, [Validators.min(0)]));
    this.form.addControl('status', new FormControl(1, Validators.required));
    this.form.addControl('type', new FormControl(1, Validators.required));
    this.form.addControl('assignedUser', new FormControl(null));
    this.form.addControl('release', new FormControl(null));
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
   * Get the points form control
   *
   * @return The points form control
   */
  public get pointsFormControl(): FormControl {
    return this.getFormControl('points');
  }

  /**
   * Get the business value form control
   *
   * @return The business value form control
   */
  public get businessValueFormControl(): FormControl {
    return this.getFormControl('businessValue');
  }

  /**
   * Get the status form control
   *
   * @return The status form control
   */
  public get statusFormControl(): FormControl {
    return this.getFormControl('status');
  }

  /**
   * Get the type form control
   *
   * @return The status form control
   */
  public get typeFormControl(): FormControl {
    return this.getFormControl('type');
  }

  /**
   * Get the assigned user form control
   *
   * @return The assigned user form control
   */
  public get assignedUserFormControl(): FormControl {
    return this.getFormControl('assignedUser');
  }

  /**
   * Get the release form control
   *
   * @return The release form control
   */
  public get releaseFormControl(): FormControl {
    return this.getFormControl('release');
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

  /**
   * Get the name
   *
   * @return The name
   */
  public get name(): string {
    return this.nameFormControl.value;
  }

  /**
   * Get the description
   *
   * @return The description
   */
  public get description(): string {
    return this.descriptionFormControl.value;
  }

  /**
   * Get the points
   *
   * @return The points
   */
  public get points(): number {
    return this.pointsFormControl.value;
  }

  /**
   * Get the businessValue
   *
   * @return The businessValue
   */
  public get businessValue(): number {
    return this.businessValueFormControl.value;
  }

  /**
   * Get the status
   *
   * @return The status
   */
  public get status(): number {
    return this.statusFormControl.value;
  }

  /**
   * Get the type
   *
   * @return The type
   */
  public get type(): number {
    return this.typeFormControl.value;
  }

  /**
   * Get the assigned user id
   *
   * @return The assigned user id
   */
  public get assignedUser(): number {
    let value = this.assignedUserFormControl.value;

    if (value instanceof Array && value.length > 0) {
      value = value[0] as number;
    }

    return value;
  }

  /**
   * Get the release
   *
   * @return The release
   */
  public get release(): number {
    return this.releaseFormControl.value;
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
   * Get a new story from the form
   *
   * @return The created story. NULL if form is invalid
   */
  public get story(): Story {
    if (!this.isValid) {
      throw Error('The story is invalid');
    }

    return new Story(
      undefined,
      this.name,
      this.description,
      this.points,
      this.businessValue,
      this.status,
      this.type,
      this.assignedUser,
      this.release,
      this.project
    );
  }
}
