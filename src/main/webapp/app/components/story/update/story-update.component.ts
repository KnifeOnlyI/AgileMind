import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {StoryService} from 'app/service/story.service';
import {StoryUpdateForm} from './story-update.form';
import {AlertService} from 'app/service/alert.service';
import {Alert, AlertContent, AlertLevel} from 'app/shared/entity/alert.entity';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {
  ConfirmDialogComponent,
  ConfirmDialogType,
  ExitState
} from 'app/shared/components/dialogs/confirm/confirm-dialog.component';

/**
 * Component to manage story update
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './story-update.component.html',
})
export class StoryUpdateComponent implements OnInit {
  /**
   * The form
   */
  public form!: StoryUpdateForm;

  /**
   * Constructor
   *
   * @param storyService The story service
   * @param router The router service
   * @param routerService The router service
   * @param alertService The alert service
   * @param modalService The modal service
   */
  public constructor(
    private storyService: StoryService,
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
        this.getStory(params['id']);
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
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent('story.error.form.invalid')));
  }

  /**
   * Delete the story
   */
  public delete(): void {
    this.storyService.delete(this.form.id).subscribe(() => {
      this.alertService.add(new Alert(
        AlertLevel.SUCCESS,
        new AlertContent('story.message.deleted', {storyName: this.form.name})
      ));

      this.router.navigate(['/project/', this.form.projectId]).then();
    });
  }

  /**
   * Executed on valid submit
   */
  private onValidSubmit(): void {
    this.storyService.save(this.form.story).subscribe(story => {
      this.alertService.add(new Alert(
        AlertLevel.SUCCESS,
        new AlertContent('story.message.updated', {storyName: story.name})
      ));

      this.router.navigate(['/project/', this.form.projectId]).then();
    }, (error: HttpErrorResponse) => {
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent(error.error.title)));
    });
  }

  /**
   * Get the story with the specified ID
   *
   * @param id The story id
   */
  private getStory(id: number): void {
    this.storyService.get(id).subscribe(project => (this.form = new StoryUpdateForm(project)),
      (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.router.navigate(['404']).then();
        }
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
}
