import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from '../app.constants';
import {Story} from '../entities/story.entity';
import {ProjectService} from 'app/service/project.service';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class StoryService {
  public static BASE_URL = `${SERVER_API_URL}api/story`;

  /**
   * Constructeur
   *
   * @param http HTTP service
   */
  public constructor(private http: HttpClient) {
  }

  /**
   * Get the story with the specified ID
   *
   * @param id The story ID
   *
   * @return HTTP response (Observable)
   */
  public get(id: number): Observable<Story> {
    return this.http.get<Story>(`${StoryService.BASE_URL}/${id}`);
  }

  /**
   * Get all stories from the specified project id
   *
   * @param projectId The project ID
   *
   * @return HTTP response (Observable)
   */
  public getAllFromProject(projectId: number): Observable<Array<Story>> {
    return this.http.get<Array<Story>>(`${ProjectService.BASE_URL}/${projectId}/story`);
  }

  /**
   * Save the specified story
   *
   * @param story The story to save
   */
  public save(story: Story): Observable<Story> {
    if (!story.id) {
      return this.http.post(`${StoryService.BASE_URL}`, story);
    } else {
      return this.http.put(`${StoryService.BASE_URL}`, story);
    }
  }

  /**
   * Delete the story with the specified id
   *
   * @param id the ID of story to delete
   *
   * @return HTTP response
   */
  public delete(id: number): Observable<any> {
    return this.http.delete<any>(`${StoryService.BASE_URL}/${id}`);
  }
}
