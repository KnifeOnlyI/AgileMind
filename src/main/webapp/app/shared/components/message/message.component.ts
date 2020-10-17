import {Component, Input, OnInit} from '@angular/core';
import {Message, MessageLevel} from '../../entity/message.entity';

/**
 * Component to manage message displaying
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Component({
  selector: 'ag-message',
  templateUrl: './message.component.html',
})
// @ts-ignore
export class MessageComponent implements OnInit {
  /**
   * The message to display
   */
  @Input()
  public message!: Message;

  /**
   * Determine if the message must be displayed
   */
  public display = true;

  /**
   * The CSS style of the HTML message
   */
  public cssStyle = new Array<string>('alert');

  /**
   * Executed on component initialization
   */
  public ngOnInit(): void {
    if (!this.message.alreadyDisplayed) {
      if (this.message.transient) {
        window.setTimeout(() => {
          this.display = false;
        }, 3000);
      }

      switch (this.message.level) {
        case MessageLevel.INFO:
          this.cssStyle.push('alert-info');
          break;
        case MessageLevel.SUCCESS:
          this.cssStyle.push('alert-success');
          break;
        case MessageLevel.WARNING:
          this.cssStyle.push('alert-warning');
          break;
        case MessageLevel.DANGER:
          this.cssStyle.push('alert-danger');
          break;
      }

      this.message.alreadyDisplayed = true;
    } else {
      this.display = false;
    }
  }
}
