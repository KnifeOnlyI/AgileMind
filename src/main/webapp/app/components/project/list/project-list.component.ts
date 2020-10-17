import {Component, OnInit} from '@angular/core';
import {Project} from 'app/entities/project.entity';
import {ProjectService} from 'app/service/project.service';
import {MessageService} from 'app/service/message.service';
import {TranslateService} from '@ngx-translate/core';

/**
 * Component to manage list displaying
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './project-list.component.html',
})
// @ts-ignore
export class ProjectListComponent implements OnInit {
  /**
   * The project list
   */
  public projects!: Array<Project>;

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param messageService The message service
   * @param translateService The translate service
   */
  public constructor(
    private projectService: ProjectService,
    private messageService: MessageService,
    private translateService: TranslateService
  ) {
  }

  /**
   * Called on init
   */
  public ngOnInit(): void {
    // Fetch all projects from back API
    this.projectService.getAll().subscribe(projects => {
      this.projects = projects;
    });
  }
}
