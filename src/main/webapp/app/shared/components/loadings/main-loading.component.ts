import {Component, Input} from '@angular/core';

/**
 * Component to represent a main loading
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-main-loading',
  template: `
    <h1 class="text-center">
      <fa-icon icon="spinner" [spin]="true"></fa-icon>&nbsp;
      <span [jhiTranslate]="label"></span>
    </h1>`
})
export class MainLoadingComponent {
  /**
   * The label
   */
  @Input()
  public label = 'global.loading';
}
