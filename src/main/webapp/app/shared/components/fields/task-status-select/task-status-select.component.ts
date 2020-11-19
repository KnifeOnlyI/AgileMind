import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {TaskStatus} from 'app/entities/task-status.entity';
import {TaskStatusService} from 'app/service/task-status.service';

/**
 * Component to manage task status selection
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-task-status-select',
  template: `
    <div *ngIf="control && taskStatuses" class="form-group">
      <label for="statusId"><span jhiTranslate="global.form.field.status"></span></label>
      <select [formControl]="control" class="form-control" id="statusId" name="statusId">
        <option *ngFor="let storyStatus of taskStatuses" [jhiTranslate]="storyStatus.key!"
                [value]="storyStatus.id"></option>
      </select>
    </div>`
})
// @ts-ignore
export class TaskStatusSelectComponent implements OnInit {
  /**
   * The form control on the field
   */
  @Input()
  public control!: FormControl;

  /**
   * The task statuses
   */
  public taskStatuses?: Array<TaskStatus>;

  /**
   * Constructor
   *
   * @param taskStatusService The task status service
   */
  public constructor(private taskStatusService: TaskStatusService) {
  }

  /**
   * OnInit
   */
  public ngOnInit(): void {
    if (!this.control) {
      throw new Error('this.control CANNOT be undefined');
    }

    this.taskStatusService.getAll().subscribe((taskStatuses) => {
      this.taskStatuses = taskStatuses;
    });
  }
}
