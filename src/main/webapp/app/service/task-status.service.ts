import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {TaskStatus} from '../entities/task-status.entity';
import {TaskStatusConstants} from 'app/constants/task-status.constants';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class TaskStatusService {
  /**
   * The cache for task status
   */
  private taskStatusCache = new Array<TaskStatus>(
    new TaskStatus(TaskStatusConstants.ID.TODO, 'taskStatus.key.todo'),
    new TaskStatus(TaskStatusConstants.ID.IN_PROGRESS, 'taskStatus.key.inProgress'),
    new TaskStatus(TaskStatusConstants.ID.DONE, 'taskStatus.key.done'),
  );

  /**
   * Constructeur
   *
   * @param http HTTP service
   */
  public constructor(private http: HttpClient) {
  }

  /**
   * Get the task status with specified ID
   *
   * @param id The ID of task status to get
   *
   * @return HTTP response (Observable)
   */
  public get(id: number): Observable<TaskStatus> {
    if (id < 1 || id > 3) {
      throw Error('Invalid task status');
    }

    return of(this.taskStatusCache[id - 1]);
  }

  /**
   * Get all task status from database and put its in cache or directly from cache if its already in cache
   *
   * @return HTTP response (Observable)
   */
  public getAll(): Observable<Array<TaskStatus>> {
    return of(this.taskStatusCache);
  }
}
