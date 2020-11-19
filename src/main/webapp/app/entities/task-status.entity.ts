/**
 * Represent a task status
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
export class TaskStatus {
  /**
   * Constructor
   *
   * @param id The ID
   * @param key The key name
   */
  public constructor(
    public id?: number,
    public key?: string,
  ) {
  }
}
