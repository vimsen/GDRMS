package com.wattics.vimsen.EDMSdatamanager;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wattics.vimsen.utils.JSONMapper;

public class EDMSDataGetter implements EDMSDataGetterInterface {

  public EDMSMeasurement getEDMSMeasurementFromVGWUrl(String urlRequest) throws EDMSDataGetterException {
    List<EDMSMeasurement> measurements;
    try {
      measurements = getMeasurementsVGW(urlRequest);
    } catch (ClientProtocolException e) {
      throw new EDMSDataGetterException(e);
    } catch (IOException e) {
      throw new EDMSDataGetterException(e);
    }
    if (measurements != null) {
      return measurements.get(0);
    }
    return null;
  }

  public List<EDMSMeasurement> getMeasurementsVGW(String preparedUrl) throws ClientProtocolException, IOException {
    List<EDMSMeasurement> element = null;
    String requestURL = preparedUrl;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpget = new HttpGet(requestURL);
    CloseableHttpResponse response = httpClient.execute(httpget);
    try {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        String result = EntityUtils.toString(entity);
        element = edmsMeasurementDeserializer(result);
      }
    } finally {
      response.close();
    }
    return element;
  }

  public static List<EDMSMeasurement> edmsMeasurementDeserializer(String measurementJson)
      throws JsonParseException, JsonMappingException, IOException {
    TypeFactory typeFactory = JSONMapper.objectMapper.getTypeFactory();
    List<EDMSMeasurement> elements = JSONMapper.objectMapper.readValue(measurementJson,
        typeFactory.constructCollectionType(List.class, EDMSMeasurement.class));
    return elements;
  }

}
