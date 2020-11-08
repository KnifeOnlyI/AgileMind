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
   * @param statusId The status id
   * @param assignatedUserId The assignated user id
   * @param projectId The project id
   */
  public constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public points?: number,
    public businessValue?: number,
    public statusId?: number,
    public assignatedUserId?: number,
    public projectId?: number,
  ) {
  }
}
