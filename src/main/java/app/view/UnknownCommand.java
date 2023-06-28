package app.view;

import app.model.persistence.ConnectionPool;
import app.exceptions.CustomException;
import io.javalin.http.Context;

public class UnknownCommand extends Command {
    @Override
    public void execute(Context ctx, String template, ConnectionPool connectionPool) throws CustomException {
        String msg = "Unknown command. Contact IT";
        throw new CustomException(msg);
    }
}
