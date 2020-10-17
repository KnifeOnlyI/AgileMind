import {Component, OnInit} from '@angular/core';
import {MessageService} from 'app/service/message.service';
import {Message, MessageLevel} from '../../entity/message.entity';

@Component({
  selector: 'ag-message-list',
  templateUrl: './message-list.component.html',
})
export class MessageListComponent implements OnInit {
  /**
   * Contains info messages
   */
  public infoMessages = new Array<Message>();

  /**
   * Contains success messages
   */
  public successMessages = new Array<Message>();

  /**
   * Contains warning messages
   */
  public warnMessages = new Array<Message>();

  /**
   * Contains danger messages
   */
  public dangerMessages = new Array<Message>();

  /**
   * Constructor
   *
   * @param messageService The message service
   */
  public constructor(private messageService: MessageService) {

  }

  public ngOnInit(): void {
    this.infoMessages = this.messageService.getAll(MessageLevel.INFO);
    this.successMessages = this.messageService.getAll(MessageLevel.SUCCESS);
    this.warnMessages = this.messageService.getAll(MessageLevel.WARNING);
    this.dangerMessages = this.messageService.getAll(MessageLevel.DANGER);
  }
}
