/**
 * This file is part of jpa-cert application.
 *
 * Jpa-cert is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jpa-cert is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jpa-cert; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.kaczmarzyk.jpacert.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static Date newDate(int year, int month, int day) {
		return newDate(year, month, day, 0, 0, 0);
	}

	public static Date newDate(int year, int month, int day, int hourOfDay,
			int minute, int second) {
		Calendar calendar = newCalendar(new Date());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.clear(Calendar.MILLISECOND);

		return calendar.getTime();
	}

	public static Date setTime(Date date, int hourOfDay, int minute, int second) {
		Calendar calendar = newCalendar(date);
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	public static int getYear(Date date) {
		return newCalendar(date).get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		return newCalendar(date).get(Calendar.MONTH);
	}

	public static int getDay(Date date) {
		return newCalendar(date).get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		return newCalendar(date).get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		return newCalendar(date).get(Calendar.MINUTE);
	}

	public static Date addMonths(int months, Date date) {
		Calendar calendar = newCalendar(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	public static Date addMonthsToNow(int months) {
		return addMonths(months, new Date());
	}

	public static Date addDaysToNow(int days) {
		return addDays(days, new Date());
	}

	public static Date addDays(int days, Date date) {
		Calendar calendar = newCalendar(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	public static Date addYears(int years, Date date) {
		Calendar calendar = newCalendar(date);
		calendar.add(Calendar.YEAR, years);
		return calendar.getTime();
	}

	public static Date addMinutes(int minutes, Date date) {
		Calendar calendar = newCalendar(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	public static Date addMinutesToNow(int minutes) {
		return addMinutes(minutes, new Date());
	}

	public static Date addSeconds(int seconds, Date date) {
		Calendar calendar = newCalendar(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	public static Date addYearsToNow(int years) {
		return addYears(years, new Date());
	}

	public static Date today() {
		return new Date();
	}

	public static Date today(int hour, int minute) {
		return setTime(today(), hour, minute, 0);
	}

	public static Date yesterday() {
		return addDaysToNow(-1);
	}

	public static Date minAgo() {
		return addMinutesToNow(-1);
	}

	public static Date tomorrow() {
		return addDaysToNow(1);
	}

	public static Date tomorrow(int hour, int minute) {
		return setTime(tomorrow(), hour, minute, 0);
	}

	public static Date nextWeek() {
		return addDaysToNow(7);
	}

	public static Date weekAgo() {
		return addDaysToNow(-7);
	}

	public static Date resetTime(Date date) {
		return resetTime(date);
	}

	public static int get(int field, Date date) {
		Calendar calendar = newCalendar(date);
		return calendar.get(field);
	}

	private static Calendar newCalendar(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	public static boolean isFirstDayOfMonth() {
		Calendar calendar = newCalendar(new Date());
		return calendar.get(Calendar.DAY_OF_MONTH) == 1;
	}

	public static boolean isLastDayOfMonth() {
		Calendar calendar = newCalendar(new Date());
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return calendar.get(Calendar.DAY_OF_MONTH) == lastDay;
	}

}
