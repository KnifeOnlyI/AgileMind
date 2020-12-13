import {Component, Input, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {Task} from 'app/entities/task.entity';
import {TaskService} from 'app/service/task.service';
import {TaskStatusConstants} from 'app/constants/task-status.constants';
import {HttpErrorResponse} from '@angular/common/http';
import {Alert, AlertContent, AlertLevel} from 'app/shared/entity/alert.entity';
import {AlertService} from 'app/service/alert.service';

/**
 * The component to manage the story's task list view/update
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss'],
})
export class TaskListComponent implements OnInit {
  /**
   * The story id
   */
  @Input()
  public storyId!: number;

  /**
   * The task status constants list
   */
  public taskStatusConstants = TaskStatusConstants;

  /**
   * TRUE if the component is initialized, FALSE otherwise
   */
  public initialized = false;

  /**
   * The tasks with "TO DO" status
   */
  public todo = new Array<Task>();

  /**
   * The tasks with "IN PROGRESS" status
   */
  public inProgress = new Array<Task>();

  /**
   * The tasks with "DONE" status
   */
  public done = new Array<Task>();

  /**
   * Constructor
   *
   * @param taskService The task service
   * @param alertService The alert service
   */
  public constructor(private taskService: TaskService, private alertService: AlertService) {
  }

  /**
   * Move an item according to the specified event
   *
   * @param event The event
   */
  private static moveItem(event: CdkDragDrop<Array<Task>>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }

  /**
   * On init
   */
  public ngOnInit(): void {
    if (!this.storyId) {
      throw Error('No story ID specified in : @Input() storyId');
    }

    this.taskService.getAllFromStory(this.storyId).subscribe((tasks) => {
      tasks.sort((a, b) => a.id! - b.id!);

      tasks.forEach(task => {
        if (task.statusId === TaskStatusConstants.ID.TODO) {
          this.todo.push(task);
        } else if (task.statusId === TaskStatusConstants.ID.IN_PROGRESS) {
          this.inProgress.push(task);
        } else if (task.statusId === TaskStatusConstants.ID.DONE) {
          this.done.push(task);
        } else {
          throw new Error(`Not managed task status : ${task.statusId}`);
        }
      });

      this.initialized = true;
    });
  }

  /**
   * Move task according to the specified event
   *
   * @param event The received event
   * @param newStatusId The new status id
   */
  public moveTask(event: CdkDragDrop<Array<Task>>, newStatusId: number): void {
    const task = event.previousContainer.data[event.previousIndex];

    const oldStatusId = task.statusId;

    if (task.statusId === newStatusId) {
      TaskListComponent.moveItem(event);
    } else {
      task.statusId = newStatusId;

      this.taskService.save(task).subscribe(
        () => TaskListComponent.moveItem(event),
        (error: HttpErrorResponse) => {
          // If error, cancel status change
          task.statusId = oldStatusId;

          this.onError(error);
        }
      );
    }
  }

  /**
   * Executed on http error
   *
   * @param error The error
   */
  private onError(error: HttpErrorResponse): void {
    switch (error.status) {
      case 0:
        this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent('error.http.0')));
        break;
      case 404:
        this.alertService.add(new Alert(
          AlertLevel.ERROR,
          new AlertContent(error.error.title ? error.error.title : error.error.message))
        );
        break;
    }
  }
}
