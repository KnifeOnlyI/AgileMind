import {FormControl, Validators} from '@angular/forms';
import {Release} from 'app/entities/release.entity';
import {ReleaseCreateForm} from '../create/release-create.form';
import {DateUtil} from 'app/shared/util/date-util';

/**
 * Represent the form to create project
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class ReleaseUpdateForm extends ReleaseCreateForm {
  /**
   * Constructor
   *
   * @param release The release parameter
   */
  public constructor(release: Release) {
    if (!release || !release.id || !release.project || !release.name) {
      throw Error('The release is invalid');
    }

    super(release.project);

    this.form.addControl('id', new FormControl(null, Validators.required));

    this.form.patchValue({id: release.id});
    this.form.patchValue({name: release.name});
    this.form.patchValue({description: release.description});
    this.form.patchValue({date: DateUtil.dateToString(release.date)});
    this.form.patchValue({stories: release.stories});
    this.form.patchValue({project: release.project});
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
   * Get the ID
   *
   * @return The ID
   */
  public get id(): number {
    return this.idFormControl.value;
  }

  // endregion

  /**
   * Get the updated release from the form
   *
   * @return The created project. NULL if form is invalid
   */
  public get release(): Release {
    const release = super.release;

    release.id = this.id;
    release.date = this.date;

    return release;
  }
}
