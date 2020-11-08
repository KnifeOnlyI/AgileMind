import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ToastrService} from 'ngx-toastr';
import {Alert, AlertLevel} from 'app/shared/entity/alert.entity';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class AlertService {
  /**
   * Constructor
   *
   * @param translateService The translate service
   * @param alertService The alert service
   */
  public constructor(private translateService: TranslateService, private alertService: ToastrService) {
  }

  /**
   * Add message
   *
   * @param alert The alert message to display
   */
  public add(alert: Alert): void {
    const content = alert.content?.key;
    const contentParams = alert.content?.interpolateParams;

    const title = alert.title?.key;
    const titleParams = alert.title?.interpolateParams;

    if (content && title) {
      this.addContentTitle(alert.level,
        this.translateService.instant(content, contentParams),
        this.translateService.instant(title, titleParams)
      );
    } else if (content) {
      this.addContent(alert.level, this.translateService.instant(content, contentParams));
    } else if (title) {
      this.addTitle(alert.level, this.translateService.instant(title, titleParams));
    } else {
      throw new Error('Invalid alert. MUST have content AND/OR title !');
    }
  }

  /**
   * Add message with content and title
   *
   * @param level The alert level
   * @param content The alert content
   * @param title The alert title
   */
  private addContentTitle(level: AlertLevel, content: string, title: string): void {
    switch (level) {
      case AlertLevel.INFO:
        this.alertService.info(content, title);
        break;
      case AlertLevel.SUCCESS:
        this.alertService.success(content, title);
        break;
      case AlertLevel.WARNING:
        this.alertService.warning(content, title);
        break;
      case AlertLevel.ERROR:
        this.alertService.error(content, title);
        break;
    }
  }

  /**
   * Add message with title
   *
   * @param level The alert level
   * @param title The alert title
   */
  private addTitle(level: AlertLevel, title: string): void {
    switch (level) {
      case AlertLevel.INFO:
        this.alertService.info(undefined, title);
        break;
      case AlertLevel.SUCCESS:
        this.alertService.success(undefined, title);
        break;
      case AlertLevel.WARNING:
        this.alertService.warning(undefined, title);
        break;
      case AlertLevel.ERROR:
        this.alertService.error(undefined, title);
        break;
    }
  }

  /**
   * Add message with content
   *
   * @param level The alert level
   * @param content The alert content
   */
  private addContent(level: AlertLevel, content: string): void {
    switch (level) {
      case AlertLevel.INFO:
        this.alertService.info(content);
        break;
      case AlertLevel.SUCCESS:
        this.alertService.success(content);
        break;
      case AlertLevel.WARNING:
        this.alertService.warning(content);
        break;
      case AlertLevel.ERROR:
        this.alertService.error(content);
        break;
    }
  }
}
