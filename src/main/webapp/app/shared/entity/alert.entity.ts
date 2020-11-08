/**
 * Represent a message level
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
export enum AlertLevel {
  INFO,
  SUCCESS,
  WARNING,
  ERROR,
}

/**
 * Represent an alert title
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class AlertTitle {
  /**
   * Constructor
   *
   * @param key The key
   * @param interpolateParams The interpolate params
   */
  public constructor(public key: string, public interpolateParams?: Object) {
  }
}

/**
 * Represent an alert content
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class AlertContent {
  /**
   * Constructor
   *
   * @param key The content
   * @param interpolateParams The interpolate params
   */
  public constructor(public key: string, public interpolateParams?: Object) {
  }
}

/**
 * Represent an alert message for displaying to user
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
export class Alert {
  /**
   * Create new alert message
   *
   * @param level The level
   * @param content The content
   * @param title The title
   */
  public constructor(
    public level: AlertLevel,
    public content?: AlertContent,
    public title?: AlertTitle,
  ) {
  }
}
