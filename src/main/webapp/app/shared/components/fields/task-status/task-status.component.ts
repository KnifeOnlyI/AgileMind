import {Component, Input, OnInit} from '@angular/core';
import {TaskStatus} from 'app/entities/task-status.entity';
import {TaskStatusService} from 'app/service/task-status.service';

/**
 * Component to manage task status displaying
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-task-status',
  template: `
    <span *ngIf="status" [ngStyle]="{'color': color}" jhiTranslate="{{status.key}}"></span>`
})
// @ts-ignore
export class TaskStatusComponent implements OnInit {
  @Input()
  public id!: number;

  /**
   * The task status
   */
  public status?: TaskStatus;

  /**
   * The class
   */
  public color = 'red';

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
    if (!this.id) {
      throw new Error('@Input() id CANNOT be undefined');
    }

    this.taskStatusService.get(this.id).subscribe((taskStatus) => {
      this.status = taskStatus;

      if (this.status.id === 2) {
        this.color = 'blue';
      } else if (this.status.id === 3) {
        this.color = 'green';
      }
    });
  }
}
