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
   * @param assignedUserId The assigned user id
   * @param projectId The project id
   * @param taskIdList The task id list
   */
  public constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public points?: number,
    public businessValue?: number,
    public statusId?: number,
    public assignedUserId?: number,
    public projectId?: number,
    public taskIdList?: Array<number>,
  ) {
  }
}
