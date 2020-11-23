import {Component, OnInit} from '@angular/core';
import {ProjectService} from 'app/service/project.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ProjectUpdateForm} from 'app/components/project/update/project-update.form';
import {HttpErrorResponse} from '@angular/common/http';
import {UserService} from 'app/core/user/user.service';
import {
  ConfirmDialogComponent,
  ConfirmDialogType,
  ExitState
} from 'app/shared/components/dialogs/confirm/confirm-dialog.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AlertService} from 'app/service/alert.service';
import {Alert, AlertContent, AlertLevel} from 'app/shared/entity/alert.entity';

/**
 * Component to manage project update
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  public form!: ProjectUpdateForm;

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param router The router
   * @param routerService The router service
   * @param messageService The message service
   * @param userService The user service
   * @param modalService The modal service
   */
  public constructor(
    private projectService: ProjectService,
    private router: Router,
    private routerService: ActivatedRoute,
    private messageService: AlertService,
    private userService: UserService,
    private modalService: NgbModal,
  ) {
  }

  /**
   * On init
   */
  public ngOnInit(): void {
    this.routerService.params.subscribe(params => {
      if (params['id']) {
        this.getProject(params['id']);
      } else {
        throw Error('No project ID specified in URL');
      }
    });
  }

  /**
   * Submit the project
   */
  public onSubmit(): void {
    this.form.isValid ? this.onValidSubmit() : this.onInvalidSubmit();
  }

  /**
   * Delete the project
   */
  public delete(): void {
    this.projectService.delete(this.form.id).subscribe(() => {
      this.messageService.add(new Alert(AlertLevel.SUCCESS, new AlertContent('project.alert.deleted')));

      this.router.navigate(['/']).then();
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
    this.projectService.save(this.form.project).subscribe(project => {
      this.messageService.add(new Alert(AlertLevel.SUCCESS, new AlertContent('project.alert.updated')));

      this.router.navigate(['/project', project.id]).then();
    }, (error: HttpErrorResponse) => {
      this.messageService.add(new Alert(AlertLevel.ERROR, new AlertContent(error.error.title)));
    });
  }

  /**
   * Executed on invalid submit
   */
  private onInvalidSubmit(): void {
    this.messageService.add(new Alert(AlertLevel.ERROR, new AlertContent('project.form.field.error.invalid')));
  }

  /**
   * Initialize the project
   *
   * @param id ID of project to initialize
   */
  private getProject(id: number): void {
    this.projectService.get(id).subscribe(project => (this.form = new ProjectUpdateForm(project)),
      (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.router.navigate(['404']).then();
        }
      });
  }
}
