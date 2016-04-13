package com.wattics.vimsen.DSSdata;

import java.util.HashMap;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wattics.vimen.DSSdata.DRRequest;
import com.wattics.vimen.DSSdata.DSSRequestsJsonMapper;
import com.wattics.vimen.DSSdata.PlanCurrentStatus;
import com.wattics.vimsen.utils.MapperException;
import com.wattics.vimsen.utils.TimeHelpers;

public class DSSRequestJsonMapperTest {

  private String generateDefaultJsonDRRequest() {
    String dssRequest = "{ \"start_time\":\"2015-10-19T13:00:00.000+02:00\", " + "\"interval\":900," + "\"unit\":\"kW\","
        + "\"target_reduction\":[  50.3253179,45.15539599955082,56.13061499595642,68.14941400289536,65.17419399321079,63.140625,47.14074699580669,28.16027800738811,61.14758299291134],"
        + "\"prosumers_primary\":[  1,4,7,1]," + "\"prosumers_secondary\":[  2,3,8,11,16]" + "}";
    return dssRequest;
  }

  @Test
  public void deserialiseDRRequest() throws MapperException {
    String jsonDRRequest = generateDefaultJsonDRRequest();
    DRRequest dssRequest = DSSRequestsJsonMapper.deserialiseDRRequest(jsonDRRequest);

    Assert.assertEquals(dssRequest.interval, 900);
    Assert.assertEquals(dssRequest.prosumers_primary[0], 1);
  }

  @Test
  public void deserialiseTimeZone() throws MapperException {
    String jsonDRRequest = generateDefaultJsonDRRequest();
    DRRequest dssRequest = DSSRequestsJsonMapper.deserialiseDRRequest(jsonDRRequest);

    DateTime expectedTime = TimeHelpers.createDateTimePreservingTimeZone("2015-10-19T13:00:00.000+02:00");

    Assert.assertEquals(dssRequest.start_time, expectedTime);
  }

  @Test
  public void serializePlanCurrentStatus() throws MapperException {
    PlanCurrentStatus planCurrentStatus = generateDefaultPlanCurrentStatus();
    String jsonFormat = DSSRequestsJsonMapper.serializePlanCurrentStatus(planCurrentStatus);
    String expectedJson = "{\"status\":\"processed\",\"start_time\":\"2015-10-19T13:00:00.000+02:00\",\"plan_id\":20,\"interval\":900,\"unit\":\"kW\",\"planned_dr\":{\"1\":[12.0,8.0],\"4\":[4.0,8.0]},\"actual_dr\":{}}";

    Assert.assertEquals(jsonFormat, expectedJson);
  }

  private PlanCurrentStatus generateDefaultPlanCurrentStatus() {
    PlanCurrentStatus planCurrentStatus = new PlanCurrentStatus();
    planCurrentStatus.interval = 900;
    planCurrentStatus.start_time = "2015-10-19T13:00:00.000+02:00";
    planCurrentStatus.status = "processed";
    planCurrentStatus.plan_id = 20;
    planCurrentStatus.unit = "kW";
    HashMap<Integer, Double[]> planned_dr = new HashMap<Integer, Double[]>();
    planned_dr.put(1, new Double[] { 3.0, 2.0 });
    planned_dr.put(4, new Double[] { 1.0, 2.0 });
    planCurrentStatus.planned_dr = planned_dr;
    return planCurrentStatus;
  }
}
