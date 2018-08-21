import org.cdlg.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ItemsMapperTest {

   /*@Autowired
    ItemMapper itemsMapper;*/
   @Autowired
    ItemService itemService;

    @Test
    public  void testMapper(){
      /*PageHelper.startPage(0,2);
      if(itemsMapper!=null) {
          List<Items> items = itemsMapper.selectByExample(null);
          PageInfo<Items> info = new PageInfo<Items>(items);
          //System.out.println(info.getList());
      }*/
      //  Item item=itemService.queryById(41L);


    }


}