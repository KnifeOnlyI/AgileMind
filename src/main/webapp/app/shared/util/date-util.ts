export class DateUtil {
  public static dateToString(date: Date | undefined): string | undefined {
    let string;

    console.log('Date', date);

    if (date) {
      const timestamp = Date.parse(JSON.stringify(date).substr(1, 28));

      string = new Date(timestamp).toISOString().substr(0, 10);
    }

    return string;
  };
}
