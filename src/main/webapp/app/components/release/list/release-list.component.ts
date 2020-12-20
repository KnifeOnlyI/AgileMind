import {Component, Input, OnInit} from '@angular/core';
import {ReleaseService} from 'app/service/release.service';
import {Release} from 'app/entities/release.entity';

/**
 * The component to manage the project's release list view/update
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-release-list',
  templateUrl: './release-list.component.html',
  styleUrls: ['./release-list.component.scss'],
})
export class ReleaseListComponent implements OnInit {
  /**
   * The project id
   */
  @Input()
  public projectId!: number;

  /**
   * TRUE if the component is initialized, FALSE otherwise
   */
  public initialized = false;

  /**
   * Releases
   */
  public releases = new Array<Release>();

  /**
   * Constructor
   *
   * @param releaseService The release service
   */
  public constructor(private releaseService: ReleaseService) {
  }

  /**
   * On init
   */
  public ngOnInit(): void {
    if (!this.projectId) {
      throw Error('No project ID specified in : @Input() projectId');
    }

    this.releaseService.getAllOfProject(this.projectId).subscribe((releases) => {
      releases.sort((a, b) => a.id! - b.id!).forEach(release => {
        this.releases.push(release)

        this.releaseService.updateAllCache(releases);
      });

      this.initialized = true;
    });
  }
}
