import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {AlertService} from 'app/service/alert.service';
import {Alert, AlertContent, AlertLevel} from 'app/shared/entity/alert.entity';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {
  ConfirmDialogComponent,
  ConfirmDialogType,
  ExitState
} from 'app/shared/components/dialogs/confirm/confirm-dialog.component';
import {TaskService} from 'app/service/task.service';
import {TaskUpdateForm} from 'app/components/task/update/task-update.form';

/**
 * Component to manage task update
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  /**
   * The form
   */
  public form!: TaskUpdateForm;

  /**
   * TRUE if the component is initialized, FALSE otherwise
   */
  public initialized = false;

  /**
   * Constructor
   *
   * @param taskService The task service
   * @param router The router service
   * @param routerService The router service
   * @param alertService The alert service
   * @param modalService The modal service
   */
  public constructor(
    private taskService: TaskService,
    private router: Router,
    private routerService: ActivatedRoute,
    private alertService: AlertService,
    private modalService: NgbModal,
  ) {
  }

  /**
   * OnInit
   */
  public ngOnInit(): void {
    this.routerService.params.subscribe(params => {
      if (params['id']) {
        this.getTask(params['id']);
      } else {
        throw Error('No project ID specified in URL');
      }
    });
  }

  /**
   * Submit the form
   */
  public onSubmit(): void {
    this.form.isValid ?
      this.onValidSubmit() :
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent('task.error.form.invalid')));
  }

  /**
   * Delete the task
   */
  public delete(): void {
    this.taskService.delete(this.form.id).subscribe(() => {
      this.alertService.add(new Alert(AlertLevel.SUCCESS, new AlertContent('task.alert.deleted')));

      this.router.navigate(['/story', 'edit', this.form.storyId]).then();
    });
  }

  /**
   * Manage delete confirmation
   */
  public confirmDelete(): void {
    const modalRef: ConfirmDialogComponent = this.modalService.open(ConfirmDialogComponent).componentInstance;

    modalRef.type = ConfirmDialogType.DELETE;

    modalRef.onClose.subscribe((exitState: ExitState) => {
      if (exitState === ExitState.YES) {
        this.delete();
      }
    });
  }

  /**
   * Executed on valid submit
   */
  private onValidSubmit(): void {
    this.taskService.save(this.form.task).subscribe((task) => {
      this.alertService.add(new Alert(AlertLevel.SUCCESS, new AlertContent('task.alert.updated')));

      this.router.navigate(['/story', 'edit', task.storyId]).then();
    }, (error: HttpErrorResponse) => {
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent(error.error.title)));
    });
  }

  /**
   * Get the task with the specified ID
   *
   * @param id The task id
   */
  private getTask(id: number): void {
    this.taskService.get(id).subscribe(task => {
        this.form = new TaskUpdateForm(task);

        this.initialized = true;
      },
      (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.router.navigate(['404']).then();
        }
      });
  }
}
