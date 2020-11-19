import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {AlertService} from 'app/service/alert.service';
import {Alert, AlertContent, AlertLevel} from 'app/shared/entity/alert.entity';
import {TaskCreateForm} from './task-create.form';
import {TaskService} from 'app/service/task.service';

/**
 * Component to manage task creation
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './task-create.component.html',
})
export class TaskCreateComponent implements OnInit {
  /**
   * The form
   */
  public form!: TaskCreateForm;

  /**
   * Constructor
   *
   * @param taskService The task service
   * @param router The router service
   * @param routerService The router service
   * @param alertService The alert service
   */
  public constructor(
    private taskService: TaskService,
    private router: Router,
    private routerService: ActivatedRoute,
    private alertService: AlertService,
  ) {
  }

  /**
   * OnInit
   */
  public ngOnInit(): void {
    this.routerService.params.subscribe(params => {
      if (params['storyId']) {
        this.form = new TaskCreateForm(params['storyId']);
      } else {
        throw Error('No story ID specified in URL');
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
   * Executed on valid submit
   */
  private onValidSubmit(): void {
    this.taskService.save(this.form.task).subscribe(task => {
      this.alertService.add(new Alert(
        AlertLevel.SUCCESS,
        new AlertContent('task.alert.created', {taskName: task.name})
      ));

      this.router.navigate(['/story', 'edit', this.form.storyId]).then();
    }, (error: HttpErrorResponse) => {
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent(error.error.title)));
    });
  }
}
