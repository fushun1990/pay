package com.fushun.pay.app.dto.clientobject.createpay.response;

import com.fushun.pay.app.dto.clientobject.createpay.EStatus;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class CreatedPayVO {

    /**
     * 处理状态
     */
    private EStatus status;
}
