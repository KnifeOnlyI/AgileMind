import {FormControl, Validators} from '@angular/forms';
import {Story} from 'app/entities/story.entity';
import {StoryCreateForm} from '../create/story-create.form';

/**
 * Represent the form to update story
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class StoryUpdateForm extends StoryCreateForm {
  /**
   * Constructor
   *
   * @param story The story to update
   */
  public constructor(story: Story) {
    super(story.project!);

    this.form.addControl('id', new FormControl(story.id, Validators.required));

    this.form.patchValue({name: story.name});
    this.form.patchValue({description: story.description});
    this.form.patchValue({points: story.points});
    this.form.patchValue({businessValue: story.businessValue});
    this.form.patchValue({status: story.status});
    this.form.patchValue({type: story.type});
    this.form.patchValue({assignedUser: story.assignedUser});
    this.form.patchValue({release: story.release});
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
   * Get a updated story from the form
   *
   * @return The created story. NULL if form is invalid
   */
  public get story(): Story {
    const project = super.story;

    project.id = this.id;

    return project;
  }
}
