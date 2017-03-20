package com.wattics.vimsen.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class TimeHelpers {

  public static final int MS_IN_A_SEC = 1000;
  public static final int MS_IN_A_DAY = 24 * 60 * 60 * 1000;
  public static final int SEC_IN_A_MIN = 60;
  public static final int MIN_IN_A_HOUR = 60;
  public static final int SIX_MONTHS_IN_MS = 6 * 30 * MS_IN_A_DAY;
  public static final DateTimeFormatter timestampUtcFormatterISO8601 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
  public static final DateTimeFormatter timestampUtcFormatterISO8601Colon = DateTimeFormat
      .forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
  public static final DateTimeFormatter timestampUtcFormatterISO8601OnlyDate = DateTimeFormat.forPattern("yyyy-MM-dd");
  public static final DateTimeFormatter timestampUtcFormatterISO8601NoMs = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ");
  public static final DateTimeFormatter timestampUtcFormatterISO8601WithMs = DateTimeFormat
      .forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

  private final static DateTimeZone DEFAULT_ZONE = DateTimeZone.UTC;

  public static DateTime convertInstantToDateTime(Instant instant) {
    DateTime dateTime = instant.toDateTime(DEFAULT_ZONE);
    return dateTime;
  }

  public static DateTime convertInstantToDateMidnight(Instant timestamp) {
    DateTime time = timestamp.toDateTime(DEFAULT_ZONE);
    DateTime timeMidnight = time.withTimeAtStartOfDay();
    return timeMidnight;
  }

  public static DateTime convertDateTimeToDateMidnight(DateTime datetime) {
    DateTime time = datetime.toDateTime(DateTimeZone.UTC);
    DateTime timeMidnight = time.withTimeAtStartOfDay();
    return timeMidnight;
  }

  public static DateTime createDateTimePreservingTimeZone(String timeString) {
    DateTime datetime = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(timeString);
    return datetime;
  }

  public static Double[] getValuesDuringSelectedPeriod(Double[] dailyValues15mn, DateTime startTime, DateTime endTime) {
    int startHour = startTime.getHourOfDay();
    int startMinute = startTime.getMinuteOfHour();
    int endHour = endTime.getHourOfDay();
    int endMinute = endTime.getMinuteOfHour();
    int startIndex = getIndexFromDayTime(startHour, startMinute);
    int endIndex = getIndexFromDayTime(endHour, endMinute);
    Double[] selectedValues = Arrays.copyOfRange(dailyValues15mn, startIndex, endIndex);
    return selectedValues;
  }

  private static int getIndexFromDayTime(int startHour, int startMinute) {
    int index = startHour * 4 + (startMinute / 15);
    return index;
  }

  public static int getNumberIntervals(Instant startTime, Instant endTime, int intervalDurationSec) {
    long startTimeMs = startTime.getMillis();
    long endTimeMs = endTime.getMillis();

    long startSlot = startTimeMs / (intervalDurationSec * MS_IN_A_SEC);
    long endSlot = endTimeMs / (intervalDurationSec * MS_IN_A_SEC);

    int numberSlots = (int) (endSlot - startSlot);
    return numberSlots;
  }

  public static String getLocalTimeHourAndMinutes(String referenceTime, DateTime dateTime) {
    DateTime datetimeRef = TimeHelpers.timestampUtcFormatterISO8601.withOffsetParsed().parseDateTime(referenceTime);
    Duration duration = new Duration(datetimeRef.getMillis(), dateTime.getMillis());
    DateTime dateTimeAdjusted = datetimeRef.plus(duration);

    DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
    String hourMinutes = dateTimeAdjusted.toString(formatter);

    return hourMinutes;
  }

  public static int getLocalTimeHour(String referenceTime, DateTime dateTime) {
    DateTime datetimeRef = TimeHelpers.timestampUtcFormatterISO8601.withOffsetParsed().parseDateTime(referenceTime);
    Duration duration = new Duration(datetimeRef.getMillis(), dateTime.getMillis());
    DateTime dateTimeAdjusted = datetimeRef.plus(duration);

    return dateTimeAdjusted.getHourOfDay();
  }

  public static String getLocalDate(String referenceTime, DateTime dateTime) {
    DateTime datetimeRef = TimeHelpers.timestampUtcFormatterISO8601.withOffsetParsed().parseDateTime(referenceTime);
    Duration duration = new Duration(datetimeRef.getMillis(), dateTime.getMillis());
    DateTime dateTimeAdjusted = datetimeRef.plus(duration);

    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    String hourMinutes = dateTimeAdjusted.toString(formatter);

    return hourMinutes;
  }

  public static String convertDateTimeToDateString(DateTime originalDateTime) {
    String dateString = timestampUtcFormatterISO8601OnlyDate.print(originalDateTime);
    return dateString;
  }

  public static String convertDateTimeToString(DateTime originalDateTime) {
    String dateString = timestampUtcFormatterISO8601Colon.print(originalDateTime);
    return dateString;
  }

  public static String convertInstantToString(Instant instantTime) {
    DateTime dateTime = new DateTime(instantTime);
    String timeString = convertDateTimeToString(dateTime);
    return timeString;
  }

  public static DateTime convertStringToDateTime(String timeString) {
    DateTime time = timestampUtcFormatterISO8601NoMs.withOffsetParsed().parseDateTime(timeString);
    return time;
  }

  public static DateTime convertStringWithMsToDateTime(String timeString) {
    DateTime time = timestampUtcFormatterISO8601WithMs.withOffsetParsed().parseDateTime(timeString);
    return time;
  }

  public static List<Long> getInitialSlotsTimeStamps(DateTime startTime, DateTime endTime, int timeInterval) {
    List<Long> timestamps = new ArrayList<Long>();
    Long startMs = startTime.toInstant().getMillis();
    Long endMs = endTime.toInstant().getMillis();

    if (endMs < startMs) {
      return timestamps;
    }

    int numberIntervals = TimeHelpers.getNumberIntervals(startTime.toInstant(), endTime.toInstant(), timeInterval);

    for (int i = 0; i < numberIntervals; i++) {
      timestamps.add(startMs + (i * timeInterval * 1000));
    }

    return timestamps;
  }

}
