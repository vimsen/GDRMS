package com.wattics.vimsen.entities;

// Generated 19-gen-2016 16.17.04 by Hibernate Tools 3.4.0.CR1

/**
 * KwhForecast generated by hbm2java
 */
public class KwhForecast implements java.io.Serializable {

  private KwhForecastId id;
  private Equipment equipment;
  private String type;
  private Double t0000;
  private Double t0015;
  private Double t0030;
  private Double t0045;
  private Double t0100;
  private Double t0115;
  private Double t0130;
  private Double t0145;
  private Double t0200;
  private Double t0215;
  private Double t0230;
  private Double t0245;
  private Double t0300;
  private Double t0315;
  private Double t0330;
  private Double t0345;
  private Double t0400;
  private Double t0415;
  private Double t0430;
  private Double t0445;
  private Double t0500;
  private Double t0515;
  private Double t0530;
  private Double t0545;
  private Double t0600;
  private Double t0615;
  private Double t0630;
  private Double t0645;
  private Double t0700;
  private Double t0715;
  private Double t0730;
  private Double t0745;
  private Double t0800;
  private Double t0815;
  private Double t0830;
  private Double t0845;
  private Double t0900;
  private Double t0915;
  private Double t0930;
  private Double t0945;
  private Double t1000;
  private Double t1015;
  private Double t1030;
  private Double t1045;
  private Double t1100;
  private Double t1115;
  private Double t1130;
  private Double t1145;
  private Double t1200;
  private Double t1215;
  private Double t1230;
  private Double t1245;
  private Double t1300;
  private Double t1315;
  private Double t1330;
  private Double t1345;
  private Double t1400;
  private Double t1415;
  private Double t1430;
  private Double t1445;
  private Double t1500;
  private Double t1515;
  private Double t1530;
  private Double t1545;
  private Double t1600;
  private Double t1615;
  private Double t1630;
  private Double t1645;
  private Double t1700;
  private Double t1715;
  private Double t1730;
  private Double t1745;
  private Double t1800;
  private Double t1815;
  private Double t1830;
  private Double t1845;
  private Double t1900;
  private Double t1915;
  private Double t1930;
  private Double t1945;
  private Double t2000;
  private Double t2015;
  private Double t2030;
  private Double t2045;
  private Double t2100;
  private Double t2115;
  private Double t2130;
  private Double t2145;
  private Double t2200;
  private Double t2215;
  private Double t2230;
  private Double t2245;
  private Double t2300;
  private Double t2315;
  private Double t2330;
  private Double t2345;

  public KwhForecast() {
  }

  public KwhForecast(KwhForecastId id, Equipment equipment) {
    this.id = id;
    this.equipment = equipment;
  }

  public KwhForecast(KwhForecastId id, Equipment equipment, String type, Double t0000, Double t0015, Double t0030, Double t0045,
      Double t0100, Double t0115, Double t0130, Double t0145, Double t0200, Double t0215, Double t0230, Double t0245,
      Double t0300, Double t0315, Double t0330, Double t0345, Double t0400, Double t0415, Double t0430, Double t0445,
      Double t0500, Double t0515, Double t0530, Double t0545, Double t0600, Double t0615, Double t0630, Double t0645,
      Double t0700, Double t0715, Double t0730, Double t0745, Double t0800, Double t0815, Double t0830, Double t0845,
      Double t0900, Double t0915, Double t0930, Double t0945, Double t1000, Double t1015, Double t1030, Double t1045,
      Double t1100, Double t1115, Double t1130, Double t1145, Double t1200, Double t1215, Double t1230, Double t1245,
      Double t1300, Double t1315, Double t1330, Double t1345, Double t1400, Double t1415, Double t1430, Double t1445,
      Double t1500, Double t1515, Double t1530, Double t1545, Double t1600, Double t1615, Double t1630, Double t1645,
      Double t1700, Double t1715, Double t1730, Double t1745, Double t1800, Double t1815, Double t1830, Double t1845,
      Double t1900, Double t1915, Double t1930, Double t1945, Double t2000, Double t2015, Double t2030, Double t2045,
      Double t2100, Double t2115, Double t2130, Double t2145, Double t2200, Double t2215, Double t2230, Double t2245,
      Double t2300, Double t2315, Double t2330, Double t2345) {
    this.id = id;
    this.equipment = equipment;
    this.type = type;
    this.t0000 = t0000;
    this.t0015 = t0015;
    this.t0030 = t0030;
    this.t0045 = t0045;
    this.t0100 = t0100;
    this.t0115 = t0115;
    this.t0130 = t0130;
    this.t0145 = t0145;
    this.t0200 = t0200;
    this.t0215 = t0215;
    this.t0230 = t0230;
    this.t0245 = t0245;
    this.t0300 = t0300;
    this.t0315 = t0315;
    this.t0330 = t0330;
    this.t0345 = t0345;
    this.t0400 = t0400;
    this.t0415 = t0415;
    this.t0430 = t0430;
    this.t0445 = t0445;
    this.t0500 = t0500;
    this.t0515 = t0515;
    this.t0530 = t0530;
    this.t0545 = t0545;
    this.t0600 = t0600;
    this.t0615 = t0615;
    this.t0630 = t0630;
    this.t0645 = t0645;
    this.t0700 = t0700;
    this.t0715 = t0715;
    this.t0730 = t0730;
    this.t0745 = t0745;
    this.t0800 = t0800;
    this.t0815 = t0815;
    this.t0830 = t0830;
    this.t0845 = t0845;
    this.t0900 = t0900;
    this.t0915 = t0915;
    this.t0930 = t0930;
    this.t0945 = t0945;
    this.t1000 = t1000;
    this.t1015 = t1015;
    this.t1030 = t1030;
    this.t1045 = t1045;
    this.t1100 = t1100;
    this.t1115 = t1115;
    this.t1130 = t1130;
    this.t1145 = t1145;
    this.t1200 = t1200;
    this.t1215 = t1215;
    this.t1230 = t1230;
    this.t1245 = t1245;
    this.t1300 = t1300;
    this.t1315 = t1315;
    this.t1330 = t1330;
    this.t1345 = t1345;
    this.t1400 = t1400;
    this.t1415 = t1415;
    this.t1430 = t1430;
    this.t1445 = t1445;
    this.t1500 = t1500;
    this.t1515 = t1515;
    this.t1530 = t1530;
    this.t1545 = t1545;
    this.t1600 = t1600;
    this.t1615 = t1615;
    this.t1630 = t1630;
    this.t1645 = t1645;
    this.t1700 = t1700;
    this.t1715 = t1715;
    this.t1730 = t1730;
    this.t1745 = t1745;
    this.t1800 = t1800;
    this.t1815 = t1815;
    this.t1830 = t1830;
    this.t1845 = t1845;
    this.t1900 = t1900;
    this.t1915 = t1915;
    this.t1930 = t1930;
    this.t1945 = t1945;
    this.t2000 = t2000;
    this.t2015 = t2015;
    this.t2030 = t2030;
    this.t2045 = t2045;
    this.t2100 = t2100;
    this.t2115 = t2115;
    this.t2130 = t2130;
    this.t2145 = t2145;
    this.t2200 = t2200;
    this.t2215 = t2215;
    this.t2230 = t2230;
    this.t2245 = t2245;
    this.t2300 = t2300;
    this.t2315 = t2315;
    this.t2330 = t2330;
    this.t2345 = t2345;
  }

  public KwhForecastId getId() {
    return this.id;
  }

  public void setId(KwhForecastId id) {
    this.id = id;
  }

  public Equipment getEquipment() {
    return this.equipment;
  }

  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getT0000() {
    return this.t0000;
  }

  public void setT0000(Double t0000) {
    this.t0000 = t0000;
  }

  public Double getT0015() {
    return this.t0015;
  }

  public void setT0015(Double t0015) {
    this.t0015 = t0015;
  }

  public Double getT0030() {
    return this.t0030;
  }

  public void setT0030(Double t0030) {
    this.t0030 = t0030;
  }

  public Double getT0045() {
    return this.t0045;
  }

  public void setT0045(Double t0045) {
    this.t0045 = t0045;
  }

  public Double getT0100() {
    return this.t0100;
  }

  public void setT0100(Double t0100) {
    this.t0100 = t0100;
  }

  public Double getT0115() {
    return this.t0115;
  }

  public void setT0115(Double t0115) {
    this.t0115 = t0115;
  }

  public Double getT0130() {
    return this.t0130;
  }

  public void setT0130(Double t0130) {
    this.t0130 = t0130;
  }

  public Double getT0145() {
    return this.t0145;
  }

  public void setT0145(Double t0145) {
    this.t0145 = t0145;
  }

  public Double getT0200() {
    return this.t0200;
  }

  public void setT0200(Double t0200) {
    this.t0200 = t0200;
  }

  public Double getT0215() {
    return this.t0215;
  }

  public void setT0215(Double t0215) {
    this.t0215 = t0215;
  }

  public Double getT0230() {
    return this.t0230;
  }

  public void setT0230(Double t0230) {
    this.t0230 = t0230;
  }

  public Double getT0245() {
    return this.t0245;
  }

  public void setT0245(Double t0245) {
    this.t0245 = t0245;
  }

  public Double getT0300() {
    return this.t0300;
  }

  public void setT0300(Double t0300) {
    this.t0300 = t0300;
  }

  public Double getT0315() {
    return this.t0315;
  }

  public void setT0315(Double t0315) {
    this.t0315 = t0315;
  }

  public Double getT0330() {
    return this.t0330;
  }

  public void setT0330(Double t0330) {
    this.t0330 = t0330;
  }

  public Double getT0345() {
    return this.t0345;
  }

  public void setT0345(Double t0345) {
    this.t0345 = t0345;
  }

  public Double getT0400() {
    return this.t0400;
  }

  public void setT0400(Double t0400) {
    this.t0400 = t0400;
  }

  public Double getT0415() {
    return this.t0415;
  }

  public void setT0415(Double t0415) {
    this.t0415 = t0415;
  }

  public Double getT0430() {
    return this.t0430;
  }

  public void setT0430(Double t0430) {
    this.t0430 = t0430;
  }

  public Double getT0445() {
    return this.t0445;
  }

  public void setT0445(Double t0445) {
    this.t0445 = t0445;
  }

  public Double getT0500() {
    return this.t0500;
  }

  public void setT0500(Double t0500) {
    this.t0500 = t0500;
  }

  public Double getT0515() {
    return this.t0515;
  }

  public void setT0515(Double t0515) {
    this.t0515 = t0515;
  }

  public Double getT0530() {
    return this.t0530;
  }

  public void setT0530(Double t0530) {
    this.t0530 = t0530;
  }

  public Double getT0545() {
    return this.t0545;
  }

  public void setT0545(Double t0545) {
    this.t0545 = t0545;
  }

  public Double getT0600() {
    return this.t0600;
  }

  public void setT0600(Double t0600) {
    this.t0600 = t0600;
  }

  public Double getT0615() {
    return this.t0615;
  }

  public void setT0615(Double t0615) {
    this.t0615 = t0615;
  }

  public Double getT0630() {
    return this.t0630;
  }

  public void setT0630(Double t0630) {
    this.t0630 = t0630;
  }

  public Double getT0645() {
    return this.t0645;
  }

  public void setT0645(Double t0645) {
    this.t0645 = t0645;
  }

  public Double getT0700() {
    return this.t0700;
  }

  public void setT0700(Double t0700) {
    this.t0700 = t0700;
  }

  public Double getT0715() {
    return this.t0715;
  }

  public void setT0715(Double t0715) {
    this.t0715 = t0715;
  }

  public Double getT0730() {
    return this.t0730;
  }

  public void setT0730(Double t0730) {
    this.t0730 = t0730;
  }

  public Double getT0745() {
    return this.t0745;
  }

  public void setT0745(Double t0745) {
    this.t0745 = t0745;
  }

  public Double getT0800() {
    return this.t0800;
  }

  public void setT0800(Double t0800) {
    this.t0800 = t0800;
  }

  public Double getT0815() {
    return this.t0815;
  }

  public void setT0815(Double t0815) {
    this.t0815 = t0815;
  }

  public Double getT0830() {
    return this.t0830;
  }

  public void setT0830(Double t0830) {
    this.t0830 = t0830;
  }

  public Double getT0845() {
    return this.t0845;
  }

  public void setT0845(Double t0845) {
    this.t0845 = t0845;
  }

  public Double getT0900() {
    return this.t0900;
  }

  public void setT0900(Double t0900) {
    this.t0900 = t0900;
  }

  public Double getT0915() {
    return this.t0915;
  }

  public void setT0915(Double t0915) {
    this.t0915 = t0915;
  }

  public Double getT0930() {
    return this.t0930;
  }

  public void setT0930(Double t0930) {
    this.t0930 = t0930;
  }

  public Double getT0945() {
    return this.t0945;
  }

  public void setT0945(Double t0945) {
    this.t0945 = t0945;
  }

  public Double getT1000() {
    return this.t1000;
  }

  public void setT1000(Double t1000) {
    this.t1000 = t1000;
  }

  public Double getT1015() {
    return this.t1015;
  }

  public void setT1015(Double t1015) {
    this.t1015 = t1015;
  }

  public Double getT1030() {
    return this.t1030;
  }

  public void setT1030(Double t1030) {
    this.t1030 = t1030;
  }

  public Double getT1045() {
    return this.t1045;
  }

  public void setT1045(Double t1045) {
    this.t1045 = t1045;
  }

  public Double getT1100() {
    return this.t1100;
  }

  public void setT1100(Double t1100) {
    this.t1100 = t1100;
  }

  public Double getT1115() {
    return this.t1115;
  }

  public void setT1115(Double t1115) {
    this.t1115 = t1115;
  }

  public Double getT1130() {
    return this.t1130;
  }

  public void setT1130(Double t1130) {
    this.t1130 = t1130;
  }

  public Double getT1145() {
    return this.t1145;
  }

  public void setT1145(Double t1145) {
    this.t1145 = t1145;
  }

  public Double getT1200() {
    return this.t1200;
  }

  public void setT1200(Double t1200) {
    this.t1200 = t1200;
  }

  public Double getT1215() {
    return this.t1215;
  }

  public void setT1215(Double t1215) {
    this.t1215 = t1215;
  }

  public Double getT1230() {
    return this.t1230;
  }

  public void setT1230(Double t1230) {
    this.t1230 = t1230;
  }

  public Double getT1245() {
    return this.t1245;
  }

  public void setT1245(Double t1245) {
    this.t1245 = t1245;
  }

  public Double getT1300() {
    return this.t1300;
  }

  public void setT1300(Double t1300) {
    this.t1300 = t1300;
  }

  public Double getT1315() {
    return this.t1315;
  }

  public void setT1315(Double t1315) {
    this.t1315 = t1315;
  }

  public Double getT1330() {
    return this.t1330;
  }

  public void setT1330(Double t1330) {
    this.t1330 = t1330;
  }

  public Double getT1345() {
    return this.t1345;
  }

  public void setT1345(Double t1345) {
    this.t1345 = t1345;
  }

  public Double getT1400() {
    return this.t1400;
  }

  public void setT1400(Double t1400) {
    this.t1400 = t1400;
  }

  public Double getT1415() {
    return this.t1415;
  }

  public void setT1415(Double t1415) {
    this.t1415 = t1415;
  }

  public Double getT1430() {
    return this.t1430;
  }

  public void setT1430(Double t1430) {
    this.t1430 = t1430;
  }

  public Double getT1445() {
    return this.t1445;
  }

  public void setT1445(Double t1445) {
    this.t1445 = t1445;
  }

  public Double getT1500() {
    return this.t1500;
  }

  public void setT1500(Double t1500) {
    this.t1500 = t1500;
  }

  public Double getT1515() {
    return this.t1515;
  }

  public void setT1515(Double t1515) {
    this.t1515 = t1515;
  }

  public Double getT1530() {
    return this.t1530;
  }

  public void setT1530(Double t1530) {
    this.t1530 = t1530;
  }

  public Double getT1545() {
    return this.t1545;
  }

  public void setT1545(Double t1545) {
    this.t1545 = t1545;
  }

  public Double getT1600() {
    return this.t1600;
  }

  public void setT1600(Double t1600) {
    this.t1600 = t1600;
  }

  public Double getT1615() {
    return this.t1615;
  }

  public void setT1615(Double t1615) {
    this.t1615 = t1615;
  }

  public Double getT1630() {
    return this.t1630;
  }

  public void setT1630(Double t1630) {
    this.t1630 = t1630;
  }

  public Double getT1645() {
    return this.t1645;
  }

  public void setT1645(Double t1645) {
    this.t1645 = t1645;
  }

  public Double getT1700() {
    return this.t1700;
  }

  public void setT1700(Double t1700) {
    this.t1700 = t1700;
  }

  public Double getT1715() {
    return this.t1715;
  }

  public void setT1715(Double t1715) {
    this.t1715 = t1715;
  }

  public Double getT1730() {
    return this.t1730;
  }

  public void setT1730(Double t1730) {
    this.t1730 = t1730;
  }

  public Double getT1745() {
    return this.t1745;
  }

  public void setT1745(Double t1745) {
    this.t1745 = t1745;
  }

  public Double getT1800() {
    return this.t1800;
  }

  public void setT1800(Double t1800) {
    this.t1800 = t1800;
  }

  public Double getT1815() {
    return this.t1815;
  }

  public void setT1815(Double t1815) {
    this.t1815 = t1815;
  }

  public Double getT1830() {
    return this.t1830;
  }

  public void setT1830(Double t1830) {
    this.t1830 = t1830;
  }

  public Double getT1845() {
    return this.t1845;
  }

  public void setT1845(Double t1845) {
    this.t1845 = t1845;
  }

  public Double getT1900() {
    return this.t1900;
  }

  public void setT1900(Double t1900) {
    this.t1900 = t1900;
  }

  public Double getT1915() {
    return this.t1915;
  }

  public void setT1915(Double t1915) {
    this.t1915 = t1915;
  }

  public Double getT1930() {
    return this.t1930;
  }

  public void setT1930(Double t1930) {
    this.t1930 = t1930;
  }

  public Double getT1945() {
    return this.t1945;
  }

  public void setT1945(Double t1945) {
    this.t1945 = t1945;
  }

  public Double getT2000() {
    return this.t2000;
  }

  public void setT2000(Double t2000) {
    this.t2000 = t2000;
  }

  public Double getT2015() {
    return this.t2015;
  }

  public void setT2015(Double t2015) {
    this.t2015 = t2015;
  }

  public Double getT2030() {
    return this.t2030;
  }

  public void setT2030(Double t2030) {
    this.t2030 = t2030;
  }

  public Double getT2045() {
    return this.t2045;
  }

  public void setT2045(Double t2045) {
    this.t2045 = t2045;
  }

  public Double getT2100() {
    return this.t2100;
  }

  public void setT2100(Double t2100) {
    this.t2100 = t2100;
  }

  public Double getT2115() {
    return this.t2115;
  }

  public void setT2115(Double t2115) {
    this.t2115 = t2115;
  }

  public Double getT2130() {
    return this.t2130;
  }

  public void setT2130(Double t2130) {
    this.t2130 = t2130;
  }

  public Double getT2145() {
    return this.t2145;
  }

  public void setT2145(Double t2145) {
    this.t2145 = t2145;
  }

  public Double getT2200() {
    return this.t2200;
  }

  public void setT2200(Double t2200) {
    this.t2200 = t2200;
  }

  public Double getT2215() {
    return this.t2215;
  }

  public void setT2215(Double t2215) {
    this.t2215 = t2215;
  }

  public Double getT2230() {
    return this.t2230;
  }

  public void setT2230(Double t2230) {
    this.t2230 = t2230;
  }

  public Double getT2245() {
    return this.t2245;
  }

  public void setT2245(Double t2245) {
    this.t2245 = t2245;
  }

  public Double getT2300() {
    return this.t2300;
  }

  public void setT2300(Double t2300) {
    this.t2300 = t2300;
  }

  public Double getT2315() {
    return this.t2315;
  }

  public void setT2315(Double t2315) {
    this.t2315 = t2315;
  }

  public Double getT2330() {
    return this.t2330;
  }

  public void setT2330(Double t2330) {
    this.t2330 = t2330;
  }

  public Double getT2345() {
    return this.t2345;
  }

  public void setT2345(Double t2345) {
    this.t2345 = t2345;
  }

}
