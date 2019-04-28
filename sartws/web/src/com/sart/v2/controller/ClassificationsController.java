package com.sart.v2.controller;

import com.sart.facades.classifications.SartoriusClassificationFacade;
import de.hybris.platform.catalog.CatalogVersionService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Web Services Controller for product's classifications.
 *
 * @author Created by Maxim.Olkhin@bridge-x.com on 27.04.2019.
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/classifications")
public class ClassificationsController extends BaseController {

  private static final Logger LOG = Logger.getLogger(ClassificationsController.class);

  @Resource
  private SartoriusClassificationFacade sartoriusClassificationFacade;

  @Resource
  private CatalogVersionService catalogVersionService;

  /**
   * Update product classifications
   *
   * @param productCode product code
   * @param classifications list of classification name
   */
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/product/{productCode}", method = RequestMethod.POST)
  @ResponseBody
  public void updateClassifications(
      @PathVariable final String productCode,
      @RequestParam final List<String> classifications) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("updateClassifications: product code=" + sanitize(productCode) + " | classifications=" + classifications);
    }
    catalogVersionService.setSessionCatalogVersion("electronicsProductCatalog", "Staged");
    sartoriusClassificationFacade.updateForProduct(productCode, classifications);
  }

  /**
   * Delete classification by name
   *
   * @param classification classification name
   */
  @RequestMapping(value = "/{classification}", method = RequestMethod.DELETE)
  @ResponseBody
  public void deleteClassifications(
      @PathVariable final String classification) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("deleteClassifications: classification=" + sanitize(classification));
    }
    sartoriusClassificationFacade.deleteByName(classification);
  }

  /**
   * Create classification by name
   *
   * @param classification classification name
   */
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/{classification}", method = RequestMethod.POST)
  @ResponseBody
  public void createClassifications(
      @PathVariable final String classification) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("createClassifications: classification=" + sanitize(classification));
    }
    sartoriusClassificationFacade.create(classification);
  }
}
