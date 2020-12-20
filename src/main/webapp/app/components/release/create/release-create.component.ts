import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {UserService} from 'app/core/user/user.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AlertService} from 'app/service/alert.service';
import {Alert, AlertContent, AlertLevel} from 'app/shared/entity/alert.entity';
import {ReleaseService} from 'app/service/release.service';
import {ReleaseCreateForm} from 'app/components/release/create/release-create.form';

/**
 * Component to manage release creation
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './release-create.component.html',
})
export class ReleaseCreateComponent implements OnInit {
  /**
   * The project form data
   */
  public form!: ReleaseCreateForm;

  /**
   * Constructor
   *
   * @param releaseService The release service
   * @param router The router
   * @param routerService The router service
   * @param messageService The message service
   * @param userService The user service
   * @param modalService The modal service
   */
  public constructor(
    private releaseService: ReleaseService,
    private router: Router,
    private routerService: ActivatedRoute,
    private messageService: AlertService,
    private userService: UserService,
    private modalService: NgbModal,
  ) {
  }

  /**
   * On init
   */
  public ngOnInit(): void {
    this.routerService.params.subscribe(params => {
      if (params['projectId']) {
        this.form = new ReleaseCreateForm(params['projectId']);
      } else {
        throw Error('No project ID specified in URL');
      }
    });
  }

  /**
   * Submit the project
   */
  public onSubmit(): void {
    this.form.isValid ? this.onValidSubmit() : this.onInvalidSubmit();
  }

  /**
   * Executed on valid submit
   */
  private onValidSubmit(): void {
    this.releaseService.save(this.form.release).subscribe(release => {
      this.messageService.add(new Alert(AlertLevel.SUCCESS, new AlertContent('release.alert.created')));

      this.releaseService.updateCache(release);

      this.router.navigate(['project', 'edit', this.form.project]).then();
    }, (error: HttpErrorResponse) => {
      this.messageService.add(new Alert(AlertLevel.ERROR, new AlertContent(error.error.title)));
    });
  }

  /**
   * Executed on invalid submit
   */
  private onInvalidSubmit(): void {
    this.messageService.add(new Alert(AlertLevel.ERROR, new AlertContent('release.form.field.error.invalid')));
  }
}
