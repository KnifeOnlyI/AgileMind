/**
 * Represent a cache
 *
 * @param T The value types
 * @param K The key types
 */
export class Cache<T, K = number> {
  /** The cache content */
  private content = new Array<{ key: K, value?: T }>();

  /**
   * Set value for the specified key (create new key if not exists, replace value otherwise)
   *
   * @param key The key
   * @param value The value
   */
  public set(key: K, value?: T): void {
    const founded = this.content.find((content) => content.key === key);

    if (!founded) {
      this.content.push({key, value});
    } else {
      founded.value = value;
    }
  }

  /**
   * Get the value for the specified key (undefined if not exists or the value is null or undefined)
   *
   * @param key The key to search
   *
   * @return The founded value
   */
  public get(key: K): T | undefined {
    return this.content.find((content) => content.key === key)?.value;
  }

  /**
   * Get all values
   *
   * @return All values
   */
  public getAll(): Array<T | undefined> {
    const results = new Array<T | undefined>();

    this.content.forEach(content => results.push(content.value));

    return results;
  }

  /**
   * Check if the specified key exists
   *
   * @param key The key to check
   *
   * @return TRUE if the specified key exists, FALSE otherwise
   */
  public has(key: K): boolean {
    return this.content.findIndex((content) => content.key === key) !== -1;
  }

  /**
   * Delete the specified entry
   *
   * @param key The key to remove
   */
  public delete(key: K): void {
    const index = this.content.findIndex((content) => content.key === key);

    if (index !== -1) {
      this.content.splice(index, 1);
    }
  }

  /**
   * Delete all the cache
   */
  public deleteAll(): void {
    this.content.splice(0, this.content.length);
  }
}
