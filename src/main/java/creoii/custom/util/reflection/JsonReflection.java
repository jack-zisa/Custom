package creoii.custom.util.reflection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class JsonReflection {
    public static JsonElement serializeClass(Class<?> clazz) {
        JsonObject object = new JsonObject();

        object.addProperty("package", clazz.getPackage().getName());

        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            object.add(annotation.annotationType().getName(), serializeAnnotation(annotation));
        }

        object.addProperty("access_types", Modifier.toString(clazz.getModifiers()));
        String type = clazz.isRecord() ? "record" : clazz.isInterface() ? "interface" : clazz.isEnum() ? "enum" : clazz.isAnnotation() ? "annotation" : "class";
        object.addProperty("type", type);
        object.addProperty("name", clazz.getSimpleName());

        JsonArray fields = new JsonArray();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(serializeField(field));
        }
        object.add("fields", fields);

        JsonArray constructors = new JsonArray();
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            constructors.add(serializeConstructor(constructor));
        }
        object.add("constructors", constructors);

        JsonArray methods = new JsonArray();
        for (Method method : clazz.getDeclaredMethods()) {
            methods.add(serializeMethod(method));
        }
        object.add("methods", methods);

        JsonArray subclasses = new JsonArray();
        for (Class<?> clazz1 : clazz.getDeclaredClasses()) {
            subclasses.add(serializeClass(clazz1));
        }
        object.add("subclasses", subclasses);

        return object;
    }

    public static JsonElement serializeRecord(Record record) {
        return serializeClass(record.getClass());
    }

    public static JsonElement serializeAnnotation(Annotation annotation) {
        JsonObject object = new JsonObject();
        object.add(annotation.annotationType().getName(), serializeClass(annotation.annotationType()));
        return object;
    }

    public static JsonElement serializeField(Field field) {
        JsonObject object = new JsonObject();
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            //object.add(annotation.annotationType().getName(), serializeAnnotation(annotation));
        }
        object.addProperty("access_types", Modifier.toString(field.getModifiers()));
        object.addProperty("return_type", field.getType().getSimpleName());
        object.addProperty("name", field.getName());
        return object;
    }


    public static JsonElement serializeConstructor(Constructor<?> constructor) {
        JsonObject object = new JsonObject();
        for (Annotation annotation : constructor.getDeclaredAnnotations()) {
            //object.add(annotation.annotationType().getName(), serializeAnnotation(annotation));
        }
        object.addProperty("access_types", Modifier.toString(constructor.getModifiers()));
        object.addProperty("name", constructor.getDeclaringClass().getSimpleName());
        if (constructor.getParameterCount() > 0) {
            JsonArray parameters = new JsonArray();
            for (Parameter parameter : constructor.getParameters()) {
                parameters.add(serializeParameter(parameter));
            }
            object.add("parameters", parameters);
        }
        return object;
    }

    public static JsonElement serializeMethod(Method method) {
        JsonObject object = new JsonObject();
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            //object.add(annotation.annotationType().getName(), serializeAnnotation(annotation));
        }
        object.addProperty("access_types", Modifier.toString(method.getModifiers()));
        object.addProperty("return_type", method.getReturnType().getSimpleName());
        object.addProperty("name", method.getName());
        if (method.getParameterCount() > 0) {
            JsonArray parameters = new JsonArray();
            for (Parameter parameter : method.getParameters()) {
                parameters.add(serializeParameter(parameter));
            }
            object.add("parameters", parameters);
        }
        return object;
    }

    public static JsonElement serializeParameter(Parameter parameter) {
        JsonObject object = new JsonObject();
        for (Annotation annotation : parameter.getDeclaredAnnotations()) {
            //object.add(annotation.annotationType().getName(), serializeAnnotation(annotation));
        }
        object.addProperty("return_type", parameter.getType().getSimpleName());
        object.addProperty("name", parameter.getName());
        return object;
    }

    public static String deserializeClass(JsonElement element, boolean includePackage) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            StringBuilder builder = new StringBuilder();
            if (includePackage) {
                builder.append("package ").append(object.get("package").getAsString()).append(";");
                builder.append("\n\n");
            }
            if (!includePackage) builder.append("   ");
            builder.append(object.get("access_types").getAsString()).append(" ").append(object.get("type").getAsString()).append(" ").append(object.get("name").getAsString()).append(" {\n");
            for (JsonElement field : object.getAsJsonArray("fields")) {
                if (!includePackage) builder.append("   ");
                builder.append(deserializeField(field));
                builder.append("\n");
            }
            builder.append("\n");
            for (JsonElement constructor : object.getAsJsonArray("constructors")) {
                if (!includePackage) builder.append("   ");
                builder.append(deserializeConstructor(constructor));
                builder.append("\n");
            }
            for (JsonElement method : object.getAsJsonArray("methods")) {
                if (!includePackage) builder.append("   ");
                builder.append(deserializeMethod(method));
                builder.append("\n");
            }
            for (JsonElement subclass : object.getAsJsonArray("subclasses")) {
                if (!includePackage) builder.append("   ");
                builder.append(deserializeClass(subclass, false));
            }
            if (!includePackage) builder.append("   ");
            builder.append("}");
            return builder.toString();
        }
        throw new JsonSyntaxException("Json element is not a class.");
    }

    public static String deserializeRecord(JsonElement element, boolean includePackage) {
        return deserializeClass(element, includePackage);
    }

    public static String deserializeField(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            return "    " + object.get("access_types").getAsString() + " " +
                    object.get("return_type").getAsString() + " " + object.get("name").getAsString() + ";";
        }
        throw new JsonSyntaxException("Json element is not a field");
    }

    public static String deserializeConstructor(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            StringBuilder builder = new StringBuilder();
            builder.append("    ").append(object.get("access_types").getAsString()).append(" ").append(object.get("name").getAsString()).append("(");
            if (object.has("parameters")) {
                JsonArray parameters = object.getAsJsonArray("parameters");
                for (int i = 0; i < parameters.size(); ++i) {
                    builder.append(deserializeParameter(parameters.get(i)));
                    if (i != parameters.size() - 1) builder.append(", ");
                }
            }
            builder.append(") {").append("\n    }\n");
            return builder.toString();
        }
        throw new JsonSyntaxException("Json element is not a constructor");
    }

    public static String deserializeParameter(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            return object.get("return_type").getAsString() + " " + object.get("name").getAsString();
        }
        throw new JsonSyntaxException("Json element is not a parameter");
    }

    public static String deserializeMethod(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            StringBuilder builder = new StringBuilder();
            builder.append("    ").append(object.get("access_types").getAsString()).append(" ").append(object.get("return_type").getAsString()).append(" ").append(object.get("name").getAsString()).append("(");
            if (object.has("parameters")) {
                JsonArray parameters = object.getAsJsonArray("parameters");
                for (int i = 0; i < parameters.size(); ++i) {
                    builder.append(deserializeParameter(parameters.get(i)));
                    if (i != parameters.size() - 1) builder.append(", ");
                }
            }
            builder.append(") {").append("\n    }\n");
            return builder.toString();
        }
        throw new JsonSyntaxException("Json element is not a field");
    }
}
