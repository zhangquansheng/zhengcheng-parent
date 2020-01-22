package com.zhengcheng.aliyun.green.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * ImageResult
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 22:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageResult extends BaseResult {
    /**
     * 图片中含有二维码时，返回图片中所有二维码包含的文本信息。
     * 说明 仅适用于qrcode场景。
     */
    private List<String> qrcodeData;
    /**
     * 识别到的图片中的完整文字信息。
     * 说明 默认不返回，如需返回请通过工单联系我们。
     */
    private List<String> ocrData;
}
