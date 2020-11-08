import {Component, Input, OnInit} from '@angular/core';
import {User} from 'app/core/user/user.model';
import {UserService} from 'app/core/user/user.service';

/**
 * Component to manage user display
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-user',
  template: `
    <span *ngIf="user">{{user.firstName}} {{user.lastName}} ({{user.login}})</span>`
})
// @ts-ignore
export class UserComponent implements OnInit {
  @Input()
  public id!: number;

  /**
   * The user
   */
  public user?: User;

  /**
   * Constructor
   *
   * @param userService The user service
   */
  public constructor(private userService: UserService) {
  }

  /**
   * OnInit
   */
  public ngOnInit(): void {
    if (!this.id) {
      throw new Error('@Input() id CANNOT be undefined');
    }

    this.userService.get(this.id).subscribe((user) => {
      this.user = user;
    });
  }
}
