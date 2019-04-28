package com.sart.core.classifications.dao.impl;

import static java.lang.String.format;

import com.sart.core.classifications.dao.SartoriusClassificationDAO;
import com.sart.core.model.ClassificationModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import java.util.Collections;
import org.apache.log4j.Logger;

/**
 * @author Created by Maxim.Olkhin@bridge-x.com on 28.04.2019.
 */
public class DefaultSartoriusClassificationDAO extends AbstractItemDao implements SartoriusClassificationDAO {

  private static final Logger LOG = Logger.getLogger(DefaultSartoriusClassificationDAO.class);

  private static final String GET_BY_NAME_QUERY = "SELECT {pk} FROM {Classification} WHERE {name} = ?name";

  @Override
  public ClassificationModel getByName(String name) {
    SearchResult<ClassificationModel> searchResult = this.getFlexibleSearchService().search(GET_BY_NAME_QUERY, Collections.singletonMap("name", name));
    ClassificationModel result = null;
    if (!searchResult.getResult().isEmpty()) {
      result = searchResult.getResult().get(0);
    } else {
      LOG.warn(format("No Classification item was found with search name: %s", name));
    }
    return result;
  }
}
