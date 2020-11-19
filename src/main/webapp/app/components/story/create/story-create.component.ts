import {Component, OnInit} from '@angular/core';
import {ProjectService} from 'app/service/project.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {HttpErrorResponse} from '@angular/common/http';
import {StoryCreateForm} from './story-create.form';
import {StoryService} from 'app/service/story.service';
import {AlertService} from 'app/service/alert.service';
import {Alert, AlertContent, AlertLevel} from 'app/shared/entity/alert.entity';

/**
 * Component to manage story creation
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './story-create.component.html',
})
export class StoryCreateComponent implements OnInit {
  /**
   * The form
   */
  public form!: StoryCreateForm;

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param storyService The story service
   * @param router The router service
   * @param routerService The router service
   * @param alertService The alert service
   * @param translateService The translation service
   */
  public constructor(
    private projectService: ProjectService,
    private storyService: StoryService,
    private router: Router,
    private routerService: ActivatedRoute,
    private alertService: AlertService,
    private translateService: TranslateService,
  ) {
  }

  /**
   * OnInit
   */
  public ngOnInit(): void {
    this.routerService.params.subscribe(params => {
      if (params['projectId']) {
        this.form = new StoryCreateForm(params['projectId']);
      } else {
        throw Error('No project ID specified in URL');
      }
    });
  }

  /**
   * Submit the project
   */
  public onSubmit(): void {
    this.form.isValid ?
      this.onValidSubmit() :
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent('story.error.form.invalid')));
  }

  /**
   * Executed on valid submit
   */
  private onValidSubmit(): void {
    this.storyService.save(this.form.story).subscribe(() => {
      this.alertService.add(new Alert(AlertLevel.SUCCESS, new AlertContent('story.alert.created')));

      this.router.navigate(['/project/', this.form.projectId]).then();
    }, (error: HttpErrorResponse) => {
      this.alertService.add(new Alert(AlertLevel.ERROR, new AlertContent(error.error.title)));
    });
  }
}
