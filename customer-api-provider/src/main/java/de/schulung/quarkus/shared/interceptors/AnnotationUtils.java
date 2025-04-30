package de.schulung.quarkus.shared.interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

class AnnotationUtils {

  private AnnotationUtils() {
  }

  static <A extends Annotation> Optional<A> findAnnotation(Method method, Class<A> annotationClass) {
    return Optional
      .ofNullable(method.getAnnotation(annotationClass))
      .or(() -> findAnnotation(method.getDeclaringClass(), annotationClass));
  }

  static <A extends Annotation> Optional<A> findAnnotation(Class<?> clazz, Class<A> annotationClass) {
    return Optional
      .ofNullable(clazz.getAnnotation(annotationClass));
  }

}
