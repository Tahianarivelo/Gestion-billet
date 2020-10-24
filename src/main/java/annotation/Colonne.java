package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Colonne 
{
    String sequence() default "";
    String prefixe() default "";
    String colonne() default "";
    String classPackage() default "";
    boolean identifiant() default false;
}
