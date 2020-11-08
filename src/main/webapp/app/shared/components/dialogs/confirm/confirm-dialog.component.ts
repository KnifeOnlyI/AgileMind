import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

/**
 * Represent all confirm dialog type
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export enum ConfirmDialogType {
  YES_NO,
  DELETE,
}

/**
 * The exit state
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export enum ExitState {
  NO_CHOICE,
  YES,
  NO
}

/**
 * Component to manage confirm dialog
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  templateUrl: './confirm-dialog.component.html'
})
export class ConfirmDialogComponent implements OnInit {
  /**
   * The dialog type
   */
  @Input()
  public type = ConfirmDialogType.YES_NO;

  /**
   * On close event (TRUE if user choice is "yes", FALSE otherwise)
   */
  @Output()
  public onClose = new EventEmitter<ExitState>();

  /**
   * The title label
   */
  public titleLabel = 'global.confirm.yesNo.title';

  /**
   * The yes button label
   */
  public yesBtnLabel = 'global.confirm.yesNo.yes';

  /**
   * The no button label
   */
  public noBtnLabel = 'global.confirm.yesNo.no';

  /**
   * The yes button class
   */
  public yesBtnClass = 'btn btn-primary';

  /**
   * The no button class
   */
  public noBtnClass = 'btn btn-secondary';

  /**
   * Constructor
   *
   * @param activeModal The active modal service
   */
  public constructor(public activeModal: NgbActiveModal) {
  }

  /**
   * On init
   */
  public ngOnInit(): void {
    switch (this.type) {
      case ConfirmDialogType.DELETE:
        this.titleLabel = 'global.confirm.delete.title';
        this.yesBtnLabel = 'global.confirm.delete.yes';
        this.noBtnLabel = 'global.confirm.delete.no';
        this.yesBtnClass = 'btn btn-danger';
        this.noBtnClass = 'btn btn-secondary';
        break;

    }
  }

  /**
   * Executed to close the popup
   *
   * @param exitState the exit state
   */
  public close(exitState: ExitState): void {
    this.activeModal.dismiss();
    this.onClose.emit(exitState);
  }

  /**
   * Executed when user no choice
   */
  public noChoice(): void {
    this.close(ExitState.NO_CHOICE);
  }

  /**
   * Executed when user choose "yes"
   */
  public yes(): void {
    this.close(ExitState.YES);
  }

  /**
   * Executed when user choose "no"
   */
  public no(): void {
    this.close(ExitState.NO);
  }
}
