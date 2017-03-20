package com.wattics.vimsen.GDRMdatamanager;

import java.util.List;

import org.joda.time.DateTime;

import com.wattics.vimsen.dbDAO.DataAccessLayerException;
import com.wattics.vimsen.dbDAO.HQLServices;
import com.wattics.vimsen.dbDAO.HibernateUtil;
import com.wattics.vimsen.entities.Action;
import com.wattics.vimsen.entities.ActionSla;
import com.wattics.vimsen.entities_services.ActionService;
import com.wattics.vimsen.entities_services.ActionSlaService;

public class DatabaseSetUpHelper {

  public static void addActionSla(HibernateUtil hibernateUtil) throws DataAccessLayerException {

    HQLServices hqlServices = new HQLServices(hibernateUtil);
    hqlServices.hqlTruncate("ActionSla");

    ActionService actionService = new ActionService(hibernateUtil);
    List<Action> actions = (List<Action>) actionService.findAll();

    for (int i = 0; i < actions.size(); i++) {
      Action action = actions.get(i);
      ActionSla actionSla = new ActionSla(action);
      actionSla.setConsumptionTarget(i * 4.0 + 1);
      actionSla.setStartResponsePeriod(new DateTime("2016-05-10T23:00:00"));
      actionSla.setEndResponsePeriod(new DateTime("2016-05-10T21:00:00"));
      ActionSlaService actionslaService = new ActionSlaService(hibernateUtil);
      actionslaService.insert(actionSla);
    }
  }
}
