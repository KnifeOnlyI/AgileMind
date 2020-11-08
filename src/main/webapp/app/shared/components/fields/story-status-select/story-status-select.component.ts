import {Component, Input, OnInit} from '@angular/core';
import {StoryStatus} from 'app/entities/story-status.entity';
import {StoryStatusService} from 'app/service/story-status.service';
import {FormControl} from '@angular/forms';

/**
 * Component to manage story status selection
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-story-status-select',
  template: `
    <div *ngIf="control && storiesStatus" class="form-group">
      <label for="statusId"><span jhiTranslate="story.form.field.status"></span></label>
      <select [formControl]="control" class="form-control" id="statusId" name="statusId">
        <option *ngFor="let storyStatus of storiesStatus" [jhiTranslate]="storyStatus.key!"
                [value]="storyStatus.id"></option>
      </select>
    </div>`
})
// @ts-ignore
export class StoryStatusSelectComponent implements OnInit {
  /**
   * The form control on the field
   */
  @Input()
  public control!: FormControl;

  /**
   * The stories status
   */
  public storiesStatus?: Array<StoryStatus>;

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
    if (!this.control) {
      throw new Error('this.control CANNOT be undefined');
    }

    this.storyStatusService.getAll().subscribe((storiesStatus) => {
      this.storiesStatus = storiesStatus;
    });
  }
}
