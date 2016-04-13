package com.wattics.vimen.DSSdata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wattics.vimsen.utils.JSONMapper;
import com.wattics.vimsen.utils.LoadConverter;
import com.wattics.vimsen.utils.MapperException;

public class DSSRequestsJsonMapper {

  public static DRRequest deserialiseDRRequest(String jsonDRRequest) throws MapperException {
    DRRequest dssRequest = new DRRequest();
    try {
      dssRequest = DSSRequestsJsonMapper.readValueFromJson(jsonDRRequest);
      dssRequest = drRequestloadEnergyConversion(dssRequest);
    } catch (IOException e) {
      throw new MapperException("Error to serialize DR request: ", e);
    }

    return dssRequest;
  }

  private static DRRequest drRequestloadEnergyConversion(DRRequest dssRequest) {
    int intervalSizeSec = dssRequest.interval;
    Double[] targetLoad = LoadConverter.convertArrayKwToKwh(dssRequest.target_reduction, intervalSizeSec);
    dssRequest.target_reduction = targetLoad;
    return dssRequest;
  }

  private static DRRequest readValueFromJson(String jsonString) throws JsonParseException, JsonMappingException, IOException {
    DRRequest elements = JSONMapper.objectMapper.readValue(jsonString, DRRequest.class);
    return elements;
  }

  public static String serializeDRRequestAck(DRRequestAck planInfo) throws MapperException {
    String json = null;
    try {
      json = JSONMapper.objectMapper.writeValueAsString(planInfo);
    } catch (JsonProcessingException e) {
      throw new MapperException("Error to serialize DRRequest response: ", e);
    }
    return json;
  }

  public static String serializePlanCurrentStatus(PlanCurrentStatus planCurrentStatus) throws MapperException {
    planCurrentStatus = planStatusEnergyLoadConversion(planCurrentStatus);
    String jsonPlan = null;
    try {
      jsonPlan = JSONMapper.objectMapper.writeValueAsString(planCurrentStatus);
    } catch (JsonProcessingException e) {
      throw new MapperException("Not possible to serialize Plan current status in json format: ", e);
    }
    return jsonPlan;
  }

  private static PlanCurrentStatus planStatusEnergyLoadConversion(PlanCurrentStatus planCurrentStatus) {
    HashMap<Integer, Double[]> actual_dr_load = new HashMap<Integer, Double[]>();
    if (isNotNull(planCurrentStatus.actual_dr)) {
      HashMap<Integer, Double[]> actual_dr = planCurrentStatus.actual_dr;
      for (Entry<Integer, Double[]> entry : actual_dr.entrySet()) {
        Double[] load = LoadConverter.convertArrayKwhToKw(entry.getValue(), planCurrentStatus.interval);
        actual_dr_load.put(entry.getKey(), load);
      }
    }
    planCurrentStatus.actual_dr = actual_dr_load;

    HashMap<Integer, Double[]> planned_dr_load = new HashMap<Integer, Double[]>();
    if (isNotNull(planCurrentStatus.planned_dr)) {
      HashMap<Integer, Double[]> planned_dr = planCurrentStatus.planned_dr;
      for (Entry<Integer, Double[]> entry : planned_dr.entrySet()) {
        Double[] load = LoadConverter.convertArrayKwhToKw(entry.getValue(), planCurrentStatus.interval);
        planned_dr_load.put(entry.getKey(), load);
      }
    }
    planCurrentStatus.planned_dr = planned_dr_load;

    return planCurrentStatus;
  }

  private static boolean isNotNull(HashMap<Integer, Double[]> map) {
    return map != null;
  }

  public static DRRequestAck deserialiseDRRequestAck(String jsonDRRequestAck) throws MapperException {
    DRRequestAck dssRequestAck = new DRRequestAck();
    try {
      dssRequestAck = JSONMapper.objectMapper.readValue(jsonDRRequestAck, DRRequestAck.class);
    } catch (IOException e) {
      throw new MapperException("Error to serialize DR request: ", e);
    }
    return dssRequestAck;
  }

}
