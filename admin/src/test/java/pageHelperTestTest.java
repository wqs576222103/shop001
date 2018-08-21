import org.cdlg.mapper.ItemMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class pageHelperTestTest {

    @Autowired
    ItemMapper itemMapper;

    @Test
    public  void pageTest(){
       /* PageHelper.startPage(1,2);
        List<Items> exa=itemsMapper.selectByExample(null);
        PageInfo info=new PageInfo(exa);*/

    }
}