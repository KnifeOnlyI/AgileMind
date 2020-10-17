/**
 * Represent a standard HTTP from the server
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export type HTTPError = {
  /**
   * The title (error key)
   */
  title: string;

  /**
   * The HTTP status
   */
  status: number;
};
