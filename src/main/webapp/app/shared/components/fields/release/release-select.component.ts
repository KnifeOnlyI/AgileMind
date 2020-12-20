import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Release} from 'app/entities/release.entity';
import {ReleaseService} from 'app/service/release.service';

/**
 * Component to manage release selection
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-release-select',
  template: `
    <label *ngIf="showLabel" for="release"><span jhiTranslate="story.form.field.release"></span></label>
    <div *ngIf="releases; else NotInitialized" class="form-group">
      <select [formControl]="control" class="form-control" id="release" name="release">
        <option [value]="null">---</option>
        <option *ngFor="let release of releases"
                [value]="release.id"
                [title]="release.description">
          {{release.name}}
        </option>
      </select>
    </div>

    <ng-template #NotInitialized>
      <br *ngIf="showLabel">
      <fa-icon [spin]="true" icon="spinner"></fa-icon>&nbsp;
      <span jhiTranslate="releases.loading"></span>
    </ng-template>`
})
// @ts-ignore
export class ReleaseSelectComponent implements OnInit {
  /**
   * The form control on the field
   */
  @Input()
  public control!: FormControl;

  /**
   * The project id
   */
  @Input()
  public projectId!: number;

  /**
   * TRUE to show the label, FALSE otherwise
   */
  @Input()
  public showLabel = true;

  /**
   * The types
   */
  public releases?: Array<Release>;

  /**
   * Constructor
   *
   * @param releaseService The release service
   */
  public constructor(private releaseService: ReleaseService) {
  }

  /**
   * OnInit
   */
  public ngOnInit(): void {
    if (!this.control) {
      throw new Error('this.control CANNOT be undefined');
    }

    if (!this.projectId) {
      throw new Error('this.projectId CANNOT be undefined');
    }

    this.releaseService.getAllOfProject(this.projectId).subscribe(releases => {
      this.releases = releases;

      this.releases.sort((a, b) => a.id! - b.id!);

      this.releaseService.updateAllCache(releases);
    });
  }
}
