/**
 * Represent a story
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
export class Story {
  /**
   * Constructor
   *
   * @param id The ID
   * @param name The name
   * @param description The description
   * @param points The story points
   * @param businessValue The business value
   * @param status The status id
   * @param type The status id
   * @param assignedUser The assigned user id
   * @param release The release id
   * @param project The project id
   * @param tasks The task id list
   */
  public constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public points?: number,
    public businessValue?: number,
    public status?: number,
    public type?: number,
    public assignedUser?: number,
    public release?: number,
    public project?: number,
    public tasks?: Array<number>,
  ) {
  }
}
