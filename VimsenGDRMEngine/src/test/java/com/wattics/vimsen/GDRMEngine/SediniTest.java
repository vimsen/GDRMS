package com.wattics.vimsen.GDRMEngine;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Months;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimsen.entities.MarketSignal;
import com.wattics.vimsen.utils.TimeHelpers;

public class SediniTest {
  @Test
  public void testPlanIsHistorical() {
    MarketSignal ms= new MarketSignal();
    ms.setStartTime(DateTime.now().plusDays(4));
//    Long sixMonthsAgoInMs = Instant.now().getMillis() - 6 * 30 * 24*60*60*10;
//    System.out.println("Now: "+new DateTime(Instant.now().getMillis()));
//    System.out.println(6 * 30 * 24*60*60);
//    System.out.println("Six months ago: "+new DateTime(sixMonthsAgoInMs));
//    boolean isHist= ms.getStartTime().isBefore(sixMonthsAgoInMs);
    DateTime now =  DateTime.now();
    boolean isHist=Math.abs(Months.monthsBetween(now, ms.getStartTime()).getMonths()) > 6;

//    boolean isHist= PlanManager.isPlanHistorical(ms);
    Assert.assertFalse(isHist);
  }
}
