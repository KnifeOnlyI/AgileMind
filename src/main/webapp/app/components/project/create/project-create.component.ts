import {Component} from '@angular/core';
import {ProjectService} from 'app/service/project.service';
import {Router} from '@angular/router';
import {MessageService} from 'app/service/message.service';
import {TranslateService} from '@ngx-translate/core';
import {ProjectCreateForm} from 'app/components/project/create/project-create.form';
import {Message, MessageLevel} from 'app/shared/entity/message.entity';
import {HttpErrorResponse} from '@angular/common/http';
import {HTTPError} from 'app/shared/entity/error.entity';

/**
 * Component to manage project creation
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './project-create.component.html',
})
export class ProjectCreateComponent {
  public projectForm = new ProjectCreateForm();

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param router The router
   * @param messageService The message service
   * @param translateService The translation service
   */
  public constructor(
    private projectService: ProjectService,
    private router: Router,
    private messageService: MessageService,
    private translateService: TranslateService
  ) {
  }

  /**
   * Submit the project
   */
  public onSubmit(): void {
    this.projectForm.isValid ? this.onValidSubmit() : this.onInvalidSubmit();
  }

  /**
   * Executed on valid submit
   */
  private onValidSubmit(): void {
    this.projectService.save(this.projectForm.project).subscribe(project => {
      this.messageService.addMessage(new Message(
        this.translateService.instant('project.message.created', {projectName: project.name}),
        MessageLevel.SUCCESS
      ));

      this.router.navigate(['/project']).then();
    }, (error: HttpErrorResponse) => {
      this.onInvalidReponse(error.error);
    });
  }

  /**
   * Executed on invalid submit
   */
  private onInvalidSubmit(): void {
    this.messageService.addMessage(new Message(
      this.translateService.instant('project.error.form.invalid'),
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
}
