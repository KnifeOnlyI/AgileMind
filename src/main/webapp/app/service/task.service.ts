import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from '../app.constants';
import {Task} from '../entities/task.entity';
import {StoryService} from './story.service';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class TaskService {
  public static BASE_URL = `${SERVER_API_URL}api/task`;

  /**
   * Constructeur
   *
   * @param http HTTP service
   */
  public constructor(private http: HttpClient) {
  }

  /**
   * Get the task with the specified ID
   *
   * @param id The task ID
   *
   * @return HTTP response (Observable)
   */
  public get(id: number): Observable<Task> {
    return this.http.get<Task>(`${TaskService.BASE_URL}/${id}`);
  }

  /**
   * Get all stories from the specified project id
   *
   * @param storyId The project ID
   *
   * @return HTTP response (Observable)
   */
  public getAllFromStory(storyId: number): Observable<Array<Task>> {
    return this.http.get<Array<Task>>(`${StoryService.BASE_URL}/${storyId}/task`);
  }

  /**
   * Save the specified task
   *
   * @param task The task to save
   */
  public save(task: Task): Observable<Task> {
    if (!task.id) {
      return this.http.post(`${TaskService.BASE_URL}`, task);
    } else {
      return this.http.put(`${TaskService.BASE_URL}`, task);
    }
  }

  /**
   * Delete the task with the specified id
   *
   * @param id the ID of task to delete
   *
   * @return HTTP response
   */
  public delete(id: number): Observable<any> {
    return this.http.delete<any>(`${TaskService.BASE_URL}/${id}`);
  }
}
