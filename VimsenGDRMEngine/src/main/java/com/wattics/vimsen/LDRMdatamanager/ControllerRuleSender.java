package com.wattics.vimsen.LDRMdatamanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterException;
import com.wattics.vimsen.EDMSdatamanager.EDMSDataGetterInterface;
import com.wattics.vimsen.GDRMdatamanager.GDRMDataGetterInterface;
import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.entities.Plan;
import com.wattics.vimsen.utils.MapperException;

public class ControllerRuleSender implements ControllerRuleSenderInterface {

  private static String consumptionRuleUrl = "http://vimsen.telint.eu/Rest/SetConsumptionRule/";

  public String sendNewConsumptionRule(String ruleJson) throws LDRMRuleException {
    String response = null;
    try {
      response = postRule(ruleJson);
    } catch (KeyManagementException e) {
      e.printStackTrace();
      throw new LDRMRuleException(e);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
      throw new LDRMRuleException(e);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new LDRMRuleException(e);
    } catch (IOException e) {
      e.printStackTrace();
      throw new LDRMRuleException(e);
    }
    return response;
  }

  private static String postRule(String ruleJson)
      throws ClientProtocolException, IOException, NoSuchAlgorithmException, KeyManagementException {

    HttpClient client = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost(consumptionRuleUrl);

    // add header
    post.setHeader("AccessKey", "9zSLIWmfQy59i7PCAVvrL5g3iYUHrw/CtoHI2/wM+vg=");

    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    nvps.add(new BasicNameValuePair("rule", ruleJson));
    post.setEntity(new UrlEncodedFormEntity(nvps));
    HttpResponse response = client.execute(post);

    System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

    StringBuffer result = new StringBuffer();
    String line = "";
    while ((line = rd.readLine()) != null) {
      result.append(line);
    }
    System.out.println("Response message : " + result);
    return result.toString();
  }

  public static List<String> generatePlansControllerRulesMap(List<Integer> plansId, EDMSDataGetterInterface edmsDataGetter,
      GDRMDataGetterInterface dataGetter) throws DataAccessLayerException, MapperException, EDMSDataGetterException {
    List<String> actionRulesJsonAll = new ArrayList<String>();
    for (int planId : plansId) {
      Plan plan = dataGetter.getPlan(planId);
      String actionsRulesJson = ControllerRuleMapper.mapPlanActionsInControllerRulesJsonMap(plan, edmsDataGetter, dataGetter);
      actionRulesJsonAll.add(actionsRulesJson);
    }
    return actionRulesJsonAll;
  }

}
