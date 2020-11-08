import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, of} from 'rxjs';

import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption, Pagination} from 'app/shared/util/request-util';
import {IUser, User} from './user.model';

interface UserCacheList {
  [key: number]: User;
}

@Injectable({providedIn: 'root'})
export class UserService {
  public resourceUrl = SERVER_API_URL + 'api/users';

  /**
   * User cache list
   */
  private readonly userCache: UserCacheList = {};

  public constructor(private http: HttpClient) {
  }

  public create(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrl, user);
  }

  public update(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(this.resourceUrl, user);
  }

  /**
   * Get the user with the specified ID
   *
   * @param id The ID of user to get
   */
  public get(id: number): Observable<User> {
    let $results;

    // If the user is already in cache, return it immediatly
    // Else, query and put it in cache
    if (this.userCache[id]) {
      $results = of(this.userCache[id]);
    } else {
      $results = this.http.get<User>(`${this.resourceUrl}/get-by-id/${id}`);

      // Put the user in cache
      $results.subscribe((user) => {
        this.userCache[user.id] = user;
      });
    }

    return $results;
  }

  public find(login: string): Observable<IUser> {
    return this.http.get<IUser>(`${this.resourceUrl}/${login}`);
  }

  public query(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  public delete(login: string): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }

  public authorities(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities');
  }
}
