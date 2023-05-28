package app;

import app.entities.User;
import app.config.ThymeleafConfig;
import app.persistence.ConnectionPool;
import app.persistence.UserFacade;
import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{
    private static ConnectionPool connectionPool = new ConnectionPool();

    public static void main(String[] args)
    {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinRenderer.register(new JavalinThymeleaf());
            JavalinThymeleaf.init(ThymeleafConfig
                    .templateEngine(ThymeleafConfig
                            .templateResolver(TemplateMode.HTML, "/templates/", ".html")));
        }).start(7070);

        app.get("/", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("extra", "Extra");
            model.put("hello", "Hello, Javalin-World.");
            model.put("message", "Her er en besked");
            ctx.render("index.html", model);
        });

        app.get("/users", ctx -> {
            Map<String, Object> model = new HashMap<>();
            List<User> userList = UserFacade.getAllUsers(connectionPool);
            model.put("userList", userList);
            ctx.render("users.html", model);
        });
    }
}