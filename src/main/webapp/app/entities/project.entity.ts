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
   * @param assignatedUserIdList The assignated users id list
   * @param storyIdList The story id list
   */
  public constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public assignatedUserIdList?: Array<number>,
    public storyIdList?: Array<number>,
  ) {
  }
}
