import {Component, Input, OnInit} from '@angular/core';
import {TaskStatus} from 'app/entities/task-status.entity';
import {TaskStatusService} from 'app/service/task-status.service';
import {IconName} from '@fortawesome/fontawesome-svg-core';

/**
 * Component to manage task status displaying
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-task-status',
  template: `
    <fa-icon [ngStyle]="styles" [icon]="icon"></fa-icon>
  `
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
   * The icon
   */
  public icon: IconName = 'check';

  /**
   * The color
   */
  public styles = {'color': 'green'};

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

      if (this.status.id === 1) {
        this.icon = 'circle';
        this.styles.color = 'red';
      } else if (this.status.id === 2) {
        this.icon = 'spinner';
        this.styles.color = 'blue';
      }
    });
  }
}
