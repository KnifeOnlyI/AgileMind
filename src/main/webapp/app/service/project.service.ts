import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Project} from '../entities/project.entity';
import {SERVER_API_URL} from '../app.constants';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class ProjectService {
  public static BASE_URL = `${SERVER_API_URL}api/project`;

  /**
   * Constructeur
   *
   * @param http HTTP service
   */
  public constructor(private http: HttpClient) {
  }

  /**
   * Get project with the specified id
   *
   * @return HTTP response (Observable)
   */
  public get(id: number): Observable<Project> {
    return this.http.get<Project>(`${ProjectService.BASE_URL}/${id}`);
  }

  /**
   * Check if the specified user is
   *
   * @return HTTP response (Observable)
   */
  public loggedUserIsAssigned(id: number): Observable<boolean> {
    return this.http.get<boolean>(`${ProjectService.BASE_URL}/${id}/logged-user-is-assigned`);
  }

  /**
   * Save the specified project
   *
   * @param project The project to save
   */
  public save(project: Project): Observable<Project> {
    if (!project.id) {
      return this.http.post(`${ProjectService.BASE_URL}`, project);
    } else {
      return this.http.put(`${ProjectService.BASE_URL}`, project);
    }
  }

  /**
   * Get all projects
   *
   * @return HTTP response (Observable)
   */
  public getAll(): Observable<Array<Project>> {
    return this.http.get<Array<Project>>(ProjectService.BASE_URL);
  }

  /**
   * Delete the project with the specified id
   *
   * @param id the ID of project to delete
   *
   * @return HTTP response
   */
  public delete(id: number): Observable<any> {
    return this.http.delete<any>(`${ProjectService.BASE_URL}/${id}`);
  }
}
