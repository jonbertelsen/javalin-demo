package app;

import app.config.ThymeleafConfig;
import app.controllers.IndexController;
import app.controllers.UserController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.templatemode.TemplateMode;

public class Main
{
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/startcode?currentSchema=public";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL);

    public static void main(String[] args)
    {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinRenderer.register(new JavalinThymeleaf());
            JavalinThymeleaf.init(ThymeleafConfig
                    .templateEngine(ThymeleafConfig
                            .templateResolver(TemplateMode.HTML, "/templates/", ".html")));
        }).start(7070);

        app.get("/", (ctx) ->  IndexController.indexController(ctx));
        app.get("/users", (ctx) ->  UserController.userController(ctx, connectionPool));
    }
}