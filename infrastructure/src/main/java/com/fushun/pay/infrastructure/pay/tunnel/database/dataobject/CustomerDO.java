package com.fushun.pay.infrastructure.pay.tunnel.database.dataobject;

import com.alibaba.cola.tunnel.DataObject;
import lombok.Data;

@Data
public class CustomerDO extends DataObject {
    private String customerId;
    private String memberId;
    private String globalId;
    private String companyName;
    private String source;
    private String companyType;
}
