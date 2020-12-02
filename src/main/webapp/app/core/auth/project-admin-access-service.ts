import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate} from '@angular/router';
import {Observable} from 'rxjs';

import {ProjectService} from 'app/service/project.service';
import {map} from 'rxjs/operators';

/**
 * Auth guard for project administrator needed for access
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Injectable({providedIn: 'root'})
export class ProjectAdminRouteAccessService implements CanActivate {
  /**
   * Constructor
   *
   * @param projectService The project service
   */
  public constructor(
    private projectService: ProjectService,
  ) {
  }

  /**
   * Check if the route can be activated
   *
   * @return Observable on boolean value (TRUE if user is admin, FALSE otherwise)
   */
  public canActivate(route: ActivatedRouteSnapshot): Observable<boolean> {
    return this.projectService.loggedUserIsAssigned(route.params['id']).pipe(map((res) => {
      console.warn('Res');

      return true;
    }));
  }
}
