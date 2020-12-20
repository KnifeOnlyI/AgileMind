/**
 * Represent a task
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
export class Task {
  /**
   * Constructor
   *
   * @param id The ID
   * @param name The name
   * @param description The description
   * @param estimatedTime The estimated time (in minutes)
   * @param loggedTime The logged time (in minutes)
   * @param status The status id
   * @param assignedUser The assigned user id
   * @param story The story id
   */
  public constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public estimatedTime?: number,
    public loggedTime?: number,
    public status?: number,
    public assignedUser?: number,
    public story?: number,
  ) {
  }
}
