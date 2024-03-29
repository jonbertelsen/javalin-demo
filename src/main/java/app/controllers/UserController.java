package app.controllers;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserFacade;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController
{
    public static void showUserList(Context ctx, ConnectionPool connectionPool) throws DatabaseException
    {
        int usergroup = Integer.parseInt(ctx.queryParam("usergroup"));
        int page = Integer.parseInt(ctx.queryParam("page"));

        String userName = ctx.formParam("username");
        String password = ctx.formParam("password");

        Map<String, Object> model = new HashMap<>();
        List<User> userList = UserFacade.getAllUsers(connectionPool);
        model.put("userList", userList);
        ctx.attribute("name", "Javalin");
        ctx.render("users.html", model);
    };

}
