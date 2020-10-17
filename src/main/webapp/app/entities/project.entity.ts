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
   * @param assignatedUsers The assignated users id list
   */
  public constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public assignatedUsers?: Array<number>
  ) {
  }
}
