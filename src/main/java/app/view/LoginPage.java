package app.view;

import app.model.persistence.ConnectionPool;
import app.exceptions.CustomException;
import io.javalin.http.Context;

public class LoginPage extends Command {
    @Override
    public void execute(Context ctx, String template, ConnectionPool connectionPool) throws CustomException {
        ctx.render("loginpage.html", model);
    }
}
