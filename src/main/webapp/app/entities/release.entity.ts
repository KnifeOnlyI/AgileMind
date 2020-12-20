/**
 * Represent a release
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */

export class Release {
  /**
   * Constructor
   *
   * @param id The ID
   * @param name The Name
   * @param description The description
   * @param date The date
   * @param stories The stories
   * @param project The project
   */
  public constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public date?: Date,
    public stories?: Array<number>,
    public project?: number,
  ) {
  }
}
