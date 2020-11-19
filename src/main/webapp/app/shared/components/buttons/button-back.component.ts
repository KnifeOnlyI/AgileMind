import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';

/**
 * Component to represent a back button
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-button-back',
  template: `
    <button type="button" class="btn btn-secondary" (click)="goToLink()">
      <fa-icon icon="arrow-left"></fa-icon>
      <span jhiTranslate="entity.action.back"></span>
    </button>`
})
export class ButtonBackComponent {
  @Input()
  public routerLink: any[] | string;

  /**
   * Constructor
   *
   * @param router The router service
   */
  public constructor(private router: Router) {
    this.routerLink = new Array<any>();
  }

  /**
   * Navigate to the specified @Input() routerLink
   */
  public goToLink(): void {
    if (this.routerLink instanceof Array) {
      this.router.navigate(this.routerLink).then();
    } else {
      this.router.navigate([this.routerLink]).then();
    }
  }
}
