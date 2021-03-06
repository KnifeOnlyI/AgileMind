import {FormControl, Validators} from '@angular/forms';
import {Task} from 'app/entities/task.entity';
import {BaseForm} from 'app/shared/form/base.form';

/**
 * Represent the form to create task
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class TaskCreateForm extends BaseForm {
  /**
   * Create new create task form
   *
   * @param storyId The story ID
   */
  public constructor(storyId: number) {
    super();

    this.form.addControl('name', new FormControl(null, Validators.required));
    this.form.addControl('description', new FormControl(null));
    this.form.addControl('estimatedTime', new FormControl(0, [Validators.min(0)]));
    this.form.addControl('loggedTime', new FormControl(0, [Validators.min(0)]));
    this.form.addControl('status', new FormControl(1, Validators.required));
    this.form.addControl('assignedUser', new FormControl(null));
    this.form.addControl('story', new FormControl(storyId, Validators.required));
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
   * Get the estimatedTime form control
   *
   * @return The estimatedTime form control
   */
  public get estimatedTimeFormControl(): FormControl {
    return this.getFormControl('estimatedTime');
  }

  /**
   * Get the loggedTime form control
   *
   * @return The loggedTime form control
   */
  public get loggedTimeFormControl(): FormControl {
    return this.getFormControl('loggedTime');
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
   * Get the assigned user form control
   *
   * @return The assigned user form control
   */
  public get assignedUserFormControl(): FormControl {
    return this.getFormControl('assignedUser');
  }

  /**
   * Get the storyId form control
   *
   * @return The storyId form control
   */
  public get storyFormControl(): FormControl {
    return this.getFormControl('story');
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
   * Get the estimatedTime
   *
   * @return The estimatedTime
   */
  public get estimatedTime(): number {
    return this.estimatedTimeFormControl.value;
  }

  /**
   * Get the loggedTime
   *
   * @return The loggedTime
   */
  public get loggedTime(): number {
    return this.loggedTimeFormControl.value;
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
   * Get the story
   *
   * @return The story
   */
  public get story(): number {
    return this.storyFormControl.value;
  }

  // endregion

  /**
   * Get a new task from the form
   *
   * @return The created task. NULL if form is invalid
   */
  public get task(): Task {
    if (!this.isValid) {
      throw Error('The form is invalid');
    }

    return new Task(
      undefined,
      this.name,
      this.description,
      this.estimatedTime,
      this.loggedTime,
      this.status,
      this.assignedUser,
      this.story
    );
  }
}
