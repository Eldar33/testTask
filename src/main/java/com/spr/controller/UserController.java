package com.spr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spr.exception.UserNotFound;
import com.spr.model.User;
import com.spr.service.UserService;
import com.spr.validation.UserValidator;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    public static String nameSearch = "";

    private static final int PAGE_SIZE = 10;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;
    private static int currentPageNumber = 1;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView newUserPage() {
        ModelAndView mav = new ModelAndView("user-new", "user", new User());
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute @Valid User user,
                                      BindingResult result,
                                      final RedirectAttributes redirectAttributes) {

        if (result.hasErrors())
            return new ModelAndView("user-new");

        ModelAndView mav = new ModelAndView();
        String message = "New user " + user.getName() + " was successfully created.";

        userService.create(user);
        mav.setViewName("redirect:/index.html");

        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }

    @RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView userListPage(@PathVariable("pageNumber") String pageNumber) {
        return getUserListMAV(pageNumber);
    }


    @RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.POST)
    public ModelAndView findUsers(@RequestParam(value = "search") String name, @PathVariable("pageNumber") String pageNumber) {
        nameSearch = name;
        return getUserListMAV(pageNumber);

    }

    public ModelAndView getUserListMAV(String pageNumber) {
        long countAllRows = userService.getCountAllRows("%" + nameSearch + "%");
        int totalPages = (int) Math.ceil((double) countAllRows / (double) PAGE_SIZE);
        totalPages = (totalPages == 0) ? 1 : totalPages;
        int currentPageNumber = Integer.parseInt(pageNumber);

        Pageable pageable = new PageRequest(currentPageNumber - 1, PAGE_SIZE);

        int begin = Math.max(1, currentPageNumber - 5);
        int end = Math.min(begin + 10, totalPages);

        ModelAndView mav = new ModelAndView("user-list");
        mav.addObject("userList", userService.findAll("%" + nameSearch + "%", pageable));
        mav.addObject("currentPageNumber", currentPageNumber);
        mav.addObject("totalPages", totalPages);
        mav.addObject("beginIndex", begin);
        mav.addObject("endIndex", end);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUserPage(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("user-edit");
        User user = userService.findById(id);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute @Valid User user,
                                 BindingResult result) throws UserNotFound {

        if (result.hasErrors())
            return new ModelAndView("user-edit");

        ModelAndView mav = new ModelAndView("redirect:/user/list/" + currentPageNumber + ".html");

        userService.update(user);

        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Integer id) throws UserNotFound {

        ModelAndView mav = new ModelAndView("redirect:/user/list/" + currentPageNumber + ".html");

        User user = userService.delete(id);

        return mav;
    }

}
