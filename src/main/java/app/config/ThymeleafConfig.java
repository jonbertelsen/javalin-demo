package app.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

public class ThymeleafConfig {

    public static TemplateEngine templateEngine(ITemplateResolver templateResolver)
    {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.addTemplateResolver(templateResolver);
        return templateEngine;
    }

    public static ITemplateResolver templateResolver(TemplateMode templateMode, String prefix, String suffix)
    {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(templateMode);
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
}