import {Component, Input, OnInit} from '@angular/core';
import {StoryStatus} from 'app/entities/story-status.entity';
import {StoryStatusService} from 'app/service/story-status.service';

/**
 * Component to manage story status displaying
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-story-status',
  template: `
    <span *ngIf="status" [ngStyle]="{'color': color}" jhiTranslate="{{status.key}}"></span>`
})
// @ts-ignore
export class StoryStatusComponent implements OnInit {
  @Input()
  public id!: number;

  /**
   * The story status
   */
  public status?: StoryStatus;

  /**
   * The class
   */
  public color = 'gray';

  /**
   * Constructor
   *
   * @param storyStatusService The story status service
   */
  public constructor(private storyStatusService: StoryStatusService) {
  }

  /**
   * OnInit
   */
  public ngOnInit(): void {
    if (!this.id) {
      throw new Error('@Input() id CANNOT be undefined');
    }

    this.storyStatusService.get(this.id).subscribe((storyStatus) => {
      this.status = storyStatus;

      console.log('StoryStatus', this.id);

      if (this.status.id === 2) {
        this.color = 'blue';
      } else if (this.status.id === 3) {
        this.color = 'green';
      }
    });
  }
}
