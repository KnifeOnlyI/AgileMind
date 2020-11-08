import {FormControl, FormGroup} from '@angular/forms';

/**
 * The base reactive form
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export abstract class BaseForm {
  /**
   * The root reactive form group
   */
  private _form = new FormGroup({});

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
   * Get the form control identified by the specified control name
   *
   * @param controlName The control name
   *
   * @return The founded form control
   */
  public getFormControl(controlName: string): FormControl {
    if (!this._form.contains(controlName)) {
      throw Error(`The form doesn't contains controlName : ${controlName}`);
    }

    return this._form.get(controlName) as FormControl;
  }
}
