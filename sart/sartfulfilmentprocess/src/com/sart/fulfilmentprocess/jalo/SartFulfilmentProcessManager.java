/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.sart.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.sart.fulfilmentprocess.constants.SartFulfilmentProcessConstants;

public class SartFulfilmentProcessManager extends GeneratedSartFulfilmentProcessManager
{
	public static final SartFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SartFulfilmentProcessManager) em.getExtension(SartFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
