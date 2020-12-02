import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {JhiLanguageService} from 'ng-jhipster';
import {SessionStorageService} from 'ngx-webstorage';
import {Observable, of, ReplaySubject} from 'rxjs';
import {catchError, shareReplay, tap} from 'rxjs/operators';
import {StateStorageService} from 'app/core/auth/state-storage.service';

import {SERVER_API_URL} from 'app/app.constants';
import {Account} from 'app/core/user/account.model';

@Injectable({providedIn: 'root'})
export class AccountService {
  private userIdentity: Account | null = null;
  private authenticationState = new ReplaySubject<Account | null>(1);
  private accountCache$?: Observable<Account | null>;

  public constructor(
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private http: HttpClient,
    private stateStorageService: StateStorageService,
    private router: Router
  ) {
  }

  public save(account: Account): Observable<{}> {
    return this.http.post(SERVER_API_URL + 'api/account', account);
  }

  public authenticate(identity: Account | null): void {
    this.userIdentity = identity;
    this.authenticationState.next(this.userIdentity);
  }

  public hasAnyAuthority(authorities: string[] | string): boolean {
    if (!this.userIdentity || !this.userIdentity.authorities) {
      return false;
    }
    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }
    return this.userIdentity.authorities.some((authority: string) => authorities.includes(authority));
  }

  /**
   * Check if the logged user is admin
   *
   * @return TRUE if the logged user is admin, FALSE if user is not logged or not admin
   */
  public isAdmin(): boolean {
    return this.hasAnyAuthority('ROLE_ADMIN');
  }

  public identity(force?: boolean): Observable<Account | null> {
    if (!this.accountCache$ || force || !this.isAuthenticated()) {
      this.accountCache$ = this.fetch().pipe(
        catchError(() => {
          return of(null);
        }),
        tap((account: Account | null) => {
          this.authenticate(account);

          // After retrieve the account info, the language will be changed to
          // the user's preferred language configured in the account setting
          if (account && account.langKey) {
            const langKey = this.sessionStorage.retrieve('locale') || account.langKey;
            this.languageService.changeLanguage(langKey);
          }

          if (account) {
            this.navigateToStoredUrl();
          }
        }),
        shareReplay()
      );
    }
    return this.accountCache$;
  }

  public isAuthenticated(): boolean {
    return this.userIdentity !== null;
  }

  public getAuthenticationState(): Observable<Account | null> {
    return this.authenticationState.asObservable();
  }

  public getImageUrl(): string {
    return this.userIdentity ? this.userIdentity.imageUrl : '';
  }

  /**
   * Get the login of the logged user
   *
   * @return The login of the logged user or empty string if no user's logged
   */
  public getLogin(): string {
    return this.userIdentity ? this.userIdentity.login : '';
  }

  private fetch(): Observable<Account> {
    return this.http.get<Account>(SERVER_API_URL + 'api/account');
  }

  private navigateToStoredUrl(): void {
    // previousState can be set in the authExpiredInterceptor and in the userRouteAccessService
    // if login is successful, go to stored previousState and clear previousState
    const previousUrl = this.stateStorageService.getUrl();
    if (previousUrl) {
      this.stateStorageService.clearUrl();
      this.router.navigateByUrl(previousUrl);
    }
  }
}
