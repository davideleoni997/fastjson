import com.alibaba.fastjson.JSON;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.constraints.Null;
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

    private Person p;

    @Parameterized.Parameters
    public static Collection<Object[]> testParameters(){
    return Arrays.asList(new Object[][]{
            {true,"njb","llyz","{\"name\":\"njb\",\"school\":{\"name\":\"llyz\"}}"}, //PersonName, schoolName, expected
            {true,null,"llyz","{\"school\":{\"name\":\"llyz\"}}"},
            {true,"njb",null,"{\"name\":\"njb\",\"school\":{}}"},
            {true,null,null,"{\"school\":{}}"},
            {false,null,null, "null"}
    });
    }

    public FieldOrderTest(boolean fill,String personName,String schoolName,String expected){
        configure(fill,personName,schoolName,expected);
    }

    public void configure(boolean fill,String personName, String schoolName, String expected) {
        this.personName = personName;
        this.schoolName = schoolName;
        this.expected = expected;
        if(fill) {
            Person p = new Person();
            p.setName(personName);
            School s = new School();
            s.setName(schoolName);
            p.setSchool(s);
            this.p = p;
        }
        else this.p = null;
    }

    @Test
    public void test_field_order(){
        try {
            String json = JSON.toJSONString(p);
            assertEquals(expected, json);
        }catch(Exception e){
            Assert.assertEquals(expected,e.getClass().getSimpleName());
        }
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