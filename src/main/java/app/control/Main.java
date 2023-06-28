package app.control;

import app.config.ThymeleafConfig;
import app.model.persistence.ConnectionPool;
import app.view.Command;
import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.templatemode.TemplateMode;

public class Main
{
    private static final String PREFIX = "/templates/";
    private static final String SUFFIX = ".html";

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    public static void main(String[] args)
    {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            initTemplateEngine();
        }).start(7070);
        invokeRouting(app);
    }

    private static void initTemplateEngine() {
        JavalinRenderer.register(new JavalinThymeleaf());
        JavalinThymeleaf.init(ThymeleafConfig
                .templateEngine(ThymeleafConfig
                .templateResolver(TemplateMode.HTML, PREFIX, SUFFIX)));
    }

    private static void invokeRouting(Javalin app) {
        app.get("/", ctx -> {
            Command action = Command.getInstance("front_page");
            action.execute(ctx, "index.html", connectionPool);
        });

        app.get("/users", ctx -> {
            Command action = Command.getInstance("view_users_page");
            action.execute(ctx, "users.html", connectionPool);
        });

        app.get("/loginpage", ctx -> {
            Command action = Command.getInstance("login_page");
            action.execute(ctx, "loginpage.html", connectionPool);
        });

        app.post("/loginaction", ctx -> {
            Command action = Command.getInstance("login_action");
            action.execute(ctx, "index.html", connectionPool);
        });
    }
}