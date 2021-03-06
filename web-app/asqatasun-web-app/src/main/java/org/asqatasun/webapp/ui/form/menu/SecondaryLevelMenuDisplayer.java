/*
 *  Asqatasun - Automated webpage assessment
 *  Copyright (C) 2008-2019  Asqatasun.org
 * 
 *  This file is part of Asqatasun.
 * 
 *  Asqatasun is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  Contact us by mail: asqatasun AT asqatasun DOT org
 */

package org.asqatasun.webapp.ui.form.menu;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.asqatasun.entity.contract.Contract;
import org.asqatasun.entity.referential.Referential;
import org.asqatasun.entity.service.contract.ContractDataService;
import org.asqatasun.entity.user.User;
import org.asqatasun.webapp.util.TgolKeyStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Some pages need to display a second level menu. This class enables to determine
 * when this menu has to be displayed and populated a model with the 
 * appropriate data for the menu to be displayed
 * 
 * @author jkowalczyk
 */
@Component("secondaryLevelMenuDisplayer")
public class SecondaryLevelMenuDisplayer {

    @Value("${app.webapp.ui.config.referentialWithModifiableTestWeight}")
    List<String> referentialWithModifiableTestWeight;
    private ContractDataService contractDataService;

    public SecondaryLevelMenuDisplayer(ContractDataService contractDataService) {
        this.contractDataService = contractDataService;
    }

    /**
     * If the current user owns contracts with modifiable test weight 
     * referentials, the link to the modification pages has to be displayed. 
     * The different jsp checks the presence of the MODIFIABLE_TEST_WEIGHT_REFS_KEY
     * in the model to determine whether the menu has to be displayed and to 
     * get the needed data.
     * 
     * @param user
     * @param model
     * @return 
     */
    public void setModifiableReferentialsForUserToModel(
            User user, 
            Model model) {
        Collection<String> refList = new HashSet<>();
        for(Contract contract : contractDataService.getAllContractsByUser(user)) {
            for (Referential ref : contract.getReferentialSet()) {
                if (referentialWithModifiableTestWeight.contains(ref.getCode())) {
                    refList.add(ref.getCode());
                }
            }
        }
        if (!refList.isEmpty()) {
            model.addAttribute(TgolKeyStore.MODIFIABLE_TEST_WEIGHT_REFS_KEY, refList);
        }
    }

    /**
     * 
     * @param refCode
     * @return 
     */
    public boolean isRequestedReferentialModifiable(String refCode) {
        return referentialWithModifiableTestWeight.contains(refCode);
    }
    
}
