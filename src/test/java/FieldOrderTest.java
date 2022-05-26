import com.alibaba.fastjson.JSON;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Author : Davide Leoni
 */
@RunWith(value= Parameterized.class)
public class FieldOrderTest
{

    private String personName;
    private String schoolName;
    private String expected;

    @Parameterized.Parameters
    public static Collection<Object[]> testParameters(){
    return Arrays.asList(new Object[][]{
            {"njb","llyz","{\"name\":\"njb\",\"school\":{\"name\":\"llyz\"}}"}, //PersonName, schoolName, expected
            {null,"llyz","{\"school\":{\"name\":\"llyz\"}}"},
            {"njb",null,"{\"name\":\"njb\",\"school\":{}}"},
            {null,null,"{\"school\":{}}"}, //Aggiungere campi null non ha incrementato la coverage?
    });
    }

    public FieldOrderTest(String personName,String schoolName,String expected){
        configure(personName,schoolName,expected);
    }

    public void configure(String personName, String schoolName, String expected) {
        this.personName = personName;
        this.schoolName = schoolName;
        this.expected = expected;
    }

    @Test
    public void test_field_order(){
        Person p = new Person();
        p.setName(personName);
        School s = new School();
        s.setName(schoolName);
        p.setSchool(s);
        String json = JSON.toJSONString(p);
        assertEquals(expected, json);
    }

    public static class Person {
        private String name;
        private School school;

        public boolean isSchool() {
            return false;
        }

        public School getSchool() {
            return school;
        }

        public void setSchool(School school) {
            this.school = school;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class School {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}