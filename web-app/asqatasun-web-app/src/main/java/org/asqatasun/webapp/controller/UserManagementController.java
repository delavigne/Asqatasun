/*
 * Asqatasun - Automated webpage assessment 
 * Copyright (C) 2008-2019  Asqatasun.org
 *
 * This file is part of Asqatasun.
 *
 * Asqatasun is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact us by mail: asqatasun AT asqatasun DOT org
 */
package org.asqatasun.webapp.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.asqatasun.util.MD5Encoder;
import org.asqatasun.webapp.command.CreateContractCommand;
import org.asqatasun.webapp.command.CreateUserCommand;
import org.asqatasun.webapp.command.factory.CreateUserCommandFactory;
import org.asqatasun.entity.contract.Contract;
import org.asqatasun.entity.service.contract.ContractDataService;
import org.asqatasun.entity.user.User;
import org.asqatasun.webapp.exception.ForbiddenUserException;
import org.asqatasun.webapp.ui.form.parameterization.ContractOptionFormField;
import org.asqatasun.webapp.ui.form.parameterization.helper.ContractOptionFormFieldHelper;
import org.asqatasun.webapp.util.TgolKeyStore;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author jkowalczyk
 */
@Controller
public class UserManagementController extends AbstractUserAndContractsController {

    private final ContractDataService contractDataService;
    private final CreateUserCommandFactory createUserCommandFactory;

    public UserManagementController(ContractDataService contractDataService,
                                    CreateUserCommandFactory createUserCommandFactory) {
        super();
        this.contractDataService = contractDataService;
        this.createUserCommandFactory = createUserCommandFactory;
    }

    @InitBinder
    @Override
    protected void initBinder(WebDataBinder binder) {
        super.initBinder(binder);
        binder.registerCustomEditor(Collection.class, "userList", new CustomCollectionEditor(Collection.class) {

            @Override
            protected Object convertElement(Object element) {
                Long id = null;

                if (element instanceof String && !((String) element).equals("")) {
                    //From the JSP 'element' will be a String
                    try {
                        id = Long.parseLong((String) element);
                    } catch (NumberFormatException e) {
                        LoggerFactory.getLogger(this.getClass()).warn("Element was " + element);
                    }
                } else if (element instanceof Long) {
                    //From the database 'element' will be a Long
                    id = (Long) element;
                }

                return id != null ? userDataService.read(id) : null;
            }
        });
    }

    /**
     * @param request
     * @param response
     * @param model
     * @return The pages audit set-up form page
     */
    @RequestMapping(value = TgolKeyStore.ADMIN_URL, method = RequestMethod.GET)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String displayAdminPage(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        model.addAttribute(TgolKeyStore.USER_LIST_KEY, userDataService.findAll());
        // Due to different redirection that can lead to this page, we need
        // to test the different session attribute to display an appropriate
        // message and thus clean up the session with uneeded attributes
        if (request.getSession().getAttribute(TgolKeyStore.DELETED_USER_NAME_KEY) != null) {
            model.addAttribute(TgolKeyStore.DELETED_USER_NAME_KEY,
                    request.getSession().getAttribute(TgolKeyStore.DELETED_USER_NAME_KEY));
            request.getSession().removeAttribute(TgolKeyStore.DELETED_USER_NAME_KEY);
        }
        if (request.getSession().getAttribute(TgolKeyStore.DELETED_USER_AUDITS_KEY) != null) {
            model.addAttribute(TgolKeyStore.DELETED_USER_AUDITS_KEY,
                    request.getSession().getAttribute(TgolKeyStore.DELETED_USER_AUDITS_KEY));
            request.getSession().removeAttribute(TgolKeyStore.DELETED_USER_AUDITS_KEY);
        }
        if (request.getSession().getAttribute(TgolKeyStore.UPDATED_USER_NAME_KEY) != null) {
            model.addAttribute(TgolKeyStore.UPDATED_USER_NAME_KEY,
                    request.getSession().getAttribute(TgolKeyStore.UPDATED_USER_NAME_KEY));
            request.getSession().removeAttribute(TgolKeyStore.UPDATED_USER_NAME_KEY);
        }
        if (request.getSession().getAttribute(TgolKeyStore.ADDED_USER_NAME_KEY) != null) {
            model.addAttribute(TgolKeyStore.ADDED_USER_NAME_KEY,
                    request.getSession().getAttribute(TgolKeyStore.ADDED_USER_NAME_KEY));
            request.getSession().removeAttribute(TgolKeyStore.UPDATED_USER_NAME_KEY);
        }
        if (request.getSession().getAttribute(TgolKeyStore.ADDED_CONTRACT_NAME_KEY) != null && 
                request.getSession().getAttribute(TgolKeyStore.ADDED_CONTRACT_USERS_NAME_KEY) != null) {
            model.addAttribute(TgolKeyStore.ADDED_CONTRACT_NAME_KEY,
                    request.getSession().getAttribute(TgolKeyStore.ADDED_CONTRACT_NAME_KEY));
            model.addAttribute(TgolKeyStore.ADDED_CONTRACT_USERS_NAME_KEY,
                    request.getSession().getAttribute(TgolKeyStore.ADDED_CONTRACT_USERS_NAME_KEY));
            request.getSession().removeAttribute(TgolKeyStore.ADDED_CONTRACT_USERS_NAME_KEY);
            request.getSession().removeAttribute(TgolKeyStore.ADDED_CONTRACT_NAME_KEY);
        }
        return TgolKeyStore.ADMIN_VIEW_NAME;
    }

    /**
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return The pages audit set-up form page
     */
    @RequestMapping(value = TgolKeyStore.EDIT_USER_URL, method = RequestMethod.GET)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String displayEditUserAdminPage(
            @RequestParam(TgolKeyStore.USER_ID_KEY) String userId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        Long lUserId;
        try {
            lUserId = Long.valueOf(userId);
        } catch (NumberFormatException nfe) {
            throw new ForbiddenUserException();
        }
        User userToModify = userDataService.read(lUserId);
        model.addAttribute(TgolKeyStore.USER_NAME_KEY, userToModify.getEmail1());
        request.getSession().setAttribute(TgolKeyStore.USER_ID_KEY, lUserId);
        return prepateDataAndReturnCreateUserView(
                model,
                userToModify,
                TgolKeyStore.EDIT_USER_VIEW_NAME);
    }

    /**
     * This methods controls the validity of the form and launch an audit with
     * values populated by the user. In case of audit failure, an appropriate
     * message is displayed
     *
     * @param createUserCommand
     * @param result
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = TgolKeyStore.EDIT_USER_URL, method = RequestMethod.POST)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    protected String submitEditUserForm(
            @ModelAttribute(TgolKeyStore.CREATE_USER_COMMAND_KEY) CreateUserCommand createUserCommand,
            BindingResult result,
            HttpServletRequest request,
            Model model)
            throws Exception {
        Long userId;
        try {
            userId = (Long) (request.getSession().getAttribute(TgolKeyStore.USER_ID_KEY));
        } catch (NumberFormatException nfe) {
            throw new ForbiddenUserException();
        }
        boolean updateAllData = true;
        if (getCurrentUser().getId().equals(userId)) {
            updateAllData = false;
        }
        return submitUpdateUserForm(
                createUserCommand,
                result,
                request,
                model,
                userDataService.read(userId),
                TgolKeyStore.ADMIN_VIEW_NAME,
                TgolKeyStore.EDIT_USER_VIEW_NAME,
                updateAllData,
                true,
                TgolKeyStore.UPDATED_USER_NAME_KEY);
    }

    /**
     * @param request
     * @param response
     * @param model
     * @return The pages audit set-up form page
     */
    @RequestMapping(value = TgolKeyStore.ADD_USER_URL, method = RequestMethod.GET)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String displayAddUserAdminPage(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        return prepateDataAndReturnCreateUserView(
                model,
                null,
                TgolKeyStore.ADD_USER_VIEW_NAME);
    }

    /**
     * This methods controls the validity of the form and launch an audit with
     * values populated by the user. In case of audit failure, an appropriate
     * message is displayed
     *
     * @param createUserCommand
     * @param result
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = TgolKeyStore.ADD_USER_URL, method = RequestMethod.POST)
    protected String submitAddUserForm(
            @ModelAttribute(TgolKeyStore.CREATE_USER_COMMAND_KEY) CreateUserCommand createUserCommand,
            BindingResult result,
            Model model)
            throws Exception {
        return submitCreateUserForm(
                createUserCommand,
                result,
                model,
                TgolKeyStore.ADMIN_VIEW_NAME,
                TgolKeyStore.ADD_USER_VIEW_NAME,
                TgolKeyStore.ADDED_USER_NAME_KEY);
    }

    /**
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return The pages audit set-up form page
     */
    @RequestMapping(value = TgolKeyStore.DELETE_USER_URL, method = RequestMethod.GET)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String displayDeleteUserPage(
            @RequestParam(TgolKeyStore.USER_ID_KEY) String userId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        Long lUserId;
        try {
            lUserId = Long.valueOf(userId);
        } catch (NumberFormatException nfe) {
            throw new ForbiddenUserException();
        }
        User userToDelete = userDataService.read(lUserId);
        if (userToDelete == null || getCurrentUser().getId().equals(userToDelete.getId())) {
            return TgolKeyStore.ACCESS_DENIED_VIEW_NAME;
        }
        model.addAttribute(TgolKeyStore.USER_NAME_TO_DELETE_KEY, userToDelete.getEmail1());
        request.getSession().setAttribute(TgolKeyStore.USER_ID_TO_DELETE_KEY, userToDelete.getId());
        return TgolKeyStore.DELETE_USER_VIEW_NAME;
    }

    /**
     * @param request
     * @param response
     * @param model
     * @return The pages audit set-up form page
     */
    @RequestMapping(value = TgolKeyStore.DELETE_USER_URL, method = RequestMethod.POST)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String displayDeleteUserConfirmation(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        Object userId = request.getSession().getAttribute(TgolKeyStore.USER_ID_TO_DELETE_KEY);
        Long lUserId;
        if (userId instanceof Long) {
            lUserId = (Long) userId;
        } else {
            try {
                lUserId = Long.valueOf(userId.toString());
            } catch (NumberFormatException nfe) {
                throw new ForbiddenUserException();
            }
        }
        User user = getCurrentUser();
        User userToDelete = userDataService.read(lUserId);
        if (userToDelete == null || user.getId().equals(userToDelete.getId())) {
            return TgolKeyStore.ACCESS_DENIED_VIEW_NAME;
        }
        for (Contract contract : userToDelete.getContractSet()) {
            deleteAllAuditsFromContract(contract);
        }
        userDataService.delete(userToDelete.getId());
        request.getSession().removeAttribute(TgolKeyStore.USER_ID_TO_DELETE_KEY);
        request.getSession().setAttribute(TgolKeyStore.DELETED_USER_NAME_KEY, userToDelete.getEmail1());
        return TgolKeyStore.ADMIN_VIEW_REDIRECT_NAME;
    }

    /**
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = TgolKeyStore.DELETE_USER_AUDITS_URL, method = RequestMethod.GET)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String displayDeleteUserAuditsPage(
            @RequestParam(TgolKeyStore.USER_ID_KEY) String userId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        Long lUserId;
        try {
            lUserId = Long.valueOf(userId);
        } catch (NumberFormatException nfe) {
            throw new ForbiddenUserException();
        }
        
        User userToDelete = userDataService.read(lUserId);
        model.addAttribute(TgolKeyStore.USER_NAME_TO_DELETE_KEY, userToDelete.getEmail1());
        request.getSession().setAttribute(TgolKeyStore.USER_ID_TO_DELETE_KEY, userToDelete.getId());
        return TgolKeyStore.DELETE_AUDITS_VIEW_NAME;
    }

    /**
     * @param request
     * @param response
     * @param model
     * @return the name of the view that displays the confirmation page 
         * when trying to delete all the audits of a user
     */
    @RequestMapping(value = TgolKeyStore.DELETE_USER_AUDITS_URL, method = RequestMethod.POST)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String displayDeleteUserAuditsConfirmationPage(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        Object userId = request.getSession().getAttribute(TgolKeyStore.USER_ID_TO_DELETE_KEY);
        Long lUserId;
        if (userId instanceof Long) {
            lUserId = (Long) userId;
        } else {
            try {
                lUserId = Long.valueOf(userId.toString());
            } catch (NumberFormatException nfe) {
                throw new ForbiddenUserException();
            }
        }

        User userToDelete = userDataService.read(lUserId);
        for (Contract contract : userToDelete.getContractSet()) {
            deleteAllAuditsFromContract(contract);
        }
        request.getSession().removeAttribute(TgolKeyStore.USER_ID_TO_DELETE_KEY);
        request.getSession().setAttribute(TgolKeyStore.DELETED_USER_AUDITS_KEY, userToDelete.getEmail1());
        return TgolKeyStore.ADMIN_VIEW_REDIRECT_NAME;
    }

    /**
     * @param request
     * @param response
     * @param model
     * @return The pages audit set-up form page
     */
    @RequestMapping(value = TgolKeyStore.ADD_CONTRACT_URL, method = RequestMethod.GET)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String displayAddContractAdminPage(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {

        return prepateDataAndReturnCreateContractView(
                model,
                null,
                null,
                ContractOptionFormFieldHelper.getFreshContractOptionFormFieldMap(contractOptionFormFieldBuilderMap),
                TgolKeyStore.ADD_CONTRACT_VIEW_NAME);
    }

    /**
     * @param ccc the CreateContractCommand 
     * @param result
     * @param request
     * @param response
     * @param model
     * @return The pages audit set-up form page
     */
    @RequestMapping(value = TgolKeyStore.ADD_CONTRACT_URL, method = RequestMethod.POST)
    @Secured(TgolKeyStore.ROLE_ADMIN_KEY)
    public String submitAddContractAdminPage(
            @ModelAttribute(TgolKeyStore.CREATE_CONTRACT_COMMAND_KEY) CreateContractCommand ccc,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {

        Map<String, List<ContractOptionFormField>> optionFormFieldMap =
                ContractOptionFormFieldHelper.getFreshContractOptionFormFieldMap(contractOptionFormFieldBuilderMap);

        createContractFormValidator.setContractOptionFormFieldMap(optionFormFieldMap);
        // We check whether the form is valid
        createContractFormValidator.validateMultipleUsers(ccc, result);
        if (result.hasErrors()) {
            return displayFormWithErrors(
                    model,
                    ccc,
                    null,
                    null,
                    optionFormFieldMap,
                    TgolKeyStore.ADD_CONTRACT_VIEW_NAME);
        }
        Collection<User> userList = ccc.getUserList();
        StringBuilder strb = new StringBuilder();
        for (User user : userList) {
            if (user != null) {
                Contract contract = contractDataService.create();
                contract.setUser(user);
                contract = createContractCommandFactory.updateContractFromCommand(ccc, contract);
                contractDataService.saveOrUpdate(contract);
                strb.append(user.getEmail1());
                strb.append(", ");
            }
        }
        
        request.getSession().setAttribute(TgolKeyStore.ADDED_CONTRACT_NAME_KEY,ccc.getLabel());
        request.getSession().setAttribute(TgolKeyStore.ADDED_CONTRACT_USERS_NAME_KEY,strb.toString());
        return TgolKeyStore.ADMIN_VIEW_REDIRECT_NAME;
    }

    /**
     * A new user can be created from the main form that can be accessed without
     * being authentified. In this case, we check the validity of the filled-in
     * url and we prevent the new users to be activated and created with admin
     * privileges.
     * On the other side, if the user is created from the admin interface, it can
     * be set with activation and admin privileges info but the check of the url
     * is useless cause the field has been removed from the form.
     *
     * @param createUserCommand
     * @param result
     * @param model
     * @param successViewName
     * @param errorViewName
     * @param successMessageKey
     * @return
     * @throws Exception
     */
    private String submitCreateUserForm (
        CreateUserCommand createUserCommand,
        BindingResult result,
        Model model,
        String successViewName,
        String errorViewName,
        String successMessageKey) throws Exception {

        // We check whether the form is valid
        createUserFormValidator.validate(createUserCommand, result);
        // If the form has some errors, we display it again with errors' details
        if (result.hasErrors()) {
            return displayFormWithErrors(
                model,
                createUserCommand,
                errorViewName);
        }
        User user= createUser(createUserCommand,true,true);
        model.addAttribute(TgolKeyStore.USER_LIST_KEY, userDataService.findAll());
        model.addAttribute(successMessageKey,user.getEmail1());
        return successViewName;
    }

    /**
     * Create a user entit
     * @param createUserCommand
     * @return
     * @throws Exception
     */
    private User createUser(
        CreateUserCommand createUserCommand,
        boolean allowActivation,
        boolean allowAdmin) throws Exception {
        User user = userDataService.create();
        user.setEmail1(createUserCommand.getEmail());
        user.setFirstName(createUserCommand.getFirstName());
        user.setName(createUserCommand.getLastName());
        user.setPhoneNumber(createUserCommand.getPhoneNumber());
        user.setPassword(MD5Encoder.MD5(createUserCommand.getPassword()));
        user.setWebUrl1(createUserCommand.getSiteUrl());
        if (allowActivation) {
            user.setAccountActivation(createUserCommand.getActivated());
        } else {
            user.setAccountActivation(false);
        }
        if (allowAdmin && createUserCommand.getAdmin()) {
            user.setRole(createUserCommandFactory.getAdminRole());
        } else {
            user.setRole(createUserCommandFactory.getUserRole());
        }
        userDataService.saveOrUpdate(user);
        return user;
    }

}
