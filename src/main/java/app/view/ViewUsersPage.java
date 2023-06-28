package app.view;

import app.model.entities.User;
import app.model.persistence.ConnectionPool;
import app.model.persistence.UserFacade;
import app.exceptions.CustomException;
import io.javalin.http.Context;

import java.util.List;

public class ViewUsersPage extends Command {
    @Override
    public void execute(Context ctx, String template, ConnectionPool connectionPool) throws CustomException {
        List<User> userList = UserFacade.getAllUsers(connectionPool);
        model.put("userList", userList);
        ctx.render(template, model);
    }
}
