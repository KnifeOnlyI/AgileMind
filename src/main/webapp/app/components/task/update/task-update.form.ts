import {FormControl, Validators} from '@angular/forms';
import {Story} from 'app/entities/story.entity';
import {TaskCreateForm} from '../create/task-create.form';
import {Task} from 'app/entities/task.entity';

/**
 * Represent the form to update task
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class TaskUpdateForm extends TaskCreateForm {
  /**
   * Constructor
   *
   * @param task The task to update
   */
  public constructor(task: Task) {
    super(task.story!);

    this.form.addControl('id', new FormControl(task.id, Validators.required));

    this.form.patchValue({name: task.name});
    this.form.patchValue({description: task.description});
    this.form.patchValue({estimatedTime: task.estimatedTime});
    this.form.patchValue({loggedTime: task.loggedTime});
    this.form.patchValue({status: task.status});
    this.form.patchValue({assignedUser: task.assignedUser});
    this.form.patchValue({story: task.story});
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

  // endregion

  // region Values

  /**
   * Get the id
   *
   * @return The id
   */
  public get id(): number {
    return this.idFormControl.value;
  }

  // endregion

  /**
   * Get an updated task from the form
   *
   * @return The created task. NULL if form is invalid
   */
  public get task(): Story {
    const task = super.task;

    task.id = this.id;

    return task;
  }
}
