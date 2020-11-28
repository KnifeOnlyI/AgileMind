import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {StoryType} from '../entities/story-type.entity';
import {StoryTypeConstants} from 'app/constants/story-type.constants';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class StoryTypeService {
  /**
   * The cache for story type
   */
  private cache = new Array<StoryType>(
    new StoryType(StoryTypeConstants.ID.FUNCTIONALITY, 'storyType.key.functionality'),
    new StoryType(StoryTypeConstants.ID.BUG, 'storyType.key.bug'),
    new StoryType(StoryTypeConstants.ID.TECHNICAL, 'storyType.key.technical'),
  );

  /**
   * Constructeur
   *
   * @param http HTTP service
   */
  public constructor(private http: HttpClient) {
  }

  /**
   * Get the story type with specified ID
   *
   * @param id The ID of story type to get
   *
   * @return HTTP response (Observable)
   */
  public get(id: number): Observable<StoryType> {
    if (id < 1 || id > 3) {
      throw Error('Invalid story type');
    }

    return of(this.cache[id - 1]);
  }

  /**
   * Get all story type from database and put its in cache or directly from cache if its already in cache
   *
   * @return HTTP response (Observable)
   */
  public getAll(): Observable<Array<StoryType>> {
    return of(this.cache);
  }
}
