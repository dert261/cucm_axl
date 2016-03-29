package ru.obelisk.cucmaxl.web.controllers;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
/*import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.security.access.annotation.Secured;

//import ru.obelisk.cucmaxl.annotations.DatatableCriterias;
import ru.obelisk.cucmaxl.database.models.entity.EndUserPhoneBook;
import ru.obelisk.cucmaxl.database.models.entity.User;
import ru.obelisk.cucmaxl.database.models.entity.UserRole;
//import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.database.models.entity.enums.UserStatus;
import ru.obelisk.cucmaxl.database.models.entity.enums.UserType;
import ru.obelisk.cucmaxl.database.models.service.UserRoleService;
import ru.obelisk.cucmaxl.database.models.service.UserService;
import ru.obelisk.cucmaxl.database.models.views.View;
//import ru.obelisk.cucmaxl.web.ui.datatables.DataSet;
//import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesResponse;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserRoleService userRoleService;
	
	private static Logger logger = LogManager.getLogger(UsersController.class);
	
	@ModelAttribute("userStatus")
	public List<UserStatus> userStatus() {
	    return Arrays.asList(UserStatus.values());
	}
	
	@ModelAttribute("userType")
	public List<UserType> userType() {
	    return Arrays.asList(UserType.values());
	}
	
	@ModelAttribute("userRole")
	public List<UserRole> userRoleEnum() {
		return userRoleService.getAllUserRoles();
	}
	
	/*@ModelAttribute("userAll")
	public List<User> userAll() {
		List<User> users = new ArrayList<User>();
		//users.addAll(userService.getAllUsers());		
		return users;
	}*/
	
	@RequestMapping(value = {"/search/users"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody List<Select2Result> searchUsers(@RequestParam String searchString) {
		logger.info("Requesting search users with term: {}",searchString);
		return userService.findUserByTerm(searchString);
	}
	
	@RequestMapping(value = {"/search/cucmusers"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody List<Select2Result> searchCucmUsers(@RequestParam String searchString) {
		logger.info("Requesting search cucm users with term: {}", searchString);
		return userService.findCucmUserByTerm(searchString);
	}
	
	@RequestMapping(value = {"/search/user/{id}"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody List<Select2Result> searchUser(@PathVariable Integer id) {
		logger.info("Requesting search user with id: {}",id);
		User user = userService.getUserById(id);
		//u.id, CONCAT(u.name,' (',u.login,')')
		Select2Result selResult = new Select2Result(user.getId(), user.getName()+"("+user.getLogin()+")");
		List<Select2Result> resultList = new ArrayList<Select2Result>();
		resultList.add(selResult);
		return resultList;
	}
	
	@JsonView(value={View.User.class})
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String indexPage(Model model) {
		logger.info("Requesting users page");
		User user = new User();
		model.addAttribute("user", user);
        return "users/index";
	}
	

	@JsonView(value={View.User.class})
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	@RequestMapping(value = "/ajax/serverside/userdata.json", method = RequestMethod.GET)
	public @ResponseBody DataTablesOutput<User> getUsers(@Valid DataTablesInput input) {
		DataTablesOutput<User> output = userService.findAllWithSpecs(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
	
	private List<User> idGenerate(List<User> users, int start){
		for(int i=0;i<users.size();i++){
			users.get(i).setNumberLocalized(start+i+1);
		}
		return users;
	}
	
	
	/*@JsonView(value={View.User.class})
	@RequestMapping(value = {"/ajax/serverside/userdata.json"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody DatatablesResponse<User> usersDatatables(
			@DatatableCriterias DatatablesCriterias criterias, 
			Model model, 
			Locale locale) {
		logger.info("Requesting users data for table on index page");
			
		List<User> users = i18nData(userService.findUserWithDatatablesCriterias(criterias), locale);
		Long count = userService.getTotalCount();
		Long countFiltered = userService.getFilteredCount(criterias);
	    return DatatablesResponse.build(new DataSet<User>(users,count,countFiltered), criterias);
	}*/
	
	@JsonView(value={View.User.class})
	@RequestMapping(value = {"/ajax/clientside/userdata.json"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody DatatablesResponse<User> usersDataClientSide(Model model, Locale locale) {
		logger.info("Requesting users data for table on index page");
		List<User> users = i18nData(userService.getAllUsers(), locale);
		return DatatablesResponse.clientSideBuild(users);
	}
	
	private List<User> i18nData(List<User> users, Locale locale){
		Iterator<User> userIterator = users.iterator();
		while(userIterator.hasNext()){
			User user = userIterator.next();
			user.setStatusLocalized(messageSource.getMessage(user.getStatus().toString(),null, locale));
			/*user.setLocalUserFlagLocalized(messageSource.getMessage(user.getLocalUserFlag().toString(),null, locale));
			StringBuilder roleString = new StringBuilder();
			List<UserRole> roles = new ArrayList<UserRole>(user.getRoles());
			for(UserRole role : roles){
				roleString.append(messageSource.getMessage(role.getRoleName(),null, locale));
				roleString.append(";<br>");
			}
			user.setRoleLocalized(roleString.toString());*/
			
		}
		return users;
	}
	
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreatePage(Model model) {
		logger.info("Requesting create user page");
		User user = new User();
		model.addAttribute("user", user);
        return "users/create";
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveCreatePage(	final ModelMap model, 
									@Valid @ModelAttribute("user") final User user, 
									final BindingResult bindingResult ) {
		logger.info("Requesting add user method");
		if(bindingResult.hasErrors()){
			return "users/create";
		}
		user.setName(user.getLname()+" "+user.getFname()+" "+user.getMname());
		user.setSigninDate(new Date());
		userService.addUser(user);
		model.clear();
        return "redirect:/users/index.html";
	}
	
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdatePage(ModelMap model, @PathVariable(value = "id") int id) {
		logger.info("Requesting update user page");
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "users/update";
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String saveUpdatePage(final ModelMap model, 
									@Valid @ModelAttribute("user") final User formUser, 
									final BindingResult bindingResult,
									SessionStatus status) {
		logger.info("Requesting save update user method");
		if(bindingResult.hasErrors()){
			return "users/update";
		}
		userService.editUser(formUser);
		status.setComplete();
		return "redirect:/users/index.html";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteUser(int id, SessionStatus status) {
		logger.info("Requesting delete user");
		userService.deleteUser(id);
		status.setComplete();
		return "redirect:/users/index.html";
	}
	
	
	@JsonView(value={View.EndUserPhoneBook.class})
	@RequestMapping(value = {"/phoneBook/{id}"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String viewPhoneBookUserPage(ModelMap model, @PathVariable(value = "id") int id) {
		logger.info("Requesting update CUCM AXL port page");
		User user = userService.getUserById(id);
		
		EndUserPhoneBook phoneBook = user.getPhoneBook(); 
		if(phoneBook==null){
			phoneBook = new EndUserPhoneBook();
		}
		phoneBook.setUser(user);
		model.addAttribute("endUserPhoneBook", phoneBook);
		return "users/_modal";
	}
	
	@JsonView(value={View.EndUserPhoneBook.class})
	@RequestMapping(value = {"/phoneBook"}, method = RequestMethod.PUT, consumes="application/json")
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody EndUserPhoneBook saveUpdateEndUserPage(@RequestBody EndUserPhoneBook endUserPhoneBook) {
		logger.info("Requesting save end user phone book update CUCM AXL end user method");
			
		User user = userService.getUserById(endUserPhoneBook.getUser().getId());
		endUserPhoneBook.setUser(user);
		user.setPhoneBook(endUserPhoneBook);
		userService.edit(user);
		return endUserPhoneBook;
	}
}
