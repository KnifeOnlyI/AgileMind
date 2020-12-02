import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {IUser} from 'app/core/user/user.model';
import {UserService} from 'app/core/user/user.service';

/**
 * Component to manage user(s) selection
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-user-select',
  template: `
    <div *ngIf="control && users" class="form-group">
      <label for="assignedUserId">
        <span [jhiTranslate]="label">
        </span>
      </label>
      <ng-container *ngIf="multiple">
        <select class="form-control" id="assignedUserId" name="assignedUserId" [formControl]="control"
                [multiple]="multiple">
          <option [value]="null">---</option>
          <option *ngFor="let user of users" [value]="user.id">{{ user.login }}</option>
        </select>
      </ng-container>
      <ng-container *ngIf="!multiple">
        <select class="form-control" id="assignedUserId" name="assignedUserId" [formControl]="control">
          <option [value]="null">---</option>
          <option *ngFor="let user of users" [value]="user.id">{{ user.login }}</option>
        </select>
      </ng-container>
    </div>`
})
// @ts-ignore
export class UserSelectComponent implements OnInit {
  /**
   * The form control on the field
   */
  @Input()
  public control!: FormControl;

  /**
   * Determine if the user can choose multiple users
   */
  @Input()
  public multiple = false;

  @Input()
  public label = 'global.form.field.assignedUser';

  /**
   * Users list
   */
  public users?: Array<IUser>;

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
    if (!this.control) {
      throw new Error('this.control CANNOT be undefined');
    }

    this.userService.query().subscribe((users) => {
      this.users = users.body ? users.body : new Array<IUser>();
    });
  }
}
