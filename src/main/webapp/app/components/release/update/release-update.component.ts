import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
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
import {Project} from 'app/entities/project.entity';
import {ReleaseService} from 'app/service/release.service';
import {ReleaseUpdateForm} from 'app/components/release/update/release-update.form';

/**
 * Component to manage release update
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './release-update.component.html',
})
export class ReleaseUpdateComponent implements OnInit {
  /**
   * The project form data
   */
  public form!: ReleaseUpdateForm;

  /**
   * TRUE if the component is initialized, FALSE otherwise
   */
  public initialized = false;

  /**
   * Constructor
   *
   * @param releaseService The release service
   * @param router The router
   * @param routerService The router service
   * @param messageService The message service
   * @param userService The user service
   * @param modalService The modal service
   */
  public constructor(
    private releaseService: ReleaseService,
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
        this.getRelease(params['id']);
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
    this.releaseService.delete(this.form.id).subscribe(() => {
      this.messageService.add(new Alert(AlertLevel.SUCCESS, new AlertContent('release.alert.deleted')));

      this.router.navigate(['project', 'edit', this.form.project]).then();
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
    this.releaseService.save(this.form.release).subscribe(release => {
      this.messageService.add(new Alert(AlertLevel.SUCCESS, new AlertContent('release.alert.updated')));

      this.releaseService.updateCache(release);

      this.router.navigate(['project', 'edit', this.form.project]).then();
    }, (error: HttpErrorResponse) => {
      this.messageService.add(new Alert(AlertLevel.ERROR, new AlertContent(error.error.title)));
    });
  }

  /**
   * Executed on invalid submit
   */
  private onInvalidSubmit(): void {
    this.messageService.add(new Alert(AlertLevel.ERROR, new AlertContent('release.form.field.error.invalid')));
  }

  /**
   * Initialize the release
   *
   * @param id ID of release to initialize
   */
  private getRelease(id: number): void {
    this.releaseService.get(id).subscribe(release => this.onSuccess(release), error => this.onError(error));
  }

  /**
   * Executed on http success
   *
   * @param body The body
   */
  private onSuccess(body: Project): void {
    this.form = new ReleaseUpdateForm(body);

    this.initialized = true;
  }

  /**
   * Executed on http error
   *
   * @param error The error
   */
  private onError(error: HttpErrorResponse): void {
    switch (error.status) {
      case 404:
        this.messageService.add(new Alert(
          AlertLevel.ERROR,
          new AlertContent(error.error.title ? error.error.title : error.error.message))
        );
        break;
    }

    this.router.navigate(['/']).then();
  }
}
