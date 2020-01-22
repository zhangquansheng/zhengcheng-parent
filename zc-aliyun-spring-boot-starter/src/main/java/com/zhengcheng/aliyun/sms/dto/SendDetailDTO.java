package com.zhengcheng.aliyun.sms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询短信发送详情
 * https://api.aliyun.com/new?spm=a2c4g.11186623.2.13.485b19d9zpwP2e#/?product=Dysmsapi&version=2017-05-25&api=QuerySendDetails&params={%22RegionId%22:%22cn-hangzhou%22,%22PhoneNumber%22:%221212%22,%22SendDate%22:%221212%22,%22PageSize%22:%221%22,%22CurrentPage%22:%2212%22,%22BizId%22:%2212%22}&tab=DOC&lang=JAVA
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 13:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SendDetailDTO extends SmsDataDTO {

    private static final long serialVersionUID = 4411231750611135428L;

    /**
     * 短信发送总条数
     */
    private Long totalCount;

    private SmsSendDetailDTOsBean smsSendDetailDTOs;

    /**
     * 短信发送明细
     */
    @Data
    public static class SmsSendDetailDTOsBean implements Serializable {

        private static final long serialVersionUID = 4525949525480562025L;
        private SmsSendDetailDTOBean smsSendDetailDTO;

        @Data
        public static class SmsSendDetailDTOBean {

            /**
             * 短信发送日期和时间。
             */
            private String sendDate;
            /**
             * 外部流水扩展字段
             */
            private String outId;
            /**
             * 短信发送状态，包括：
             * <p>
             * 1：等待回执。
             * 2：发送失败。
             * 3：发送成功。
             */
            private Long sendStatus;
            /**
             * 短信接收日期和时间。
             */
            private String receiveDate;
            /**
             * 运营商短信状态码。
             * <p>
             * 短信发送成功：DELIVERED。
             * 短信发送失败：失败错误码请参考错误码文档。
             */
            private String errCode;
            /**
             * 短信模板ID
             */
            private String templateCode;
            /**
             * 短信内容
             */
            private String content;
            /**
             * 接收短信的手机号码
             */
            private String phoneNum;
        }
    }
}
