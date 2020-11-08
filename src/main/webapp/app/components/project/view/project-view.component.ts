import {Component, OnInit} from '@angular/core';
import {Project} from 'app/entities/project.entity';
import {ProjectService} from 'app/service/project.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {Story} from 'app/entities/story.entity';
import {StoryService} from 'app/service/story.service';
import {AlertService} from 'app/service/alert.service';
import {StoryStatusConstants} from 'app/constants/story-status.constants';

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

  public stories = {
    'todo': new Array<Story>(),
    'inProgress': new Array<Story>(),
    'done': new Array<Story>(),
  };

  /**
   * The total number of stories (to do + in progress + done)
   */
  public totalNbStories = 0;

  /**
   * The possible display status to display
   */
  public currentDisplayStatus: 'todo' | 'inProgress' | 'done' = 'todo';

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param storyService The story service
   * @param routerService The router service
   * @param router The router
   * @param alertService The alert service
   */
  public constructor(
    private projectService: ProjectService,
    private storyService: StoryService,
    private routerService: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
  ) {
  }

  /**
   * Sort the specified stories by :
   *  1. Business value (DESC)
   *  2. ID (ASC)
   *
   *  @param stories The stories to sort
   */
  private static sortStories(stories: Array<Story>): void {
    stories.sort((a, b) => {
        let sort = (!b.businessValue ? 0 : b.businessValue) - (!a.businessValue ? 0 : a.businessValue);

        // If business values are equals, sort by ASC id
        if (sort === 0) {
          sort = (!a.id ? 0 : a.id) - (!b.id ? 0 : b.id);
        }

        return sort;
      }
    );
  }

  /**
   * Called on init
   */
  public ngOnInit(): void {
    this.routerService.params.subscribe(params => {
      if (!params['id']) {
        throw Error('No project ID specified in URL');
      }

      this.initProject(params['id']);
    });
  }

  /**
   * Set the current status to display
   *
   * @param status The status of stories to display
   */
  public setCurrentDiplayStatus(status: 'todo' | 'inProgress' | 'done'): void {
    this.currentDisplayStatus = status;
  }

  /**
   * Initialize the project
   *
   * @param id ID of project to initialize
   */
  private initProject(id: number): void {
    this.projectService.get(id).subscribe(project => {
      this.project = project;

      this.storyService.getAllFromProject(project.id!).subscribe((stories) => {
        stories.forEach((story) => {
          if (story.statusId === StoryStatusConstants.ID.TODO) {
            this.stories.todo.push(story);
          } else if (story.statusId === StoryStatusConstants.ID.IN_PROGRESS) {
            this.stories.inProgress.push(story);
          } else if (story.statusId === StoryStatusConstants.ID.DONE) {
            this.stories.done.push(story);
          } else {
            throw new Error(`Not managed story status id : ${story.statusId}`);
          }
        });

        ProjectViewComponent.sortStories(this.stories.todo);
        ProjectViewComponent.sortStories(this.stories.inProgress);
        ProjectViewComponent.sortStories(this.stories.done);

        // Calculate the total number of stories in the project
        this.totalNbStories = this.stories.todo.length + this.stories.inProgress.length + this.stories.done.length;
      });
    }, (error: HttpErrorResponse) => {
      if (error.status === 404) {
        this.router.navigate(['404']).then();
      }
    });
  }
}
