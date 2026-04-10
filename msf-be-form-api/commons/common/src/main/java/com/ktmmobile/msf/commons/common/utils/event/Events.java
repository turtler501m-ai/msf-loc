package com.ktmmobile.msf.commons.common.utils.event;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import com.ktmmobile.msf.commons.common.utils.reflections.ReflectionsUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Events {

    private static ApplicationEventPublisher publisher;

    static void initialize(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void publish(Object event) {
        if (publisher == null) {
            return;
        }
        publisher.publishEvent(event);
        log.debug("event has been published(event: {})", toString(event));
    }

    private static String toString(Object event) {
        Class<?> eventClass = event.getClass();
        String fieldObjects = Arrays.stream(eventClass.getDeclaredFields())
            .map(field -> field.getName() + "=" + ReflectionsUtils.getObject(event, field).toString())
            .collect(Collectors.joining(", "));
        return eventClass.getSimpleName() + "{" + fieldObjects + "}";
    }
}
