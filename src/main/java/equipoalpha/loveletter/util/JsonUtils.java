package equipoalpha.loveletter.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {

    public static boolean hasString(JsonObject object, String element) {
        return hasPrimitive(object, element) && object.getAsJsonPrimitive(element).isString();
    }

    public static boolean hasBoolean(JsonObject object, String element) {
        return hasPrimitive(object, element) && object.getAsJsonPrimitive(element).isBoolean();
    }

    public static boolean isString(JsonElement element) {
        return element.isJsonPrimitive() && element.getAsJsonPrimitive().isString();
    }

    public static boolean isNumber(JsonElement element) {
        return element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber();
    }

    public static boolean hasArray(JsonObject object, String element) {
        return hasElement(object, element) && object.get(element).isJsonArray();
    }

    public static boolean hasPrimitive(JsonObject object, String element) {
        return hasElement(object, element) && object.get(element).isJsonPrimitive();
    }

    public static boolean hasElement(JsonObject object, String element) {
        if (object == null) {
            return false;
        } else {
            return object.get(element) != null;
        }
    }

    public static String asString(JsonElement element, String name) {
        if (element.isJsonPrimitive()) {
            return element.getAsString();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a string, was " + getType(element));
        }
    }

    public static String getString(JsonObject object, String element) {
        if (object.has(element)) {
            return asString(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a string");
        }
    }

    public static String getString(JsonObject object, String element, String defaultStr) {
        return object.has(element) ? asString(object.get(element), element) : defaultStr;
    }

    public static boolean asBoolean(JsonElement element, String name) {
        if (element.isJsonPrimitive()) {
            return element.getAsBoolean();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a Boolean, was " + getType(element));
        }
    }

    public static boolean getBoolean(JsonObject object, String element) {
        if (object.has(element)) {
            return asBoolean(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a Boolean");
        }
    }

    public static boolean getBoolean(JsonObject object, String element, boolean defaultBoolean) {
        return object.has(element) ? asBoolean(object.get(element), element) : defaultBoolean;
    }

    public static int asInt(JsonElement element, String name) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsInt();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a Int, was " + getType(element));
        }
    }

    public static int getInt(JsonObject object, String element) {
        if (object.has(element)) {
            return asInt(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a Int");
        }
    }

    public static int getInt(JsonObject object, String element, int defaultInt) {
        return object.has(element) ? asInt(object.get(element), element) : defaultInt;
    }

    public static JsonObject asObject(JsonElement element, String name) {
        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a JsonObject, was " + getType(element));
        }
    }

    public static JsonObject getObject(JsonObject object, String element) {
        if (object.has(element)) {
            return asObject(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a JsonObject");
        }
    }

    public static JsonObject getObject(JsonObject object, String element, JsonObject defaultObject) {
        return object.has(element) ? asObject(object.get(element), element) : defaultObject;
    }

    public static JsonArray asArray(JsonElement element, String name) {
        if (element.isJsonArray()) {
            return element.getAsJsonArray();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a JsonArray, was " + getType(element));
        }
    }

    public static JsonArray getArray(JsonObject object, String element) {
        if (object.has(element)) {
            return asArray(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a JsonArray");
        }
    }

    public static JsonArray getArray(JsonObject object, String name, JsonArray defaultArray) {
        return object.has(name) ? asArray(object.get(name), name) : defaultArray;
    }

    public static String getType(JsonElement element) {
        String string = StringUtils.abbreviateMiddle(String.valueOf(element), "...", 10);
        if (element == null) {
            return "null (missing)";
        } else if (element.isJsonNull()) {
            return "null (json)";
        } else if (element.isJsonArray()) {
            return "an array (" + string + ")";
        } else if (element.isJsonObject()) {
            return "an object (" + string + ")";
        } else {
            if (element.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
                if (jsonPrimitive.isNumber()) {
                    return "a number (" + string + ")";
                }

                if (jsonPrimitive.isBoolean()) {
                    return "a boolean (" + string + ")";
                }
            }
            return string;
        }
    }
}
