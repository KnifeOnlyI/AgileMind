/**
 * Represent a project
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
export class Project {
  /**
   * Constructor
   *
   * @param id The ID
   * @param name The name
   * @param description The description
   * @param assignedUsers The assigned users id list
   * @param adminUsers The project administrator user id list
   * @param stories The story id list
   * @param releases The release id list
   */
  public constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public assignedUsers?: Array<number>,
    public adminUsers?: Array<number>,
    public stories?: Array<number>,
    public releases?: Array<number>,
  ) {
  }
}
