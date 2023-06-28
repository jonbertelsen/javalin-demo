package app.view;

import app.model.persistence.ConnectionPool;
import app.exceptions.CustomException;
import io.javalin.http.Context;

public class RegisterPage extends Command {
    @Override
    public void execute(Context ctx, String template, ConnectionPool connectionPool) throws CustomException {

    }
}
