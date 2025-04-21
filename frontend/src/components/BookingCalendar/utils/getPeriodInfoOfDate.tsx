import isSameDay from "date-fns/isSameDay";
import startOfDay from "date-fns/startOfDay";
import endOfDay from "date-fns/endOfDay";
import differenceInMinutes from "date-fns/differenceInMinutes";

import { PeriodInfoOfDate, Period } from "../types";

export function getPeriodInfoOfDate(
  date: Date | number,
  period: Period[],
  isStart: boolean = false
): PeriodInfoOfDate {
  const periodInfo = {
    period: false,
    startDate: startOfDay(date),
    endDate: endOfDay(date),
  };
  if (!period.length) return periodInfo;

  const day = isStart ? endOfDay(date) : startOfDay(date);
  if (isStart) {
    const periodDay = period.find((d) => isSameDay(d.endDate, day));
    if (!periodDay) return periodInfo;

    return differenceInMinutes(day, periodDay.endDate) > 0
      ? {
          period: true,
          startDate: new Date(periodDay.endDate),
          endDate: day,
        }
      : periodInfo;
  }

  const periodDay = period.find((d) => isSameDay(d.startDate, day));
  if (!periodDay) return periodInfo;

  return differenceInMinutes(periodDay.startDate, day) > 0
    ? {
        period: true,
        startDate: day,
        endDate: new Date(periodDay.startDate),
      }
    : periodInfo;
}
