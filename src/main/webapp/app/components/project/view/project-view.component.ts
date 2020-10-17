import {Component, OnInit} from '@angular/core';
import {Project} from 'app/entities/project.entity';
import {ProjectService} from 'app/service/project.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MessageService} from 'app/service/message.service';
import {TranslateService} from '@ngx-translate/core';
import {HttpErrorResponse} from '@angular/common/http';

/**
 * Component to manage project view
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './project-view.component.html',
})
// @ts-ignore
export class ProjectViewComponent implements OnInit {
  /**
   * The project
   */
  public project!: Project;

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param routerService The router service
   * @param router The router
   * @param messageService The message service
   * @param translateService The translation service
   */
  public constructor(
    private projectService: ProjectService,
    private routerService: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    private translateService: TranslateService
  ) {
  }

  /**
   * Called on init
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
   * Initialize the project
   *
   * @param id ID of project to initialize
   */
  private getProject(id: number): void {
    this.projectService.get(id).subscribe(project => (this.project = project),
      (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.router.navigate(['404']).then();
        }
      });
  }
}
