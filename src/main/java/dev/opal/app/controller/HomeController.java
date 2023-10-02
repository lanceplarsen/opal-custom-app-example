package dev.opal.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.opal.app.service.GroupsService;
import dev.opal.app.service.ResourceService;
import dev.opal.app.service.UserService;

@Controller
public class HomeController {

	private final ResourceService resourceService;
	private final UserService userService;
	private final GroupsService groupsService;

	public HomeController(ResourceService resourceService, UserService userService, GroupsService groupsService) {
		this.resourceService = resourceService;
		this.userService = userService;
		this.groupsService = groupsService;
	}

	@GetMapping("/")
	public String showUsersResources(Model model) {
		model.addAttribute("users", userService.getAllUsers().getUsers());
		model.addAttribute("resources", resourceService.getAllResources().getResources());
		model.addAttribute("groups", groupsService.getAllGroups().getGroups());

		return "usersGroupsAndResources";
	}

	@GetMapping("/swagger-ui")
	public String index() {
		return "redirect:swagger-ui.html";
	}
}