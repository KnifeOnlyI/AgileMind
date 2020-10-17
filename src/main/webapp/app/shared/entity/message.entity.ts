/**
 * Represent a message level
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
export enum MessageLevel {
  INFO,
  SUCCESS,
  WARNING,
  DANGER,
}

/**
 * Represent a message for displaying to user
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
export class Message {
  /**
   * Create new message
   *
   * @param content The content
   * @param level The level
   * @param transient TRUE if the message is transient (displaying only for few seconds), FALSE otherwise
   * @param alreadyDisplayed TRUE if the message already displayed, FALSE otherwise
   */
  public constructor(
    public content: string,
    public level: MessageLevel,
    public transient = true,
    public alreadyDisplayed = false
  ) {
  }
}
