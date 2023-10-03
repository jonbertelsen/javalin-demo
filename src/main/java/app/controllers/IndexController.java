package app.controllers;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class IndexController
{
    public static void indexController(Context ctx)
    {
        Map<String, Object> model = new HashMap<>();
        ctx.sessionAttribute("email", "jobe@cphbusiness");
        ctx.attribute("name", "Javalin");
        model.put("hej", "hej fra request");
        model.put("extra", "Extra");
        model.put("hello", "Hello, Javalin-World.");
        model.put("message", "Her er en besked");
        ctx.render("index.html", model);
    };

}
