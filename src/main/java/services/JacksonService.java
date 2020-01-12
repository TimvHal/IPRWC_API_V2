package services;

/**
 * Interface JacksonService for Reading and Writing Json-strings
 *
 * @author TimvHal
 * @version 03/01/2020
 */
public interface JacksonService {

    public String writeValueAsString(Object[] list);

    public Object readValue(String jsonString);
}
