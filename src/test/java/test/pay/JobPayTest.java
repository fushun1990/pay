package test.pay;

import java.util.List;

import cn.kidtop.business.pay.PayApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.facade.JobPayFacade;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2017/6/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayApplication.class)
public class JobPayTest{

    @Autowired
    private JobPayFacade jobPayFacade;

    @Test
    public void jobTest(){

        List<RecordPayDTO> list= jobPayFacade.getOnlinePayOrderJob(0,20);

        for (RecordPayDTO recordPayDTO:list) {

            jobPayFacade.getOnlinePayOrderJobExecute(recordPayDTO);
        }
    }

}
