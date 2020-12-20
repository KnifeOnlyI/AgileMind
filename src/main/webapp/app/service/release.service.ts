import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of, Subscription} from 'rxjs';
import {SERVER_API_URL} from '../app.constants';
import {Release} from '../entities/release.entity';
import {Cache} from '../shared/entity/cache';
import {ProjectService} from 'app/service/project.service';
import {Project} from 'app/entities/project.entity';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class ReleaseService {
  public static BASE_URL = `${SERVER_API_URL}api/release`;

  /**
   * The cache for releases
   */
  private cache = new Cache<Release>();

  /**
   * Constructeur
   *
   * @param http HTTP service
   */
  public constructor(private http: HttpClient) {
  }

  /**
   * Get the release with specified ID
   *
   * @param id The ID of release to get
   * @param useCache TRUE to use the cache, FALSE otherwise
   *
   * @return HTTP response (Observable)
   */
  public get(id: number, useCache = true): Observable<Release> {
    let observable;
    let cached;

    if (useCache) {
      cached = this.cache.get(id);
    }

    if (cached) {
      observable = of(cached);
    } else {
      observable = this.http.get<Release>(`${ReleaseService.BASE_URL}/${id}`);

      observable.subscribe(release => this.updateCache(release));
    }

    return observable;
  }

  /**
   * Get all releases of the specified project
   *
   * @param projectId The project to check
   * @param useCache TRUE to use the cache, FALSE otherwise
   *
   * @return HTTP response (Observable)
   */
  public getAllOfProject(projectId: number, useCache = true): Observable<Array<Release>> {
    const cached = new Array<Release>();

    if (useCache) {
      this.cache.getAll().forEach((release) => {

        // FIXME: Here, projectId is considered like a 'string', so cannot be equals to release.project...
        if (release && release.project + '' === projectId + '') {
          cached.push(release);
        }
      });
    }

    return (cached && cached.length > 0) ?
      of(cached) :
      this.http.get<Array<Release>>(`${ProjectService.BASE_URL}/${projectId}/release`);
  }

  /**
   * Save the specified release
   *
   * @param release The release to save
   */
  public save(release: Release): Observable<Project> {
    let observable: Observable<Release>;

    if (!release.id) {
      observable = this.http.post(`${ReleaseService.BASE_URL}`, release);
    } else {
      observable = this.http.put(`${ReleaseService.BASE_URL}`, release);
    }

    return observable;
  }

  /**
   * Delete the release with the specified id
   *
   * @param id the ID of release to delete
   *
   * @return HTTP response
   */
  public delete(id: number): Observable<Subscription> {
    return of(this.http.delete<any>(`${ReleaseService.BASE_URL}/${id}`).subscribe(() => this.cache.set(id, undefined)));
  }

  /**
   * Update the cache with the specified release
   *
   * @param release The release
   */
  public updateCache(release: Release): void {
    if (!release || !release.id) {
      throw Error('Invalid release for cache');
    }

    this.cache.set(release.id, release);
  }

  /**
   * Update the cache with the specified release
   *
   * @param releases The releases
   */
  public updateAllCache(releases: Array<Release>): void {
    if (!releases) {
      throw Error('Invalid release for cache');
    }

    releases.forEach((release) => {
      this.updateCache(release);
    });
  }
}
