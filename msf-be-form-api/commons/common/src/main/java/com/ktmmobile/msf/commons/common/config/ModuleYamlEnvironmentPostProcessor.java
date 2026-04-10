package com.ktmmobile.msf.commons.common.config;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.*;

public class ModuleYamlEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String BASE_PATTERN_PREFIX = "classpath*:application-*";
    private static final String BASE_EXTENSION_YAML = ".yaml";
    private static final String BASE_EXTENSION_YML = ".yml";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ClassLoader classLoader = application.getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

        Set<String> activeProfiles = resolveActiveProfiles(environment);
        loadProfileModuleYaml(environment, resolver, loader, activeProfiles);
        loadBaseModuleYaml(environment, resolver, loader);
    }

    private static Set<String> resolveActiveProfiles(ConfigurableEnvironment environment) {
        Set<String> profiles = new LinkedHashSet<>(Arrays.asList(environment.getActiveProfiles()));
        if (!profiles.isEmpty()) {
            return profiles;
        }

        String configured = environment.getProperty("spring.profiles.active");
        if (configured == null || configured.isBlank()) {
            return Set.of();
        }

        return Arrays.stream(configured.split(","))
            .map(String::trim)
            .filter(value -> !value.isEmpty())
            .collect(LinkedHashSet::new, Set::add, Set::addAll);
    }

    private static void loadBaseModuleYaml(
        ConfigurableEnvironment environment,
        ResourcePatternResolver resolver,
        YamlPropertySourceLoader loader
    ) {
        String basePatternYaml = BASE_PATTERN_PREFIX + BASE_EXTENSION_YAML;
        String basePatternYml = BASE_PATTERN_PREFIX + BASE_EXTENSION_YML;
        for (Resource resource: findResources(resolver, basePatternYaml, basePatternYml)) {
            String fileName = resource.getFilename();
            if (fileName == null || isProfileSpecificFile(fileName)) {
                continue;
            }
            addYamlPropertySource(environment, loader, resource);
        }
    }

    private static void loadProfileModuleYaml(
        ConfigurableEnvironment environment,
        ResourcePatternResolver resolver,
        YamlPropertySourceLoader loader,
        Set<String> activeProfiles
    ) {
        for (String profile: activeProfiles) {
            String profilePatternYaml = BASE_PATTERN_PREFIX + "-" + profile + BASE_EXTENSION_YAML;
            String profilePatternYml = BASE_PATTERN_PREFIX + "-" + profile + BASE_EXTENSION_YML;
            for (Resource resource: findResources(resolver, profilePatternYaml, profilePatternYml)) {
                addYamlPropertySource(environment, loader, resource);
            }
        }
    }

    private static List<Resource> findResources(ResourcePatternResolver resolver, String... patterns) {
        List<Resource> resources = new ArrayList<>();
        for (String pattern: patterns) {
            try {
                resources.addAll(Arrays.asList(resolver.getResources(pattern)));
            } catch (IOException ignored) {
                // Ignore and continue; missing optional resources are expected.
            }
        }

        resources.sort(Comparator.comparing(Resource::getDescription));
        return resources;
    }

    private static void addYamlPropertySource(
        ConfigurableEnvironment environment,
        YamlPropertySourceLoader loader,
        Resource resource
    ) {
        String sourceNamePrefix = "moduleYaml:" + resource.getDescription();
        try {
            List<PropertySource<?>> sources = loader.load(sourceNamePrefix, resource);
            for (PropertySource<?> source: sources) {
                if (environment.getPropertySources().contains(source.getName())) {
                    continue;
                }
                environment.getPropertySources().addLast(source);
            }
        } catch (IOException ignored) {
            // Ignore unreadable resources to avoid blocking startup.
        }
    }

    private static boolean isProfileSpecificFile(String fileName) {
        String normalized = fileName.endsWith(".yaml")
            ? fileName.substring(0, fileName.length() - 5)
            : fileName.endsWith(".yml") ? fileName.substring(0, fileName.length() - 4) : fileName;

        String prefix = "application-";
        if (!normalized.startsWith(prefix)) {
            return false;
        }

        String token = normalized.substring(prefix.length());
        return token.contains("-");
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
