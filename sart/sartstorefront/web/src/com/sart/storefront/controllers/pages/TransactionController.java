package com.sart.storefront.controllers.pages;

import com.sart.facades.transaction.TransactionFacade;
import com.sart.storefront.controllers.ControllerConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * This controller retrieving all transactions.
 */
@Controller
public class TransactionController extends AbstractController {

    @Resource
    private TransactionFacade transactionFacade;

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public String allTransactions(final Model model) {
        model.addAttribute("transactions", transactionFacade.getAllTransactions());
        return ControllerConstants.Views.Pages.Transaction.TransactionPage;
    }

}
