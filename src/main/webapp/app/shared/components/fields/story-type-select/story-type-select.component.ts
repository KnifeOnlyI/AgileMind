import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {StoryTypeService} from 'app/service/story-type.service';
import {StoryType} from 'app/entities/story-type.entity';

/**
 * Component to manage story type selection
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-story-type-select',
  template: `
    <div *ngIf="control && types" class="form-group">
      <label for="typeId"><span jhiTranslate="story.form.field.type"></span></label>
      <select [formControl]="control" class="form-control" id="typeId" name="typeId">
        <option *ngFor="let type of types" [jhiTranslate]="type.key!" [value]="type.id"></option>
      </select>
    </div>`
})
// @ts-ignore
export class StoryTypeSelectComponent implements OnInit {
  /**
   * The form control on the field
   */
  @Input()
  public control!: FormControl;

  /**
   * The types
   */
  public types?: Array<StoryType>;

  /**
   * Constructor
   *
   * @param storyTypeService The story type service
   */
  public constructor(private storyTypeService: StoryTypeService) {
  }

  /**
   * OnInit
   */
  public ngOnInit(): void {
    if (!this.control) {
      throw new Error('this.control CANNOT be undefined');
    }

    this.storyTypeService.getAll().subscribe((types) => {
      this.types = types;
    });
  }
}
