import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {StoryStatus} from '../entities/story-status.entity';
import {StoryStatusConstants} from 'app/constants/story-status.constants';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class StoryStatusService {
  /**
   * The cache for story status
   */
  private storyStatusCache = new Array<StoryStatus>(
    new StoryStatus(StoryStatusConstants.ID.TODO, 'storyStatus.key.todo'),
    new StoryStatus(StoryStatusConstants.ID.IN_PROGRESS, 'storyStatus.key.inProgress'),
    new StoryStatus(StoryStatusConstants.ID.DONE, 'storyStatus.key.done'),
  );

  /**
   * Constructeur
   *
   * @param http HTTP service
   */
  public constructor(private http: HttpClient) {
  }

  /**
   * Get the story status with specified ID
   *
   * @param id The ID of story status to get
   *
   * @return HTTP response (Observable)
   */
  public get(id: number): Observable<StoryStatus> {
    if (id < 1 || id > 3) {
      throw Error('Invalid story status');
    }

    return of(this.storyStatusCache[id - 1]);
  }

  /**
   * Get all story status from database and put its in cache or directly from cache if its already in cache
   *
   * @return HTTP response (Observable)
   */
  public getAll(): Observable<Array<StoryStatus>> {
    return of(this.storyStatusCache);
  }
}
