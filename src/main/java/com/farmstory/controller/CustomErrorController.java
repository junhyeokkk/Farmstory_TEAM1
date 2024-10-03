package com.farmstory.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);

            String errorMessage = "Oops! Something went wrong.";
            if (statusCode == 404) {
                errorMessage = "Page not found!";
            } else if (statusCode == 500) {
                errorMessage = "Internal server error!";
            }
            model.addAttribute("error", errorMessage);
        }
        return "error/error";  // error.html로 이동
    }


}
