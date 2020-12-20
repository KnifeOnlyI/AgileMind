import {Route} from '@angular/router';
import {ConnectedRouteAccessService} from 'app/core/auth/connected-access-service';
import {ReleaseUpdateComponent} from 'app/components/release/update/release-update.component';
import {ReleaseCreateComponent} from 'app/components/release/create/release-create.component';

export const RELEASE_ROUTE: Route[] = [
  {
    path: 'project/:projectId/release/new',
    component: ReleaseCreateComponent,
    data: {
      pageTitle: 'release.title.new',
    },
    canActivate: [ConnectedRouteAccessService]
  },
  {
    path: 'release/edit/:id',
    component: ReleaseUpdateComponent,
    data: {
      pageTitle: 'release.title.edit',
    },
    canActivate: [ConnectedRouteAccessService]
  },
];
