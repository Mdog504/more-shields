package xyz.mdog.moreshields.config.module.base.config;

public @interface LoadFeature {
    String module();
    boolean enabledByDefault() default true;
    boolean canBeDisabled() default true;
}
