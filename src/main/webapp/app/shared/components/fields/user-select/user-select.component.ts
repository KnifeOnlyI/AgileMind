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
  templateUrl: './user-select.component.html',
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

  /**
   * The label
   */
  @Input()
  public label = 'global.form.field.assignedUser';

  /**
   * TRUE if the component is initialized, FALSE otherwise
   */
  public initialized = false;

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

      this.initialized = true;
    });
  }
}
