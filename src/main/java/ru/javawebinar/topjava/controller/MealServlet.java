package ru.javawebinar.topjava.controller;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static String EDIT = "/edit/meal.jsp";
    private static String LIST = "/list/meal.jsp";

    private static final Logger log = getLogger(MealServlet.class);
    private UserMealService mealService = UserMealServiceImpl.getInstance();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = LIST;

        if (action == null) {
            request.setAttribute("meals_list", mealService.getAll());
        } else {
            log.info(String.format("info: iteracting with meals action (%s)", action));

            //выбор действия, в зависимости от action
            if (action.equalsIgnoreCase("list")) {
                request.setAttribute("meals_list", mealService.getAll());

            } else if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                mealService.delete(id);
                request.setAttribute("meals_list", mealService.getAll());

            } else if (action.equalsIgnoreCase("edit")) {
                url = EDIT;
                int id = Integer.parseInt(request.getParameter("id"));
                UserMealWithExceed meal = mealService.get(id);
                request.setAttribute("meal", meal);

            } else {
                url = EDIT;
            }
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        String description = request.getParameter("description");
        long id = Long.valueOf(request.getParameter("id"));
        int calories = Integer.valueOf(request.getParameter("calories"));
        boolean exceed = Optional.ofNullable(request.getParameter("exceed")).isPresent();
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);

        UserMealWithExceed meal = new UserMealWithExceed(null, dateTime, description, calories, exceed);
        mealService.update(id, meal);
        request.setAttribute("meals_list", mealService.getAll());

        request.getRequestDispatcher(LIST).forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        mealService.delete(id);

        req.setAttribute("meals_list", mealService.getAll());
        req.getRequestDispatcher(LIST).forward(req, resp);
    }
}
