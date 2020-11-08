import {Route} from '@angular/router';
import {StoryCreateComponent} from './create/story-create.component';
import {StoryUpdateComponent} from 'app/components/story/update/story-update.component';
import {UserRouteAccessService} from 'app/core/auth/user-route-access-service';

export const STORY_ROUTE: Route[] = [
  {
    path: 'project/:projectId/story/new',
    component: StoryCreateComponent,
    data: {
      pageTitle: 'story.title.new',
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'story/edit/:id',
    component: StoryUpdateComponent,
    data: {
      pageTitle: 'story.title.edit',
    },
    canActivate: [UserRouteAccessService]
  },
];
