package app.view;

import app.model.entities.User;
import app.model.persistence.ConnectionPool;
import app.model.persistence.UserFacade;
import app.exceptions.CustomException;
import io.javalin.http.Context;

public class LoginAction extends Command {
    @Override
    public void execute(Context ctx, String template, ConnectionPool connectionPool) throws CustomException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        User user = UserFacade.login(username, password, connectionPool);
        ctx.sessionAttribute("user", user);
        ctx.redirect("/");
    }
}
