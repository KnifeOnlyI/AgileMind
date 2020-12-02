import {Component, OnInit} from '@angular/core';
import {Project} from 'app/entities/project.entity';
import {ProjectService} from 'app/service/project.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {Story} from 'app/entities/story.entity';
import {StoryService} from 'app/service/story.service';
import {AlertService} from 'app/service/alert.service';
import {StoryStatusConstants} from 'app/constants/story-status.constants';
import {AccountService} from 'app/core/auth/account.service';
import {UserService} from 'app/core/user/user.service';

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
   * Determine if the logged user can view project settings button
   */
  public showProjectSettingsBtn = false;

  /**
   * Constructor
   *
   * @param projectService The project service
   * @param storyService The story service
   * @param routerService The router service
   * @param router The router
   * @param alertService The alert service
   * @param accountService The account service
   * @param userService The user service
   */
  public constructor(
    private projectService: ProjectService,
    private storyService: StoryService,
    private routerService: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
    private accountService: AccountService,
    private userService: UserService,
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
   * Récupère les styles à appliquer sur la ligne affichant la story spécifiée
   *
   * @param story La story à afficher
   *
   * @return Les styles à appliquer sur la ligne affichant la story spécifiée
   */
  public getStyles(story: Story): { [klass: string]: any; } {
    const style = {
      'cursor': 'pointer',
      'border-bottom': '1px solid',
    };

    switch (story.typeId) {
      case 1:
        style['border-bottom'] += '#28a745';
        break;
      case 2:
        style['border-bottom'] += '#dc3545';
        break;
      case 3:
        style['border-bottom'] += '#a6a6a6';
        break;
      default:
        throw Error(`Not managed story type : ${story.typeId}`);
    }

    return style;
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

      // Determine if the user is admin or is project administrator
      this.userService.find(this.accountService.getLogin()).subscribe((user) => {
        const isAdmin = this.accountService.isAdmin();
        const isProjectAdmin = this.project.adminUserIdList?.findIndex(userId => userId === user.id) !== -1;

        this.showProjectSettingsBtn = isAdmin || isProjectAdmin;
      });
    }, (error: HttpErrorResponse) => {
      if (error.status === 404) {
        this.router.navigate(['404']).then();
      }
    });
  }
}
