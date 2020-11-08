import {Component} from '@angular/core';
import {ProjectService} from 'app/service/project.service';
import {Router} from '@angular/router';
import {ProjectCreateForm} from 'app/components/project/create/project-create.form';
import {HttpErrorResponse} from '@angular/common/http';
import {AlertService} from 'app/service/alert.service';
import {Alert, AlertContent, AlertLevel} from 'app/shared/entity/alert.entity';

/**
 * Component to manage project creation
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './project-create.component.html',
})
export class ProjectCreateComponent {
  public form = new ProjectCreateForm();

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param router The router
   * @param alertService The alert service
   */
  public constructor(
    private projectService: ProjectService,
    private router: Router,
    private alertService: AlertService
  ) {
  }

  /**
   * Submit the project
   */
  public onSubmit(): void {
    this.form.isValid ?
      this.onValidSubmit() :
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent('project.error.form.invalid')));
  }

  /**
   * Executed on valid submit
   */
  private onValidSubmit(): void {
    this.projectService.save(this.form.project).subscribe(project => {
      this.alertService.add(new Alert(
        AlertLevel.SUCCESS,
        new AlertContent('project.message.created', {projectName: project.name})
      ));

      this.router.navigate(['/project']).then();
    }, (error: HttpErrorResponse) => {
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent(error.error.title)));
    });
  }
}
