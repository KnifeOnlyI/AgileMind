import {Route} from '@angular/router';
import {ProjectListComponent} from './list/project-list.component';
import {ProjectCreateComponent} from 'app/components/project/create/project-create.component';
import {AdminRouteAccessService} from 'app/core/auth/admin-access-service';
import {ProjectViewComponent} from 'app/components/project/view/project-view.component';
import {ProjectUpdateComponent} from 'app/components/project/update/project-update.component';
import {ConnectedRouteAccessService} from 'app/core/auth/connected-access-service';

export const PROJECT_ROUTE: Route[] = [
  {
    path: 'project',
    component: ProjectListComponent,
    data: {
      pageTitle: 'project.title.list',
    },
  },
  {
    path: 'project/new',
    component: ProjectCreateComponent,
    data: {
      pageTitle: 'project.title.new',
    },
    canActivate: [AdminRouteAccessService]
  },
  {
    path: 'project/:id',
    component: ProjectViewComponent,
    data: {
      pageTitle: 'project.title.view',
    },
    canActivate: [ConnectedRouteAccessService]
  },
  {
    path: 'project/edit/:id',
    component: ProjectUpdateComponent,
    data: {
      pageTitle: 'project.title.edit',
    },
    canActivate: [AdminRouteAccessService]
  },
];
