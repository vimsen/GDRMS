package com.wattics.vimsen.EDMSdatamanager;

public interface EDMSDataGetterInterface {

  public EDMSMeasurement getEDMSMeasurementFromVGWUrl(String urlRequest) throws EDMSDataGetterException;
}
