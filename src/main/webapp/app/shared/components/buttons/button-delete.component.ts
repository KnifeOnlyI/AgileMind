import {Component, EventEmitter, Output} from '@angular/core';

/**
 * Component to represent a delete button
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-button-delete',
  template: `
    <button type="button" class="btn btn-danger" (click)="onClick()">
      <fa-icon icon="trash"></fa-icon>
      <span jhiTranslate="entity.action.delete"></span>
    </button>`
})
export class ButtonDeleteComponent {
  @Output()
  public click = new EventEmitter();

  /**
   * Executed on click
   */
  public onClick(): void {
    this.click.emit();
  }
}
