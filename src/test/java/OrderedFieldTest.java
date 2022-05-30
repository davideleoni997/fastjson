import com.alibaba.fastjson.JSONException;
import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.constraints.Null;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value= Parameterized.class)
public class OrderedFieldTest{

    private String modello;
    private int id;

    @Parameterized.Parameters
    public static Collection<Object[]> TestParameters(){
        return Arrays.asList(new Object[][] {

                {"{\"id\":1001}",1001}, //model, expectedId
                {"{\"id\":}",0}, //Parser e deserializer coverage aumenta dell'1%, al costo di dover continuare con test failures
                {"{\"id\":abc}",0}, //Abbiamo eccezione in questo caso e il precedente
                {null,0}
        });
    }

    public OrderedFieldTest(String modello, int id){
        configure(modello,id);
    }

    public void configure(String modello, int id) {
        this.modello= modello;
        this.id = id;

    }

    @Test
    public void test_ordered_field() throws Exception {
        try {
            String text = modello;
            Model model = JSON.parseObject(text, Model.class, Feature.OrderedField);
            Assert.assertEquals(id, model.getId());
            String text2 = JSON.toJSONString(model);
            Assert.assertEquals(text, text2);
        }catch(Exception e){
            if(modello!=null)
            Assert.assertEquals(e.getClass(), JSONException.class);
            else
                Assert.assertEquals(e.getClass(),NullPointerException.class);
        }
        
    }
    
    public static interface Model {
        public int getId();
        public void setId(int value);
    }
}