package app.view;

import app.model.persistence.ConnectionPool;
import app.exceptions.CustomException;
import io.javalin.http.Context;

public class Frontpage extends Command {
    @Override
    public void execute(Context ctx, String template, ConnectionPool connectionPool) throws CustomException {
        model.put("extra", "Extra");
        model.put("hello", "Hello, Javalin-World.");
        model.put("message", "Her er en besked");
        ctx.render(template, model);
    }
}
