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
    public static Collection<String[]> TestParameters(){
        return Arrays.asList(new String[][] {

                {"{\"id\":1001}","1001"}, //model, expectedId
                {"{\"id\":}","1001"}, //Parser e deserializer coverage aumenta dell'1%, al costo di dover continuare con test failures
                {"{\"id\":abc}","1001"} //Stesso effetto del null
        });
    }

    public OrderedFieldTest(String modello, String id){
        configure(modello,id);
    }

    public void configure(String modello, String id) {
        this.modello= modello;
        this.id = Integer.parseInt(id);

    }

    @Test
    public void test_ordered_field() throws Exception {
        String text = modello;
        Model model = JSON.parseObject(text, Model.class, Feature.OrderedField);
        Assert.assertEquals(id, model.getId());
        String text2 = JSON.toJSONString(model);
        Assert.assertEquals(text, text2);
        
    }
    
    public static interface Model {
        public int getId();
        public void setId(int value);
    }
}