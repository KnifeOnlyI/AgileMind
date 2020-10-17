import {Component, Input} from '@angular/core';

/**
 * Component to represent a save button
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-button-save',
  template: `
    <button [disabled]="disabled" type="submit" class="btn btn-success">
      <fa-icon icon="save"></fa-icon>
      <span jhiTranslate="entity.action.save"></span>
    </button>`
})
export class ButtonSaveComponent {
  @Input()
  public disabled = false;
}
