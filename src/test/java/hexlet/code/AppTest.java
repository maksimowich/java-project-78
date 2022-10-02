package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;
import java.util.Map;
import java.util.HashMap;

class AppTest {

    private static Validator v;

    @BeforeEach
    void preparing() {
        v = new Validator();
    }

    @Test
    @DisplayName("Test StringSchema")
    @SuppressWarnings("checkstyle:magicnumber")
    void stringSchemaTest() {

        StringSchema schema = v.string();

        assertEquals(true, schema.isValid(""));
        assertEquals(true, schema.isValid(null));
        assertEquals(false, schema.isValid(5));
        assertEquals(true, schema.contains("what").isValid(null));
        assertEquals(true, schema.minLength(5).isValid(null));

        schema = v.string();
        schema.required();
        assertEquals(true, schema.isValid("what does the fox say"));
        assertEquals(true, schema.isValid("hexlet"));
        assertEquals(false, schema.isValid(null));
        assertEquals(false, schema.isValid(""));
        assertEquals(false, schema.isValid(2));

        assertEquals(true, schema.contains("what").isValid("what does the fox say"));
        assertEquals(false, schema.contains("whatthe").isValid("what does the fox say"));
        schema = v.string().required();
        assertEquals(true, schema.minLength(5).isValid("what does the fox say"));
        assertEquals(false, schema.minLength(10).isValid("what"));

        schema = v.string().required().contains("ya"); //check required() with generics
    }

    @Test
    @DisplayName("Test NumberSchema")
    @SuppressWarnings("checkstyle:magicnumber")
    void numberSchemaTest() {
        NumberSchema schema = v.number();

        assertEquals(true, schema.isValid(null));
        assertEquals(true, schema.isValid(5));
        assertEquals(true, schema.isValid(-5));
        assertEquals(false, schema.isValid("5"));
        assertEquals(true, schema.positive().isValid(null));
        assertEquals(true, schema.range(5, 10).isValid(null));

        schema = v.number();
        schema.required();
        assertEquals(false, schema.isValid(null));
        assertEquals(true, schema.isValid(10));
        assertEquals(false, schema.isValid("5"));

        assertEquals(true, schema.positive().isValid(10));
        assertEquals(false, schema.isValid(0));
        assertEquals(false, schema.isValid(-10));

        schema.range(5, 10);
        assertEquals(true, schema.isValid(5));
        assertEquals(true, schema.isValid(10));
        assertEquals(false, schema.isValid(4));
        assertEquals(false, schema.isValid(11));

        schema = v.number().required().positive(); //check required() with generics
    }

    @Test
    @DisplayName("Test MapSchema")
    void mapSchemaTest() {
        MapSchema schema = v.map();

        assertEquals(true, schema.isValid(null));
        assertEquals(true, schema.sizeof(2).isValid(null));
        assertEquals(false, schema.sizeof(2).isValid(Map.of("key1", "value1")));

        schema = v.map();
        schema.required();
        assertEquals(false, schema.isValid(null));
        assertEquals(true, schema.isValid(new HashMap()));
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertEquals(true, schema.isValid(data));

        schema.sizeof(2);
        assertEquals(false, schema.isValid(data));
        data.put("key2", "value2");
        assertEquals(true, schema.isValid(data));

        schema = v.map().required().sizeof(0); //check required() with generics
    }

    @Test
    @DisplayName("Test MapSchema.shape()")
    @SuppressWarnings("checkstyle:magicnumber")
    void mapSchemaShapeTest() {
        MapSchema schema = v.map();

// shape - позволяет описывать валидацию для значений объекта Map по ключам.
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        assertEquals(true, schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertEquals(true, schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertEquals(false, schema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        assertEquals(false, schema.isValid(human4));

        assertEquals(true, schema.sizeof(2).isValid(human2));
        assertEquals(false, schema.sizeof(1).isValid(human2));

        schema = v.map().shape(null);
        assertEquals(true, schema.isValid(human4));

    }
}
