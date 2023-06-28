package app.view;

import app.model.persistence.ConnectionPool;
import app.exceptions.CustomException;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public abstract class Command {

    private static HashMap<String, Command> commands;
    public Map<String, Object> model = new HashMap<>();

    private static void initCommands() {
        commands = new HashMap<>();
        commands.put("front_page", new Frontpage());
        commands.put("login_page", new LoginPage());
        commands.put("login_action", new LoginAction());
        commands.put("register_page", new RegisterPage() );
        commands.put("view_users_page", new ViewUsersPage());
    }

    public static Command getInstance(String command) {
        if ( commands == null ) {
            initCommands();
        }
        return commands.getOrDefault(command, new UnknownCommand() );   // unknowncommand er default.
    }

    public abstract void execute(Context ctx, String template, ConnectionPool connectionPool)
            throws CustomException;
}
