package com.wattics.vimsen.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JSONMapper {

  public static final ObjectMapper objectMapper;

  static {

    objectMapper = new ObjectMapper().registerModule(new JodaModule());
    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    objectMapper.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
    objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

  }
}
