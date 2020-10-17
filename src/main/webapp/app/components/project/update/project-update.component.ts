import {Component, OnInit} from '@angular/core';
import {ProjectService} from 'app/service/project.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MessageService} from 'app/service/message.service';
import {TranslateService} from '@ngx-translate/core';
import {Message, MessageLevel} from 'app/shared/entity/message.entity';
import {ProjectUpdateForm} from 'app/components/project/update/project-update.form';
import {HttpErrorResponse} from '@angular/common/http';
import {UserService} from 'app/core/user/user.service';
import {IUser} from 'app/core/user/user.model';
import {HTTPError} from 'app/shared/entity/error.entity';

/**
 * Component to manage project update
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  public projectForm!: ProjectUpdateForm;

  /**
   * Users list
   */
  public users!: Array<IUser>;

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param router The router
   * @param routerService The router service
   * @param messageService The message service
   * @param translateService The translation service
   * @param userService The user service
   */
  public constructor(
    private projectService: ProjectService,
    private router: Router,
    private routerService: ActivatedRoute,
    private messageService: MessageService,
    private translateService: TranslateService,
    private userService: UserService,
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

    this.userService.query().subscribe((users) => {
      this.users = users.body ? users.body : new Array<IUser>();
    });
  }

  /**
   * Submit the project
   */
  public onSubmit(): void {
    this.projectForm.isValid ? this.onValidSubmit() : this.onInvalidSubmit();
  }

  /**
   * Delete the project
   */
  public delete(): void {
    this.projectService.delete(this.projectForm.projectId).subscribe(() => {
      this.messageService.addMessage(new Message(
        this.translateService.instant('project.message.deleted', {projectName: this.projectForm.name}),
        MessageLevel.SUCCESS
      ));
      this.router.navigate(['/project']).then();
    });
  }

  /**
   * Executed on valid submit
   */
  private onValidSubmit(): void {
    this.projectService.save(this.projectForm.project).subscribe(project => {
      this.messageService.addMessage(new Message(
        this.translateService.instant('project.message.updated', {projectName: project.name}),
        MessageLevel.SUCCESS
      ));

      this.router.navigate(['/project/', project.id]).then();
    }, (error: HttpErrorResponse) => {
      this.onInvalidReponse(error.error);
    });
  }

  /**
   * Executed on invalid submit
   */
  private onInvalidSubmit(): void {
    this.messageService.addMessage(new Message(
      this.translateService.instant('project.form.field.error.invalid'),
      MessageLevel.DANGER
    ));
  }

  /**
   * Executed on receive invalid HTTP response
   *
   * @param error The HTTP error
   */
  private onInvalidReponse(error: HTTPError): void {
    this.messageService.addMessage(new Message(this.translateService.instant(error.title), MessageLevel.DANGER));
  }

  /**
   * Initialize the project
   *
   * @param id ID of project to initialize
   */
  private getProject(id: number): void {
    this.projectService.get(id).subscribe(project => (this.projectForm = new ProjectUpdateForm(project)),
      (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.router.navigate(['404']).then();
        }
      });
  }
}
