package com.zhengcheng.magic.controller.facade.internal.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EchartsOption
 *
 * @author quansheng1.zhang
 * @since 2021/5/25 20:24
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EchartsOptionDTO {

    private TitleBean title;
    private TooltipBean tooltip;
    private LegendBean legend;
    private GridBean grid;
    @JsonProperty("xAxis")
    private AxisBean xAxis;
    @JsonProperty("yAxis")
    private AxisBean yAxis;
    private List<SeriesBean> series;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TitleBean {

        private String text;

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TooltipBean {

        private String trigger;

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LegendBean {

        private List<String> data;

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GridBean {

        private String left;
        private String right;
        private String bottom;
        private boolean containLabel;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AxisBean {

        private String type;
        private boolean boundaryGap;
        private List<Integer> data;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SeriesBean {

        private String name;
        private String type;
        /**
         * https://github.com/apache/echarts/issues/9101 stack 不能赋值
         */
        private String stack;
        /**
         *
         */
        private boolean smooth;
        private boolean showSymbol;
        private List<Integer> data;
    }
}
