package com.wattics.vimsen.utils;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TimeHelpersTest {

  @Test
  public void parserMantainsLocalTime() {
    DateTime expectedDateTime = TimeHelpers.timestampUtcFormatterISO8601.withOffsetParsed()
        .parseDateTime("2015-10-19T13:00:00.000+02:00");

    Assert.assertEquals(expectedDateTime.getHourOfDay(), 13);
  }

  @Test
  public void convertDateTimeToDateMidnight() {
    DateTime timestamp = new DateTime("2015-11-19T13:00:00.000+02:00");
    DateTime actualDateTime = TimeHelpers.convertDateTimeToDateMidnight(timestamp);

    DateTime expectedDateTime = new DateTime("2015-11-19T00:00:00.000+00:00");

    Assert.assertEquals(actualDateTime.getDayOfMonth(), 19);
    Assert.assertEquals(actualDateTime.getMillis(), expectedDateTime.getMillis());
  }

  @Test
  public void convertInstantOrDateTimeToDateMidnight() {
    String timeString = "2015-10-19T13:00:00.000+02:00";
    DateTime datetime = new DateTime(timeString);
    Instant instant = new Instant(timeString);

    DateTime datetimeMidnight = TimeHelpers.convertDateTimeToDateMidnight(datetime);
    DateTime instantMidnight = TimeHelpers.convertInstantToDateMidnight(instant);

    Assert.assertEquals(datetimeMidnight, instantMidnight);
  }

  @Test
  public void getValuesDuringSelectedPeriodTest() {
    DateTime startTime = new DateTime("2015-11-19T13:00:00.000+00:00");
    DateTime endTime = new DateTime("2015-11-19T15:00:00.000+00:00");
    Double[] values = new Double[96];
    values[13 * 4] = 13.0;

    Double[] selectedValues = TimeHelpers.getValuesDuringSelectedPeriod(values, startTime, endTime);

    Assert.assertEquals(selectedValues.length, 8);
    Assert.assertEquals(selectedValues[0], 13.0);
  }

  @Test
  public void getNumberIntervalsStartEndBorders() {
    Instant startTime = new Instant("2015-11-19T13:00:00.000+00:00");
    Instant endTime = new Instant("2015-11-19T13:30:00.000+00:00");
    int intervalDurationSec = 900;

    int numberIntervals = TimeHelpers.getNumberIntervals(startTime, endTime, intervalDurationSec);

    Assert.assertEquals(numberIntervals, 2);
  }

  @Test
  public void getNumberIntervalsStartBorder() {
    Instant startTime = new Instant("2015-11-19T13:00:00.000+00:00");
    Instant endTime = new Instant("2015-11-19T13:25:00.000+00:00");
    int intervalDurationSec = 900;

    int numberIntervals = TimeHelpers.getNumberIntervals(startTime, endTime, intervalDurationSec);

    Assert.assertEquals(numberIntervals, 1);
  }

  @Test
  public void getNumberIntervalsEndBorder() {
    Instant startTime = new Instant("2015-11-19T13:10:00.000+00:00");
    Instant endTime = new Instant("2015-11-19T13:30:00.000+00:00");
    int intervalDurationSec = 900;

    int numberIntervals = TimeHelpers.getNumberIntervals(startTime, endTime, intervalDurationSec);

    Assert.assertEquals(numberIntervals, 2);
  }

  @Test
  public void getNumberIntervalsNotExactDuration() {
    Instant startTime = new Instant("2015-11-19T13:10:00.000+00:00");
    Instant endTime = new Instant("2015-11-19T13:27:00.000+00:00");
    int intervalDurationSec = 900;

    int numberIntervals = TimeHelpers.getNumberIntervals(startTime, endTime, intervalDurationSec);

    Assert.assertEquals(numberIntervals, 1);
  }

  @Test
  public void convertDateTimeToStringTimezone() {
    String timeString = "2015-11-19T13:00:00.000+02:00";
    DateTime datetime = TimeHelpers.createDateTimePreservingTimeZone(timeString);

    String datetimeString = datetime.toString();

    Assert.assertEquals(datetimeString, timeString);
  }

  @Test
  public void testTimeBeforeAfter() {
    String time = "2016-01-22T20:00:00.000+02:00";
    String timeDayBefore = "2016-01-21T20:00:00.000+02:00";
    DateTime datetime = new DateTime(time);

    DateTime dateDayBefore = new DateTime(timeDayBefore);
    DateTime dateMinus = datetime.minusDays(1);

    Assert.assertEquals(dateMinus, dateDayBefore);
  }

  @Test
  public void getHourAndMinutesTest() {
    String time = "2016-01-22T20:30:00.000+02:00";
    String timeToConv = "2016-01-22T18:30:00.000+00:00";
    DateTime dateTimeToConvert = new DateTime(timeToConv);

    String actualHourMinute = TimeHelpers.getLocalTimeHourAndMinutes(time, dateTimeToConvert);

    String expectedHourMinutes = "20:30";
    Assert.assertEquals(actualHourMinute, expectedHourMinutes);
  }

  @Test
  public void getDateTest() {
    String time = "2016-01-22T20:30:00.000+02:00";
    String timeToConv = "2016-01-22T18:30:00.000+00:00";
    DateTime dateTimeToConvert = new DateTime(timeToConv);

    String actualDate = TimeHelpers.getLocalDate(time, dateTimeToConvert);

    String expectedDay = "2016-01-22";
    Assert.assertEquals(actualDate, expectedDay);
  }

  @Test
  public void convertDateTimeToStringTest() {
    String time = "2016-01-22T20:30:00.000+02:00";
    String expectedDate = "2016-01-22T18:30:00.000+00:00";
    DateTime originalDateTime = new DateTime(time);

    String actualDate = TimeHelpers.convertDateTimeToString(originalDateTime);

    Assert.assertEquals(actualDate, expectedDate);
  }

  @Test
  public void convertInstantToStringTest() {
    String time = "2016-01-22T20:30:00.000+02:00";
    String expectedDate = "2016-01-22T18:30:00.000+00:00";
    Instant originalDateTime = new Instant(time);

    String actualDate = TimeHelpers.convertInstantToString(originalDateTime);

    Assert.assertEquals(actualDate, expectedDate);
  }

  @Test
  public void convertDateTimeToLocalHour() {
    String time = "2016-01-10T20:30:00.000+02:00";
    String timeToConv = "2016-01-22T18:30:00.000+00:00";
    DateTime dateTimeToConvert = new DateTime(timeToConv);

    int actualHour = TimeHelpers.getLocalTimeHour(time, dateTimeToConvert);

    int expectedHour = 20;
    Assert.assertEquals(actualHour, expectedHour);
  }

  @Test
  public void convertDateTimeToLocalHourNegativeDurationDifference() {
    String time = "2016-01-10T20:30:00+00:00";
    String timeToConv = "2016-01-10T21:30:00+01:00";
    DateTime dateTimeToConvert = new DateTime(timeToConv);

    int actualHour = TimeHelpers.getLocalTimeHour(time, dateTimeToConvert);

    int expectedHour = 20;
    Assert.assertEquals(actualHour, expectedHour);
  }

  @Test
  public void convertDateTimeToDateString() {
    DateTime datetime = new DateTime("2016-11-10T21:30:00.000+01:00");
    String dateString = TimeHelpers.convertDateTimeToDateString(datetime);

    Assert.assertEquals(dateString, "2016-11-10");
  }

}
