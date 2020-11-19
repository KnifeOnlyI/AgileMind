import {Route} from '@angular/router';
import {TaskCreateComponent} from './create/task-create.component';
import {UserRouteAccessService} from 'app/core/auth/user-route-access-service';
import {TaskUpdateComponent} from 'app/components/task/update/task-update.component';

export const TASK_ROUTE: Route[] = [
  {
    path: 'story/:storyId/task/new',
    component: TaskCreateComponent,
    data: {
      pageTitle: 'task.title.new',
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task/edit/:id',
    component: TaskUpdateComponent,
    data: {
      pageTitle: 'task.title.edit',
    },
    canActivate: [UserRouteAccessService]
  },
];
