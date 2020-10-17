import {Injectable} from '@angular/core';
import {Message, MessageLevel} from '../shared/entity/message.entity';

@Injectable({providedIn: 'root'})
// @ts-ignore
export class MessageService {
  /**
   * Contains info messages
   */
  private infoMessages: Array<Message> = new Array<Message>();

  /**
   * Contains success messages
   */
  private successMessages: Array<Message> = new Array<Message>();

  /**
   * Contains warning messages
   */
  private warnMessages: Array<Message> = new Array<Message>();

  /**
   * Contains danger messages
   */
  private dangerMessages: Array<Message> = new Array<Message>();

  /**
   * Add the specified message to messages list
   *
   * @param message The message to add
   */
  public addMessage(message: Message): void {
    switch (message.level) {
      case MessageLevel.INFO:
        this.infoMessages.push(message);
        break;
      case MessageLevel.SUCCESS:
        this.successMessages.push(message);
        break;
      case MessageLevel.WARNING:
        this.warnMessages.push(message);
        break;
      case MessageLevel.DANGER:
        this.dangerMessages.push(message);
        break;
    }
  }

  /**
   * Get all messages with the specified level
   *
   * @param level The queried level
   */
  public getAll(level: MessageLevel): Array<Message> {
    let results: Array<Message>;

    switch (level) {
      case MessageLevel.INFO:
        results = this.infoMessages;
        this.infoMessages.splice(0, this.infoMessages.length - 1);
        break;
      case MessageLevel.SUCCESS:
        results = this.successMessages;
        this.successMessages.splice(0, this.successMessages.length - 1);
        break;
      case MessageLevel.WARNING:
        results = this.warnMessages;
        this.warnMessages.splice(0, this.warnMessages.length - 1);
        break;
      case MessageLevel.DANGER:
        results = this.dangerMessages;
        this.dangerMessages.splice(0, this.dangerMessages.length - 1);
        break;
    }

    return results;
  }
}
