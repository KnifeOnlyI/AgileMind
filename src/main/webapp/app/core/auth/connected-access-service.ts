import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {Observable} from 'rxjs';

import {AccountService} from 'app/core/auth/account.service';
import {map} from 'rxjs/operators';

/**
 * Auth guard for connected needed for access
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Injectable({providedIn: 'root'})
export class ConnectedRouteAccessService implements CanActivate {
  /**
   * Constructor
   *
   * @param router The router service
   * @param accountService The account service
   */
  public constructor(
    private router: Router,
    private accountService: AccountService,
  ) {
  }

  /**
   * Check if the route can be activated
   *
   * @return Observable on boolean value (TRUE if user is connected, FALSE otherwise)
   */
  public canActivate(): Observable<boolean> {
    return this.accountService.identity().pipe(map(account => {
      if (!account) {
        this.router.navigate(['404']).then();
      }

      return !!account;
    }));
  }
}
