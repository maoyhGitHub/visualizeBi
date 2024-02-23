package io.dataease.service.chart.util;

import cn.hutool.core.util.ArrayUtil;
import io.dataease.dto.chart.*;
import io.dataease.plugins.common.base.domain.ChartViewWithBLOBs;
import io.dataease.plugins.common.dto.chart.ChartViewFieldDTO;
import io.dataease.plugins.xpack.auth.dto.request.ColumnPermissionItem;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class ChartDataBuild {

    private final static String format = "(%s)";

    // AntV
    public static Map<String, Object> transChartDataAntV(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<AxisChartDataAntVDTO> dataList = new ArrayList<>();
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] row = data.get(i1);

            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(row[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(row[i]);
                    } else {
                        a.append(row[i]).append("\n");
                    }
                }
            }

            if (StringUtils.containsIgnoreCase(view.getType(), "table")) {
                for (int i = 0; i < xAxis.size() + yAxis.size(); i++) {
                    AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
                    axisChartDataDTO.setField(a.toString());
                    axisChartDataDTO.setName(a.toString());

                    List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                    List<ChartQuotaDTO> quotaList = new ArrayList<>();

                    for (int j = 0; j < xAxis.size(); j++) {
                        ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                        chartDimensionDTO.setId(xAxis.get(j).getId());
                        chartDimensionDTO.setValue(row[j]);
                        dimensionList.add(chartDimensionDTO);
                    }
                    axisChartDataDTO.setDimensionList(dimensionList);

                    int j = i - xAxis.size();
                    if (j > -1) {
                        ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                        chartQuotaDTO.setId(yAxis.get(j).getId());
                        quotaList.add(chartQuotaDTO);
                        axisChartDataDTO.setQuotaList(quotaList);
                        try {
                            axisChartDataDTO.setValue(StringUtils.isEmpty(row[i]) ? null : new BigDecimal(row[i]));
                        } catch (Exception e) {
                            axisChartDataDTO.setValue(new BigDecimal(0));
                        }
                        axisChartDataDTO.setCategory(yAxis.get(j).getName());
                    }
                    dataList.add(axisChartDataDTO);
                }
            } else {
                for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                    AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
                    axisChartDataDTO.setField(a.toString());
                    axisChartDataDTO.setName(a.toString());

                    List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                    List<ChartQuotaDTO> quotaList = new ArrayList<>();

                    for (int j = 0; j < xAxis.size(); j++) {
                        ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                        chartDimensionDTO.setId(xAxis.get(j).getId());
                        chartDimensionDTO.setValue(row[j]);
                        dimensionList.add(chartDimensionDTO);
                    }
                    axisChartDataDTO.setDimensionList(dimensionList);

                    int j = i - xAxis.size();
                    ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                    chartQuotaDTO.setId(yAxis.get(j).getId());
                    quotaList.add(chartQuotaDTO);
                    axisChartDataDTO.setQuotaList(quotaList);
                    try {
                        axisChartDataDTO.setValue(StringUtils.isEmpty(row[i]) ? null : new BigDecimal(row[i]));
                    } catch (Exception e) {
                        axisChartDataDTO.setValue(new BigDecimal(0));
                    }
                    axisChartDataDTO.setCategory(yAxis.get(j).getName());
                    dataList.add(axisChartDataDTO);
                }
            }
        }
        map.put("data", dataList);
        return map;
    }

    public static Map<String, Object> transBaseGroupDataAntV(List<ChartViewFieldDTO> xAxisBase, List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> xAxisExt, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<AxisChartDataAntVDTO> dataList = new ArrayList<>();
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] row = data.get(i1);

            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(row[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxisBase.size(); i++) {
                    if (i == xAxisBase.size() - 1) {
                        a.append(row[i]);
                    } else {
                        a.append(row[i]).append("\n");
                    }
                }
            }

            StringBuilder b = new StringBuilder();
            for (int i = xAxisBase.size(); i < xAxisBase.size() + xAxisExt.size(); i++) {
                if (i == xAxisBase.size() + xAxisExt.size() - 1) {
                    b.append(row[i]);
                } else {
                    b.append(row[i]).append("\n");
                }
            }

            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
                axisChartDataDTO.setField(a.toString());
                axisChartDataDTO.setName(a.toString());

                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();

                for (int j = 0; j < xAxis.size(); j++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(j).getId());
                    chartDimensionDTO.setValue(row[j]);
                    dimensionList.add(chartDimensionDTO);
                }
                axisChartDataDTO.setDimensionList(dimensionList);

                int j = i - xAxis.size();
                ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                chartQuotaDTO.setId(yAxis.get(j).getId());
                quotaList.add(chartQuotaDTO);
                axisChartDataDTO.setQuotaList(quotaList);
                try {
                    axisChartDataDTO.setValue(StringUtils.isEmpty(row[i]) ? null : new BigDecimal(row[i]));
                } catch (Exception e) {
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                axisChartDataDTO.setCategory(b.toString());
                dataList.add(axisChartDataDTO);

                if ("line".equals(view.getType())) {
                    if (CollectionUtils.isEmpty(xAxisExt)) {
                        axisChartDataDTO.setCategory(yAxis.get(j).getName());
                    } else {
                        // 多指标只取第一个
                        break;
                    }
                }
            }
        }
        map.put("data", dataList);
        return map;
    }

    // AntV柱状堆叠图
    public static Map<String, Object> transStackChartDataAntV(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, List<ChartViewFieldDTO> extStack, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<AxisChartDataAntVDTO> dataList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(extStack)) {
            for (int i1 = 0; i1 < data.size(); i1++) {
                String[] row = data.get(i1);

                AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
                StringBuilder a = new StringBuilder();
                if (isDrill) {
                    a.append(row[xAxis.size() - 1]);
                } else {
                    for (int i = 0; i < xAxis.size(); i++) {
                        if (i == xAxis.size() - 1) {
                            a.append(row[i]);
                        } else {
                            a.append(row[i]).append("\n");
                        }
                    }
                }
                axisChartDataDTO.setField(a.toString());
                axisChartDataDTO.setName(a.toString());
                axisChartDataDTO.setCategory(row[xAxis.size()]);

                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();

                for (int k = 0; k < xAxis.size(); k++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(k).getId());
                    chartDimensionDTO.setValue(row[k]);
                    dimensionList.add(chartDimensionDTO);
                }
                ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                chartDimensionDTO.setId(extStack.get(0).getId());
                chartDimensionDTO.setValue(row[xAxis.size()]);
                dimensionList.add(chartDimensionDTO);
                axisChartDataDTO.setDimensionList(dimensionList);

                if (CollectionUtils.isNotEmpty(yAxis)) {
                    int valueIndex = xAxis.size() + extStack.size();
                    ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                    chartQuotaDTO.setId(yAxis.get(0).getId());
                    quotaList.add(chartQuotaDTO);
                    axisChartDataDTO.setQuotaList(quotaList);
                    try {
                        axisChartDataDTO.setValue(StringUtils.isEmpty(row[valueIndex]) ? null : new BigDecimal(row[valueIndex]));
                    } catch (Exception e) {
                        axisChartDataDTO.setValue(new BigDecimal(0));
                    }
                } else {
                    axisChartDataDTO.setQuotaList(quotaList);
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                dataList.add(axisChartDataDTO);
            }
        } else {
            for (int i1 = 0; i1 < data.size(); i1++) {
                String[] row = data.get(i1);

                StringBuilder a = new StringBuilder();
                if (isDrill) {
                    a.append(row[xAxis.size() - 1]);
                } else {
                    for (int i = 0; i < xAxis.size(); i++) {
                        if (i == xAxis.size() - 1) {
                            a.append(row[i]);
                        } else {
                            a.append(row[i]).append("\n");
                        }
                    }
                }

                for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                    AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
                    axisChartDataDTO.setField(a.toString());
                    axisChartDataDTO.setName(a.toString());

                    List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                    List<ChartQuotaDTO> quotaList = new ArrayList<>();

                    for (int j = 0; j < xAxis.size(); j++) {
                        ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                        chartDimensionDTO.setId(xAxis.get(j).getId());
                        chartDimensionDTO.setValue(row[j]);
                        dimensionList.add(chartDimensionDTO);
                    }
                    axisChartDataDTO.setDimensionList(dimensionList);

                    int j = i - xAxis.size();
                    ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                    chartQuotaDTO.setId(yAxis.get(j).getId());
                    quotaList.add(chartQuotaDTO);
                    axisChartDataDTO.setQuotaList(quotaList);
                    try {
                        axisChartDataDTO.setValue(StringUtils.isEmpty(row[i]) ? null : new BigDecimal(row[i]));
                    } catch (Exception e) {
                        axisChartDataDTO.setValue(new BigDecimal(0));
                    }
                    axisChartDataDTO.setCategory(yAxis.get(j).getName());
                    dataList.add(axisChartDataDTO);
                }
            }
        }
        map.put("data", dataList);
        return map;
    }

    //AntV scatter
    public static Map<String, Object> transScatterDataAntV(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, List<ChartViewFieldDTO> extBubble, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<AxisChartDataAntVDTO> dataList = new ArrayList<>();
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] row = data.get(i1);

            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(row[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(row[i]);
                    } else {
                        a.append(row[i]).append("\n");
                    }
                }
            }

            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
                axisChartDataDTO.setField(a.toString());
                axisChartDataDTO.setName(a.toString());

                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();

                for (int j = 0; j < xAxis.size(); j++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(j).getId());
                    chartDimensionDTO.setValue(row[j]);
                    dimensionList.add(chartDimensionDTO);
                }
                axisChartDataDTO.setDimensionList(dimensionList);

                int j = i - xAxis.size();
                ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                chartQuotaDTO.setId(yAxis.get(j).getId());
                quotaList.add(chartQuotaDTO);
                axisChartDataDTO.setQuotaList(quotaList);
                try {
                    axisChartDataDTO.setValue(StringUtils.isEmpty(row[i]) ? null : new BigDecimal(row[i]));
                } catch (Exception e) {
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                axisChartDataDTO.setCategory(yAxis.get(j).getName());
                // pop
                if (CollectionUtils.isNotEmpty(extBubble)) {
                    try {
                        axisChartDataDTO.setPopSize(StringUtils.isEmpty(row[xAxis.size() + yAxis.size()]) ? null : new BigDecimal(row[xAxis.size() + yAxis.size()]));
                    } catch (Exception e) {
                        axisChartDataDTO.setPopSize(new BigDecimal(0));
                    }
                }
                dataList.add(axisChartDataDTO);
            }
        }
        map.put("data", dataList);
        return map;
    }

    // antv radar
    public static Map<String, Object> transRadarChartDataAntV(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<AxisChartDataAntVDTO> dataList = new ArrayList<>();
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] row = data.get(i1);

            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(row[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(row[i]);
                    } else {
                        a.append(row[i]).append("\n");
                    }
                }
            }

            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
                axisChartDataDTO.setField(a.toString());
                axisChartDataDTO.setName(a.toString());

                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();

                for (int j = 0; j < xAxis.size(); j++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(j).getId());
                    chartDimensionDTO.setValue(row[j]);
                    dimensionList.add(chartDimensionDTO);
                }
                axisChartDataDTO.setDimensionList(dimensionList);

                int j = i - xAxis.size();
                ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                chartQuotaDTO.setId(yAxis.get(j).getId());
                quotaList.add(chartQuotaDTO);
                axisChartDataDTO.setQuotaList(quotaList);
                try {
                    axisChartDataDTO.setValue(StringUtils.isEmpty(row[i]) ? null : new BigDecimal(row[i]));
                } catch (Exception e) {
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                axisChartDataDTO.setCategory(yAxis.get(j).getName());
                dataList.add(axisChartDataDTO);
            }
        }
        map.put("data", dataList);
        return map;
    }

    // antV组合图形
    public static Map<String, Object> transMixChartDataAntV(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<Series> series = new ArrayList<>();
        for (ChartViewFieldDTO y : yAxis) {
            Series series1 = new Series();
            series1.setName(y.getName());
            series1.setType(y.getChartType());
            series1.setData(new ArrayList<>());
            series.add(series1);
        }
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] d = data.get(i1);

            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();

                StringBuilder a = new StringBuilder();
                if (isDrill) {
                    a.append(d[xAxis.size() - 1]);
                } else {
                    for (int ii = 0; ii < xAxis.size(); ii++) {
                        if (ii == xAxis.size() - 1) {
                            a.append(d[ii]);
                        } else {
                            a.append(d[ii]).append("\n");
                        }
                    }
                }
                axisChartDataDTO.setName(a.toString());
                axisChartDataDTO.setField(a.toString());

                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();

                for (int j = 0; j < xAxis.size(); j++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(j).getId());
                    chartDimensionDTO.setValue(d[j]);
                    dimensionList.add(chartDimensionDTO);
                }
                axisChartDataDTO.setDimensionList(dimensionList);

                int j = i - xAxis.size();
                ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                chartQuotaDTO.setId(yAxis.get(j).getId());
                quotaList.add(chartQuotaDTO);
                axisChartDataDTO.setQuotaList(quotaList);
                try {
                    axisChartDataDTO.setValue(StringUtils.isEmpty(d[i]) ? null : new BigDecimal(d[i]));
                } catch (Exception e) {
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                axisChartDataDTO.setCategory(yAxis.get(j).getName());
                series.get(j).getData().add(axisChartDataDTO);
            }
        }

        map.put("data", series);
        return map;
    }

    // 基础图形
    public static Map<String, Object> transChartData(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<String> x = new ArrayList<>();
        List<Series> series = new ArrayList<>();
        for (ChartViewFieldDTO y : yAxis) {
            Series series1 = new Series();
            series1.setName(y.getName());
            series1.setType(view.getType());
            series1.setData(new ArrayList<>());
            series.add(series1);
        }
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] d = data.get(i1);

            StringBuilder a = new StringBuilder();
            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();
                AxisChartDataDTO axisChartDataDTO = new AxisChartDataDTO();

                for (int j = 0; j < xAxis.size(); j++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(j).getId());
                    chartDimensionDTO.setValue(d[j]);
                    dimensionList.add(chartDimensionDTO);
                }
                axisChartDataDTO.setDimensionList(dimensionList);

                int j = i - xAxis.size();
                ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                chartQuotaDTO.setId(yAxis.get(j).getId());
                quotaList.add(chartQuotaDTO);
                axisChartDataDTO.setQuotaList(quotaList);
                try {
                    axisChartDataDTO.setValue(StringUtils.isEmpty(d[i]) ? null : new BigDecimal(d[i]));
                } catch (Exception e) {
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                series.get(j).getData().add(axisChartDataDTO);
            }
            if (isDrill) {
                a.append(d[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(d[i]);
                    } else {
                        a.append(d[i]).append("\n");
                    }
                }
            }
            x.add(a.toString());
        }

        map.put("x", x);
        map.put("series", series);
        return map;
    }

    // 组合图形
    public static Map<String, Object> transMixChartData(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<String> x = new ArrayList<>();
        List<Series> series = new ArrayList<>();
        for (ChartViewFieldDTO y : yAxis) {
            Series series1 = new Series();
            series1.setName(y.getName());
            series1.setType(y.getChartType());
            series1.setData(new ArrayList<>());
            series.add(series1);
        }
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] d = data.get(i1);

            StringBuilder a = new StringBuilder();
            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();
                AxisChartDataDTO axisChartDataDTO = new AxisChartDataDTO();

                for (int j = 0; j < xAxis.size(); j++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(j).getId());
                    chartDimensionDTO.setValue(d[j]);
                    dimensionList.add(chartDimensionDTO);
                }
                axisChartDataDTO.setDimensionList(dimensionList);

                int j = i - xAxis.size();
                ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                chartQuotaDTO.setId(yAxis.get(j).getId());
                quotaList.add(chartQuotaDTO);
                axisChartDataDTO.setQuotaList(quotaList);
                try {
                    axisChartDataDTO.setValue(StringUtils.isEmpty(d[i]) ? null : new BigDecimal(d[i]));
                } catch (Exception e) {
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                series.get(j).getData().add(axisChartDataDTO);
            }
            if (isDrill) {
                a.append(d[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(d[i]);
                    } else {
                        a.append(d[i]).append("\n");
                    }
                }
            }
            x.add(a.toString());
        }

        map.put("x", x);
        map.put("series", series);
        return map;
    }

    // 文本卡图形
    public static Map<String, Object> transLabelChartData(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<String> x = new ArrayList<>();
        List<Series> series = new ArrayList<>();
        Series series1 = new Series();
        series1.setName(xAxis.get(0).getName());
        series1.setType(view.getType());
        series1.setData(new ArrayList<>());
        series.add(series1);
        for (String[] d : data) {
            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(d[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(d[i]);
                    } else {
                        a.append(d[i]).append("\n");
                    }
                }
            }
            x.add(a.toString());
            series.get(0).getData().add(a.toString());
        }

        map.put("x", x);
        map.put("series", series);
        return map;
    }

    // 常规图形
    public static Map<String, Object> transNormalChartData(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<String> x = new ArrayList<>();
        List<Series> series = new ArrayList<>();
        for (ChartViewFieldDTO y : yAxis) {
            Series series1 = new Series();
            series1.setName(y.getName());
            series1.setType(view.getType());
            series1.setData(new ArrayList<>());
            series.add(series1);
        }
        for (String[] d : data) {
            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(d[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(d[i]);
                    } else {
                        a.append(d[i]).append("\n");
                    }
                }
            }
            x.add(a.toString());
            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                int j = i - xAxis.size();
                try {
                    series.get(j).getData().add(StringUtils.isEmpty(d[i]) ? null : new BigDecimal(d[i]));
                } catch (Exception e) {
                    series.get(j).getData().add(new BigDecimal(0));
                }
            }
        }

        map.put("x", x);
        map.put("series", series);
        return map;
    }

    // radar图
    public static Map<String, Object> transRadarChartData(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<String> x = new ArrayList<>();
        List<Series> series = new ArrayList<>();
        for (ChartViewFieldDTO y : yAxis) {
            Series series1 = new Series();
            series1.setName(y.getName());
            series1.setType(view.getType());
            series1.setData(new ArrayList<>());
            series.add(series1);
        }
        for (String[] d : data) {
            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(d[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(d[i]);
                    } else {
                        a.append(d[i]).append("\n");
                    }
                }
            }
            x.add(a.toString());
            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                int j = i - xAxis.size();
                try {
                    series.get(j).getData().add(StringUtils.isEmpty(d[i]) ? null : new BigDecimal(d[i]));
                } catch (Exception e) {
                    series.get(j).getData().add(new BigDecimal(0));
                }
            }
        }

        map.put("x", x);
        map.put("series", series);
        return map;
    }

    // 堆叠图
    public static Map<String, Object> transStackChartData(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, List<ChartViewFieldDTO> extStack, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<String> x = new ArrayList<>();
        List<String> stack = new ArrayList<>();
        List<Series> series = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(extStack)) {
            AxisChartDataDTO defaultAxisChartDataDTO = new AxisChartDataDTO();
            BigDecimal defaultValue = StringUtils.containsIgnoreCase(view.getType(), "line") ? new BigDecimal(0) : null;
            defaultAxisChartDataDTO.setValue(defaultValue);
            // 构建横轴
            for (String[] d : data) {
                StringBuilder a = new StringBuilder();
                if (isDrill) {
                    a.append(d[xAxis.size() - 1]);
                } else {
                    for (int i = 0; i < xAxis.size(); i++) {
                        if (i == xAxis.size() - 1) {
                            a.append(d[i]);
                        } else {
                            a.append(d[i]).append("\n");
                        }
                    }
                }
                x.add(a.toString());
            }
            x = x.stream().distinct().collect(Collectors.toList());
            // 构建堆叠
            for (String[] d : data) {
                stack.add(d[xAxis.size()]);
            }
            stack = stack.stream().distinct().collect(Collectors.toList());
            for (String s : stack) {
                Series series1 = new Series();
                series1.setName(s);
                series1.setType(view.getType());
                List<Object> list = new ArrayList<>();
                for (int i = 0; i < x.size(); i++) {
                    list.add(defaultAxisChartDataDTO);
                }
                series1.setData(list);
                series.add(series1);
            }
            for (Series ss : series) {
                for (int i = 0; i < x.size(); i++) {
                    for (String[] row : data) {
                        String stackColumn = row[xAxis.size()];
                        if (StringUtils.equals(ss.getName(), stackColumn)) {
                            StringBuilder a = new StringBuilder();
                            if (isDrill) {
                                a.append(row[xAxis.size() - 1]);
                            } else {
                                for (int j = 0; j < xAxis.size(); j++) {
                                    if (j == xAxis.size() - 1) {
                                        a.append(row[j]);
                                    } else {
                                        a.append(row[j]).append("\n");
                                    }
                                }
                            }
                            if (StringUtils.equals(a.toString(), x.get(i))) {
                                if (row.length > xAxis.size() + extStack.size()) {
                                    List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                                    List<ChartQuotaDTO> quotaList = new ArrayList<>();
                                    AxisChartDataDTO axisChartDataDTO = new AxisChartDataDTO();

                                    ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                                    chartQuotaDTO.setId(yAxis.get(0).getId());
                                    quotaList.add(chartQuotaDTO);
                                    axisChartDataDTO.setQuotaList(quotaList);

                                    for (int k = 0; k < xAxis.size(); k++) {
                                        ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                                        chartDimensionDTO.setId(xAxis.get(k).getId());
                                        chartDimensionDTO.setValue(row[k]);
                                        dimensionList.add(chartDimensionDTO);
                                    }
                                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                                    chartDimensionDTO.setId(extStack.get(0).getId());
                                    chartDimensionDTO.setValue(row[xAxis.size()]);
                                    dimensionList.add(chartDimensionDTO);
                                    axisChartDataDTO.setDimensionList(dimensionList);

                                    String s = row[xAxis.size() + extStack.size()];
                                    if (StringUtils.isNotEmpty(s)) {
                                        axisChartDataDTO.setValue(new BigDecimal(s));
                                        ss.getData().set(i, axisChartDataDTO);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            for (ChartViewFieldDTO y : yAxis) {
                Series series1 = new Series();
                series1.setName(y.getName());
                series1.setType(view.getType());
                series1.setData(new ArrayList<>());
                series.add(series1);
            }
            for (int i1 = 0; i1 < data.size(); i1++) {
                String[] d = data.get(i1);

                StringBuilder a = new StringBuilder();
                for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                    List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                    List<ChartQuotaDTO> quotaList = new ArrayList<>();
                    AxisChartDataDTO axisChartDataDTO = new AxisChartDataDTO();

                    for (int j = 0; j < xAxis.size(); j++) {
                        ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                        chartDimensionDTO.setId(xAxis.get(j).getId());
                        chartDimensionDTO.setValue(d[j]);
                        dimensionList.add(chartDimensionDTO);
                    }
                    axisChartDataDTO.setDimensionList(dimensionList);

                    int j = i - xAxis.size();
                    ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                    chartQuotaDTO.setId(yAxis.get(j).getId());
                    quotaList.add(chartQuotaDTO);
                    axisChartDataDTO.setQuotaList(quotaList);
                    try {
                        axisChartDataDTO.setValue(StringUtils.isEmpty(d[i]) ? null : new BigDecimal(d[i]));
                    } catch (Exception e) {
                        axisChartDataDTO.setValue(new BigDecimal(0));
                    }
                    series.get(j).getData().add(axisChartDataDTO);
                }
                if (isDrill) {
                    a.append(d[xAxis.size() - 1]);
                } else {
                    for (int i = 0; i < xAxis.size(); i++) {
                        if (i == xAxis.size() - 1) {
                            a.append(d[i]);
                        } else {
                            a.append(d[i]).append("\n");
                        }
                    }
                }
                x.add(a.toString());
            }
        }

        map.put("x", x);
        map.put("series", series);
        return map;
    }

    // 散点图
    public static Map<String, Object> transScatterData(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, List<ChartViewFieldDTO> extBubble, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<String> x = new ArrayList<>();
        List<Series> series = new ArrayList<>();
        for (ChartViewFieldDTO y : yAxis) {
            Series series1 = new Series();
            series1.setName(y.getName());
            series1.setType(view.getType());
            series1.setData(new ArrayList<>());
            series.add(series1);
        }
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] d = data.get(i1);

            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(d[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(d[i]);
                    } else {
                        a.append(d[i]).append("\n");
                    }
                }
            }
            x.add(a.toString());
            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();
                ScatterChartDataDTO scatterChartDataDTO = new ScatterChartDataDTO();

                for (int j = 0; j < xAxis.size(); j++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(j).getId());
                    chartDimensionDTO.setValue(d[j]);
                    dimensionList.add(chartDimensionDTO);
                }
                scatterChartDataDTO.setDimensionList(dimensionList);

                int j = i - xAxis.size();
                ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                chartQuotaDTO.setId(yAxis.get(j).getId());
                quotaList.add(chartQuotaDTO);
                scatterChartDataDTO.setQuotaList(quotaList);

                if (CollectionUtils.isNotEmpty(extBubble) && extBubble.size() > 0) {
                    try {
                        scatterChartDataDTO.setValue(new Object[]{
                                a.toString(),
                                StringUtils.isEmpty(d[i]) ? null : new BigDecimal(d[i]),
                                StringUtils.isEmpty(d[xAxis.size() + yAxis.size()]) ? null : new BigDecimal(d[xAxis.size() + yAxis.size()])
                        });
                    } catch (Exception e) {
                        scatterChartDataDTO.setValue(new Object[]{a.toString(), new BigDecimal(0), new BigDecimal(0)});
                    }
                } else {
                    try {
                        scatterChartDataDTO.setValue(new Object[]{
                                a.toString(),
                                StringUtils.isEmpty(d[i]) ? null : new BigDecimal(d[i])
                        });
                    } catch (Exception e) {
                        scatterChartDataDTO.setValue(new Object[]{a.toString(), new BigDecimal(0)});
                    }
                }
                series.get(j).getData().add(scatterChartDataDTO);
            }
        }

        map.put("x", x);
        map.put("series", series);
        return map;
    }

    // 表格
    public static Map<String, Object> transTableNormal(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewWithBLOBs view, List<String[]> data, List<ChartViewFieldDTO> extStack, Map<String, ColumnPermissionItem> desensitizationList) {
        List<ChartViewFieldDTO> fields = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(xAxis)) {
            fields.addAll(xAxis);
        }
        if (StringUtils.containsIgnoreCase(view.getType(), "stack")) {
            if (ObjectUtils.isNotEmpty(extStack)) {
                fields.addAll(extStack);
            }
        }
        fields.addAll(yAxis);
        return transTableNormal(fields, view, data, desensitizationList);
    }

    public static Map<String, Object> transTableNormalWithDetail(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, List<String[]> data, List<ChartViewFieldDTO> detailFields, List<String[]> detailData, Map<String, ColumnPermissionItem> desensitizationList) {
        int detailIndex = xAxis.size();

        List<ChartViewFieldDTO> realDetailFields = detailFields.subList(detailIndex, detailFields.size());

        List<ChartViewFieldDTO> fields = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(xAxis))
            fields.addAll(xAxis);
        if (CollectionUtils.isNotEmpty(yAxis))
            fields.addAll(yAxis);
        Map<String, Object> map = transTableNormal(fields, null, data, desensitizationList);
        List<Map<String, Object>> tableRow = (List<Map<String, Object>>) map.get("tableRow");
        final int xEndIndex = detailIndex;
        Map<String, List<String[]>> groupDataList = detailData.stream().collect(Collectors.groupingBy(item -> ArrayUtil.join(ArrayUtil.sub(item, 0, xEndIndex), "-de-", "(", ")")));

        tableRow.forEach(row -> {
            String key = xAxis.stream().map(x -> String.format(format, row.get(x.getDataeaseName()).toString())).collect(Collectors.joining("-de-"));
            List<String[]> detailFieldValueList = groupDataList.get(key);
            List<Map<String, Object>> detailValueMapList = detailFieldValueList.stream().map((detailArr -> {
                Map<String, Object> temp = new HashMap<>();
                for (int i = 0; i < realDetailFields.size(); i++) {
                    ChartViewFieldDTO realDetailField = realDetailFields.get(i);
                    temp.put(realDetailField.getDataeaseName(), detailArr[detailIndex + i]);
                }
                return temp;
            })).collect(Collectors.toList());
            row.put("details", detailValueMapList);
        });

        ChartViewFieldDTO detailFieldDTO = new ChartViewFieldDTO();
        detailFieldDTO.setId("DataEase-Detail");
        detailFieldDTO.setName("detail");
        detailFieldDTO.setDataeaseName("detail");
        fields.add(detailFieldDTO);
        map.put("fields", fields);
        map.put("detailFields", realDetailFields);
        map.put("tableRow", tableRow);
        return map;
    }

    // 表格
    public static Map<String, Object> transTableNormal(Map<String, List<ChartViewFieldDTO>> fieldMap, ChartViewWithBLOBs view, List<String[]> data, Map<String, ColumnPermissionItem> desensitizationList) {

        List<ChartViewFieldDTO> fields = new ArrayList<>();
        List<ChartViewFieldDTO> yfields = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fieldMap.get("xAxis"))) fields.addAll(fieldMap.get("xAxis"));
        if (CollectionUtils.isNotEmpty(fieldMap.get("tooltipAxis"))) {
            fieldMap.get("tooltipAxis").forEach(field -> {
                Integer deType = field.getDeType();
                if (deType == 2 || deType == 3) {
                    yfields.add(field);
                } else {
                    fields.add(field);
                }
            });
        }
        if (CollectionUtils.isNotEmpty(fieldMap.get("labelAxis"))) {
            fieldMap.get("labelAxis").forEach(field -> {
                Integer deType = field.getDeType();
                if (deType == 2 || deType == 3) {
                    yfields.add(field);
                } else {
                    fields.add(field);
                }
            });
        }
        if (CollectionUtils.isNotEmpty(fieldMap.get("yAxis"))) fields.addAll(fieldMap.get("yAxis"));
        if (CollectionUtils.isNotEmpty(yfields)) fields.addAll(yfields);
        return transTableNormal(fields, view, data, desensitizationList);
    }

    public static String desensitizationValue(ColumnPermissionItem columnPermissionItem, String originStr) {
        String desensitizationStr = "";
        if (!columnPermissionItem.getDesensitizationRule().getBuiltInRule().toString().equalsIgnoreCase("custom")) {
            switch (columnPermissionItem.getDesensitizationRule().getBuiltInRule()) {
                case CompleteDesensitization:
                    desensitizationStr = ColumnPermissionItem.CompleteDesensitization;
                    break;
                case KeepMiddleThreeCharacters:
                    if (StringUtils.isEmpty(originStr) || originStr.length() < 4) {
                        desensitizationStr = ColumnPermissionItem.KeepMiddleThreeCharacters;
                    } else {
                        desensitizationStr = "***" + StringUtils.substring(originStr, originStr.length() / 2 - 1, originStr.length() / 2 + 2) + "***";
                    }
                    break;
                case KeepFirstAndLastThreeCharacters:
                    if (StringUtils.isEmpty(originStr) || originStr.length() < 7) {
                        desensitizationStr = ColumnPermissionItem.KeepFirstAndLastThreeCharacters;
                    } else {
                        desensitizationStr = StringUtils.substring(originStr, 0, 3) + "***" + StringUtils.substring(originStr, originStr.length() - 3, originStr.length());
                    }
                    break;
                default:
                    break;

            }
        } else {
            switch (columnPermissionItem.getDesensitizationRule().getCustomBuiltInRule()) {
                case RetainBeforeMAndAfterN:
                    if (StringUtils.isEmpty(originStr) || originStr.length() <= columnPermissionItem.getDesensitizationRule().getM() + columnPermissionItem.getDesensitizationRule().getN() + 1) {
                        desensitizationStr = String.join("", Collections.nCopies(columnPermissionItem.getDesensitizationRule().getM(), "X")) + "***" + String.join("", Collections.nCopies(columnPermissionItem.getDesensitizationRule().getN(), "X"));
                    } else {
                        desensitizationStr = StringUtils.substring(originStr, 0, columnPermissionItem.getDesensitizationRule().getM()) + "***" + StringUtils.substring(originStr, originStr.length() - columnPermissionItem.getDesensitizationRule().getN() - 1, originStr.length() - 1);
                    }
                    break;
                case RetainMToN:
                    if (columnPermissionItem.getDesensitizationRule().getM() > columnPermissionItem.getDesensitizationRule().getN()) {
                        desensitizationStr = "*** ***";
                        break;
                    }
                    if (StringUtils.isEmpty(originStr) || originStr.length() < columnPermissionItem.getDesensitizationRule().getM()) {
                        desensitizationStr = "*** ***";
                        break;
                    }
                    if (originStr.length() >= columnPermissionItem.getDesensitizationRule().getM() && originStr.length() >= columnPermissionItem.getDesensitizationRule().getN()) {
                        desensitizationStr = buildPrefix(columnPermissionItem.getDesensitizationRule().getM()) + StringUtils.substring(originStr, columnPermissionItem.getDesensitizationRule().getM() - 1, columnPermissionItem.getDesensitizationRule().getN()) + "***";
                        break;
                    }
                    if (originStr.length() >= columnPermissionItem.getDesensitizationRule().getM() && originStr.length() < columnPermissionItem.getDesensitizationRule().getN()) {
                        desensitizationStr = buildPrefix(columnPermissionItem.getDesensitizationRule().getM()) + StringUtils.substring(originStr, columnPermissionItem.getDesensitizationRule().getM() - 1, originStr.length());
                    }
                    break;
                default:
                    break;

            }
        }
        return desensitizationStr;
    }


    private static String buildPrefix(Integer integer) {
        String prefix = "";
        if (integer <= 1) {
            return prefix;
        }
        for (int i = 0; i < integer - 1; i++) {
            prefix = prefix + "*";
        }
        return prefix;
    }

    private static Map<String, Object> transTableNormal(List<ChartViewFieldDTO> fields, ChartViewWithBLOBs view, List<String[]> data, Map<String, ColumnPermissionItem> desensitizationList) {
        Map<String, Object> map = new TreeMap<>();
        List<Map<String, Object>> tableRow = new ArrayList<>();
        data.forEach(ele -> {
            Map<String, Object> d = new HashMap<>();
            for (int i = 0; i < fields.size(); i++) {
                if (CollectionUtils.isNotEmpty(desensitizationList.keySet()) && desensitizationList.containsKey(fields.get(i).getDataeaseName())) {
                    d.put(fields.get(i).getDataeaseName(), desensitizationValue(desensitizationList.get(fields.get(i).getDataeaseName()), String.valueOf(ele[i])));
                    continue;
                }
                if (i == ele.length) break;
                ChartViewFieldDTO chartViewFieldDTO = fields.get(i);
                if (chartViewFieldDTO.getDeType() == 0 || chartViewFieldDTO.getDeType() == 1 || chartViewFieldDTO.getDeType() == 5) {
                    d.put(fields.get(i).getDataeaseName(), StringUtils.isEmpty(ele[i]) ? "" : ele[i]);
                } else if (chartViewFieldDTO.getDeType() == 2 || chartViewFieldDTO.getDeType() == 3) {
                    d.put(fields.get(i).getDataeaseName(), StringUtils.isEmpty(ele[i]) ? null : new BigDecimal(ele[i]).setScale(8, RoundingMode.HALF_UP));
                }
            }
            tableRow.add(d);
        });
        map.put("fields", fields);
        map.put("tableRow", tableRow);
        return map;
    }

    public static Map<String, Object> transGroupStackDataAntV(List<ChartViewFieldDTO> xAxisBase, List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> xAxisExt, List<ChartViewFieldDTO> yAxis, List<ChartViewFieldDTO> extStack, List<String[]> data, ChartViewWithBLOBs view, boolean isDrill) {
        // 堆叠柱状图
        if (CollectionUtils.isEmpty(xAxisExt)) {
            return transStackChartDataAntV(xAxis, yAxis, view, data, extStack, isDrill);
            //  分组柱状图
        } else if (CollectionUtils.isNotEmpty(xAxisExt) && CollectionUtils.isEmpty(extStack)) {
            return transBaseGroupDataAntV(xAxisBase, xAxis, xAxisExt, yAxis, view, data, isDrill);
            // 分组堆叠柱状图
        } else {
            Map<String, Object> map = new HashMap<>();

            List<AxisChartDataAntVDTO> dataList = new ArrayList<>();
            for (int i1 = 0; i1 < data.size(); i1++) {
                String[] row = data.get(i1);

                StringBuilder xField = new StringBuilder();
                if (isDrill) {
                    xField.append(row[xAxis.size() - 1]);
                } else {
                    for (int i = 0; i < xAxisBase.size(); i++) {
                        if (i == xAxisBase.size() - 1) {
                            xField.append(row[i]);
                        } else {
                            xField.append(row[i]).append("\n");
                        }
                    }
                }

                StringBuilder groupField = new StringBuilder();
                for (int i = xAxisBase.size(); i < xAxisBase.size() + xAxisExt.size(); i++) {
                    if (i == xAxisBase.size() + xAxisExt.size() - 1) {
                        groupField.append(row[i]);
                    } else {
                        groupField.append(row[i]).append("\n");
                    }
                }

                StringBuilder stackField = new StringBuilder();
                for (int i = xAxis.size(); i < xAxis.size() + extStack.size(); i++) {
                    if (i == xAxis.size() + extStack.size() - 1) {
                        stackField.append(row[i]);
                    } else {
                        stackField.append(row[i]).append("\n");
                    }
                }

                AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
                axisChartDataDTO.setField(xField.toString());
                axisChartDataDTO.setName(xField.toString());

                List<ChartDimensionDTO> dimensionList = new ArrayList<>();
                List<ChartQuotaDTO> quotaList = new ArrayList<>();

                for (int j = 0; j < xAxis.size(); j++) {
                    ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                    chartDimensionDTO.setId(xAxis.get(j).getId());
                    chartDimensionDTO.setValue(row[j]);
                    dimensionList.add(chartDimensionDTO);
                }
                ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                chartDimensionDTO.setId(extStack.get(0).getId());
                chartDimensionDTO.setValue(row[xAxis.size()]);
                dimensionList.add(chartDimensionDTO);
                axisChartDataDTO.setDimensionList(dimensionList);

                if (CollectionUtils.isNotEmpty(yAxis)) {
                    int valueIndex = xAxis.size() + extStack.size();
                    ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                    chartQuotaDTO.setId(yAxis.get(0).getId());
                    quotaList.add(chartQuotaDTO);
                    axisChartDataDTO.setQuotaList(quotaList);
                    try {
                        axisChartDataDTO.setValue(StringUtils.isEmpty(row[valueIndex]) ? null : new BigDecimal(row[valueIndex]));
                    } catch (Exception e) {
                        axisChartDataDTO.setValue(new BigDecimal(0));
                    }
                } else {
                    axisChartDataDTO.setQuotaList(quotaList);
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                axisChartDataDTO.setGroup(groupField.toString());
                axisChartDataDTO.setCategory(stackField.toString());
                dataList.add(axisChartDataDTO);
            }
            map.put("data", dataList);
            return map;
        }
    }

    public static Map<String, Object> transBidirectionalBarData(List<ChartViewFieldDTO> xAxis, List<ChartViewFieldDTO> yAxis, ChartViewDTO view, List<String[]> data, boolean isDrill) {
        Map<String, Object> map = new HashMap<>();

        List<AxisChartDataAntVDTO> dataList = new ArrayList<>();
        for (int i1 = 0; i1 < data.size(); i1++) {
            String[] row = data.get(i1);

            StringBuilder a = new StringBuilder();
            if (isDrill) {
                a.append(row[xAxis.size() - 1]);
            } else {
                for (int i = 0; i < xAxis.size(); i++) {
                    if (i == xAxis.size() - 1) {
                        a.append(row[i]);
                    } else {
                        a.append(row[i]).append("\n");
                    }
                }
            }

            AxisChartDataAntVDTO axisChartDataDTO = new AxisChartDataAntVDTO();
            axisChartDataDTO.setField(a.toString());
            axisChartDataDTO.setName(a.toString());

            List<ChartDimensionDTO> dimensionList = new ArrayList<>();

            for (int j = 0; j < xAxis.size(); j++) {
                ChartDimensionDTO chartDimensionDTO = new ChartDimensionDTO();
                chartDimensionDTO.setId(xAxis.get(j).getId());
                chartDimensionDTO.setValue(row[j]);
                dimensionList.add(chartDimensionDTO);
            }
            axisChartDataDTO.setDimensionList(dimensionList);
            for (int i = xAxis.size(); i < xAxis.size() + yAxis.size(); i++) {
                List<ChartQuotaDTO> quotaList = new ArrayList<>();
                int j = i - xAxis.size();
                ChartQuotaDTO chartQuotaDTO = new ChartQuotaDTO();
                chartQuotaDTO.setId(yAxis.get(j).getId());
                quotaList.add(chartQuotaDTO);
                axisChartDataDTO.setQuotaList(quotaList);
            }
            if (yAxis.size() == 2){
                try {
                    axisChartDataDTO.setValue(StringUtils.isEmpty(row[xAxis.size()]) ? null : new BigDecimal(row[xAxis.size()]));
                } catch (Exception e) {
                    axisChartDataDTO.setValue(new BigDecimal(0));
                }
                try {
                    axisChartDataDTO.setExtValue(StringUtils.isEmpty(row[xAxis.size() + yAxis.size() - 1]) ? null : new BigDecimal(row[xAxis.size() + yAxis.size() - 1]));
                } catch (Exception e) {
                    axisChartDataDTO.setExtValue(new BigDecimal(0));
                }
                dataList.add(axisChartDataDTO);
            }
        }
        map.put("data", dataList);
        return map;
    }
}
