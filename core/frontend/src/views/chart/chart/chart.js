export const DEFAULT_TAB_COLOR_CASE_DARK = {
  headFontColor: '#FFFFFF',
  headFontActiveColor: '#FFFFFF',
  headBorderColor: '#131E42',
  headBorderActiveColor: '#131E42',
  headPosition: 'left'
}

export const DEFAULT_TAB_COLOR_CASE_LIGHT = {
  headFontColor: '#OOOOOO',
  headFontActiveColor: '#OOOOOO',
  headBorderColor: '#OOOOOO',
  headBorderActiveColor: '#OOOOOO',
  headPosition: 'left'
}

export const DEFAULT_COLOR_CASE = {
  value: 'default',
  colors: ['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc'],
  alpha: 100,
  tableHeaderBgColor: '#6D9A49',
  tableItemBgColor: '#FFFFFF',
  tableHeaderFontColor: '#000000',
  tableFontColor: '#000000',
  tableStripe: true,
  dimensionColor: '#000000',
  quotaColor: '#5470c6',
  tableBorderColor: '#E6E7E4',
  seriesColors: [], // 格式：{"name":"s1","color":"","isCustom":false}
  areaBorderColor: '#303133',
  gradient: false,
  areaBaseColor: '#FFFFFF',
  tableScrollBarColor: 'rgba(0, 0, 0, 0.15)',
  tableScrollBarHoverColor: 'rgba(0, 0, 0, 0.4)',
  mapStyle: 'normal',
  mapLineGradient: false,
  mapLineSourceColor: '#146C94',
  mapLineTargetColor: '#576CBC',
}

export const DEFAULT_COLOR_CASE_DARK = {
  value: 'default',
  colors: ['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc'],
  alpha: 100,
  tableHeaderBgColor: '#5470c6',
  tableItemBgColor: '#131E42',
  tableFontColor: '#ffffff',
  tableStripe: true,
  dimensionColor: '#ffffff',
  quotaColor: '#5470c6',
  tableBorderColor: '#CCCCCC',
  seriesColors: [], // 格式：{"name":"s1","color":"","isCustom":false}
  areaBorderColor: '#EBEEF5',
  areaBaseColor: '5470C6',
  tableScrollBarColor: 'rgba(255, 255, 255, 0.5)',
  tableScrollBarHoverColor: 'rgba(255, 255, 255, 0.8)',
  mapStyle: 'darkblue',
  mapLineGradient: false,
  mapLineSourceColor: '#2F58CD',
  mapLineTargetColor: '#3795BD',
}
export const DEFAULT_SIZE = {
  barDefault: true,
  barWidth: 40,
  barGap: 0.4,
  lineWidth: 2,
  lineType: 'solid',
  lineSymbol: 'circle',
  lineSymbolSize: 4,
  lineSmooth: true,
  lineArea: false,
  pieInnerRadius: 0,
  pieOuterRadius: 80,
  pieRoseType: 'radius',
  pieRoseRadius: 5,
  funnelWidth: 80,
  radarShape: 'polygon',
  radarSize: 80,
  tableTitleFontSize: 12,
  tableItemFontSize: 12,
  tableTitleHeight: 36,
  tableItemHeight: 36,
  tablePageSize: '20',
  tableColumnMode: 'custom',
  tableColumnWidth: 100,
  tableHeaderAlign: 'left',
  tableItemAlign: 'right',
  tableAutoBreakLine: false,
  tableRowTooltip: {
    show: false
  },
  tableColTooltip: {
    show: false
  },
  gaugeMinType: 'fix', // fix or dynamic
  gaugeMinField: {
    id: '',
    summary: ''
  },
  gaugeMin: 0,
  gaugeMaxType: 'fix', // fix or dynamic
  gaugeMaxField: {
    id: '',
    summary: ''
  },
  gaugeMax: 100,
  gaugeStartAngle: 225,
  gaugeEndAngle: -45,
  gaugeAxisLine: true,
  gaugeTickCount: 5,
  dimensionFontSize: 18,
  quotaFontSize: 18,
  spaceSplit: 10,
  dimensionShow: true,
  quotaShow: true,
  quotaFontFamily: 'Microsoft YaHei',
  quotaFontIsBolder: false,
  quotaFontIsItalic: false,
  quotaLetterSpace: '0',
  quotaFontShadow: false,
  dimensionFontFamily: 'Microsoft YaHei',
  dimensionFontIsBolder: false,
  dimensionFontIsItalic: false,
  dimensionLetterSpace: '0',
  dimensionFontShadow: false,
  scatterSymbol: 'circle',
  scatterSymbolSize: 20,
  treemapWidth: 80,
  treemapHeight: 80,
  liquidMax: 100,
  liquidMaxType: 'fix', // fix or dynamic
  liquidMaxField: {
    id: '',
    summary: ''
  },
  liquidSize: 80,
  liquidOutlineBorder: 4,
  liquidOutlineDistance: 8,
  liquidWaveLength: 128,
  liquidWaveCount: 3,
  liquidShape: 'circle',
  tablePageMode: 'page',
  symbolOpacity: 0.7,
  symbolStrokeWidth: 2,
  showIndex: false,
  indexLabel: '序号',
  hPosition: 'center',
  vPosition: 'center',
  mapPitch: 0,
  mapLineType: 'arc',
  mapLineWidth: 1,
  mapLineAnimate: true,
  mapLineAnimateDuration: 3,
  mapLineAnimateInterval: 1,
  mapLineAnimateTrailLength: 1,
  wordSizeRange: [8, 32],
  wordSpacing: 6
}
export const DEFAULT_SUSPENSION = {
  show: true
}

export const DEFAULT_MARK = {
  fieldId: '',
  conditions: []
}
export const DEFAULT_LABEL = {
  show: false,
  position: 'top',
  color: '#909399',
  fontSize: '10',
  formatter: '{c}',
  gaugeFormatter: '{value}',
  labelLine: {
    show: true
  },
  gaugeLabelFormatter: {
    type: 'value', // auto,value,percent
    unit: 1, // 换算单位
    suffix: '', // 单位后缀
    decimalCount: 2, // 小数位数
    thousandSeparator: true// 千分符
  },
  reserveDecimalCount: 2,
  labelContent: ['dimension', 'proportion']
}
export const DEFAULT_TOOLTIP = {
  show: true,
  trigger: 'item',
  confine: true,
  textStyle: {
    fontSize: '10',
    color: '#909399'
  },
  formatter: '',
  backgroundColor: '#ffffff'
}
export const DEFAULT_TOTAL = {
  row: {
    showGrandTotals: true,
    showSubTotals: true,
    reverseLayout: false,
    reverseSubLayout: false,
    label: '总计',
    subLabel: '小计',
    subTotalsDimensions: [],
    calcTotals: {
      aggregation: 'SUM'
    },
    calcSubTotals: {
      aggregation: 'SUM'
    },
    totalSort: 'none', // asc,desc
    totalSortField: ''
  },
  col: {
    showGrandTotals: true,
    showSubTotals: true,
    reverseLayout: false,
    reverseSubLayout: false,
    label: '总计',
    subLabel: '小计',
    subTotalsDimensions: [],
    calcTotals: {
      aggregation: 'SUM'
    },
    calcSubTotals: {
      aggregation: 'SUM'
    },
    totalSort: 'none', // asc,desc
    totalSortField: ''
  }
}
export const DEFAULT_TITLE_STYLE = {
  show: true,
  fontSize: '18',
  color: '#000000',
  hPosition: 'left',
  vPosition: 'top',
  isItalic: false,
  isBolder: true,
  remarkShow: false,
  remark: '',
  remarkBackgroundColor: '#ffffffff',
  fontFamily: 'Microsoft YaHei',
  letterSpace: '0',
  fontShadow: false
}

export const DEFAULT_TITLE_STYLE_DARK = {
  show: true,
  fontSize: '18',
  color: '#FFFFFF',
  hPosition: 'left',
  vPosition: 'top',
  isItalic: false,
  isBolder: true,
  remarkShow: false,
  remark: '',
  remarkBackgroundColor: '#5A5C62',
  fontFamily: 'Microsoft YaHei',
  letterSpace: '0',
  fontShadow: false
}

export const DEFAULT_LEGEND_STYLE = {
  show: true,
  hPosition: 'center',
  vPosition: 'bottom',
  orient: 'horizontal',
  icon: 'circle',
  textStyle: {
    color: '#333333',
    fontSize: '12'
  }
}

export const DEFAULT_MARGIN_STYLE = {
  marginModel: 'auto',
  marginTop: 40,
  marginBottom: 44,
  marginLeft: 15,
  marginRight: 10
}

export const DEFAULT_XAXIS_STYLE = {
  show: true,
  position: 'bottom',
  name: '',
  nameTextStyle: {
    color: '#333333',
    fontSize: 12
  },
  axisLabel: {
    show: true,
    color: '#333333',
    fontSize: '12',
    rotate: 0,
    formatter: '{value}'
  },
  axisLine: {
    show: true,
    lineStyle: {
      color: '#cccccc',
      width: 1,
      style: 'solid'
    }
  },
  splitLine: {
    show: false,
    lineStyle: {
      color: '#cccccc',
      width: 1,
      style: 'solid'
    }
  },
  axisValue: {
    auto: true,
    min: null,
    max: null,
    split: null,
    splitCount: null
  },
  axisLabelFormatter: {
    type: 'auto', // auto,value,percent
    unit: 1, // 换算单位
    suffix: '', // 单位后缀
    decimalCount: 2, // 小数位数
    thousandSeparator: true// 千分符
  }
}
export const DEFAULT_YAXIS_STYLE = {
  show: true,
  position: 'left',
  name: '',
  nameTextStyle: {
    color: '#333333',
    fontSize: 12
  },
  axisLabel: {
    show: true,
    color: '#333333',
    fontSize: '12',
    rotate: 0,
    formatter: '{value}'
  },
  axisLine: {
    show: false,
    lineStyle: {
      color: '#cccccc',
      width: 1,
      style: 'solid'
    }
  },
  splitLine: {
    show: true,
    lineStyle: {
      color: '#cccccc',
      width: 1,
      style: 'solid'
    }
  },
  axisValue: {
    auto: true,
    min: null,
    max: null,
    split: null,
    splitCount: null
  },
  axisLabelFormatter: {
    type: 'auto', // auto,value,percent
    unit: 1, // 换算单位
    suffix: '', // 单位后缀
    decimalCount: 2, // 小数位数
    thousandSeparator: true// 千分符
  }
}
export const DEFAULT_YAXIS_EXT_STYLE = {
  show: true,
  position: 'right',
  name: '',
  nameTextStyle: {
    color: '#333333',
    fontSize: 12
  },
  axisLabel: {
    show: true,
    color: '#333333',
    fontSize: '12',
    rotate: 0,
    formatter: '{value}'
  },
  axisLine: {
    show: false,
    lineStyle: {
      color: '#cccccc',
      width: 1,
      style: 'solid'
    }
  },
  splitLine: {
    show: true,
    lineStyle: {
      color: '#cccccc',
      width: 1,
      style: 'solid'
    }
  },
  axisValue: {
    auto: true,
    min: null,
    max: null,
    split: null,
    splitCount: null
  },
  axisLabelFormatter: {
    type: 'auto', // auto,value,percent
    unit: 1, // 换算单位
    suffix: '', // 单位后缀
    decimalCount: 2, // 小数位数
    thousandSeparator: true// 千分符
  }
}
export const DEFAULT_BACKGROUND_COLOR = {
  color: '#ffffff',
  alpha: 0,
  borderRadius: 0
}
export const DEFAULT_SPLIT = {
  name: {
    show: true,
    color: '#999999',
    fontSize: '12'
  },
  splitNumber: 5,
  axisLine: {
    show: true,
    lineStyle: {
      color: '#999999',
      width: 1,
      type: 'solid'
    }
  },
  axisTick: {
    show: false,
    length: 5,
    lineStyle: {
      color: '#999999',
      width: 1,
      type: 'solid'
    }
  },
  axisLabel: {
    show: false,
    rotate: 0,
    margin: 8,
    color: '#999999',
    fontSize: '12',
    formatter: '{value}'
  },
  splitLine: {
    show: true,
    lineStyle: {
      color: '#999999',
      width: 1,
      type: 'solid'
    }
  },
  splitArea: {
    show: true
  }
}
export const DEFAULT_FUNCTION_CFG = {
  sliderShow: false,
  sliderRange: [0, 10],
  sliderBg: '#FFFFFF',
  sliderFillBg: '#BCD6F1',
  sliderTextClolor: '#999999',
  emptyDataStrategy: 'breakLine',
  emptyDataFieldCtrl: []
}
export const DEFAULT_THRESHOLD = {
  gaugeThreshold: '',
  labelThreshold: [],
  tableThreshold: [],
  textLabelThreshold: []
}
export const DEFAULT_SCROLL = {
  open: false,
  row: 1,
  interval: 2000,
  step: 50
}
// chart config
export const BASE_BAR = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect',
    data: []
  },
  xAxis: {
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [],
  dataZoom: [
    {
      type: 'slider',
      show: false,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'slider',
      show: false,
      yAxisIndex: [0],
      left: '93%',
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      yAxisIndex: [0],
      start: 0,
      end: 100
    }
  ]
}
export const HORIZONTAL_BAR = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect',
    data: []
  },
  xAxis: {
    type: 'value'
  },
  yAxis: {
    data: []
  },
  series: [],
  dataZoom: [
    {
      type: 'slider',
      show: false,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'slider',
      show: false,
      yAxisIndex: [0],
      left: '93%',
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      yAxisIndex: [0],
      start: 0,
      end: 100
    }
  ]
}

export const BASE_LINE = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect',
    data: []
  },
  xAxis: {
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [],
  dataZoom: [
    {
      type: 'slider',
      show: false,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'slider',
      show: false,
      yAxisIndex: [0],
      left: '93%',
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      yAxisIndex: [0],
      start: 0,
      end: 100
    }
  ]
}

export const BASE_PIE = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect'
  },
  series: [
    {
      name: '',
      type: 'pie',
      radius: ['0%', '60%'],
      avoidLabelOverlap: false,
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      },
      data: []
    }
  ]
}

export const BASE_FUNNEL = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect'
  },
  series: [
    {
      name: '',
      type: 'funnel',
      left: 'center',
      top: 60,
      bottom: 60,
      width: '80%',
      min: 0,
      max: 100,
      minSize: '0%',
      maxSize: '100%',
      sort: 'descending',
      gap: 1,
      labelLine: {
        length: 10,
        lineStyle: {
          width: 1,
          type: 'solid'
        }
      },
      itemStyle: {
        borderColor: '#fff',
        borderWidth: 1
      },
      emphasis: {
        label: {
          fontSize: 20
        }
      },
      data: []
    }
  ]
}

export const BASE_RADAR = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect',
    data: []
  },
  radar: {
    shape: 'polygon',
    name: {
      show: true,
      color: '#999999',
      fontSize: '12'
    },
    splitNumber: 5,
    axisLine: {
      show: true,
      lineStyle: {
        color: '#999999',
        width: 1,
        type: 'solid'
      }
    },
    axisTick: {
      show: false,
      length: 5,
      lineStyle: {
        color: '#999999',
        width: 1,
        type: 'solid'
      }
    },
    axisLabel: {
      show: false,
      rotate: 0,
      margin: 8,
      color: '#999999',
      fontSize: '12',
      formatter: '{value}'
    },
    splitLine: {
      show: true,
      lineStyle: {
        color: '#999999',
        width: 1,
        type: 'solid'
      }
    },
    splitArea: {
      show: true
    },
    indicator: []
  },
  series: []
}

export const BASE_GAUGE = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect'
  },
  series: [
    {
      name: '',
      type: 'gauge',
      startAngle: 225,
      endAngle: -45,
      min: 0,
      max: 100,
      progress: {
        show: true
      },
      detail: {
        show: true,
        valueAnimation: true,
        formatter: '{value}'
      },
      data: []
    }
  ]
}

export const BASE_CHART_STRING = {
  stylePriority: 'view',
  xaxis: '[]',
  yaxis: '[]',
  show: true,
  type: 'panel',
  title: '',
  customAttr: JSON.stringify({
    color: DEFAULT_COLOR_CASE,
    tableColor: DEFAULT_COLOR_CASE,
    size: DEFAULT_SIZE,
    label: DEFAULT_LABEL,
    tooltip: DEFAULT_TOOLTIP
  }),
  customStyle: JSON.stringify({
    text: DEFAULT_TITLE_STYLE,
    legend: DEFAULT_LEGEND_STYLE,
    xAxis: DEFAULT_XAXIS_STYLE,
    yAxis: DEFAULT_YAXIS_STYLE,
    yAxisExt: DEFAULT_YAXIS_EXT_STYLE
  }),
  customFilter: '[]'
}

export const BASE_CHART = {
  xaxis: [],
  yaxis: [],
  show: true,
  type: 'panel',
  title: '',
  customAttr: {
    color: DEFAULT_COLOR_CASE,
    tableColor: DEFAULT_COLOR_CASE,
    size: DEFAULT_SIZE,
    label: DEFAULT_LABEL,
    tooltip: DEFAULT_TOOLTIP
  },
  customStyle: {
    text: DEFAULT_TITLE_STYLE,
    legend: DEFAULT_LEGEND_STYLE,
    xAxis: DEFAULT_XAXIS_STYLE,
    yAxis: DEFAULT_YAXIS_STYLE,
    yAxisExt: DEFAULT_YAXIS_EXT_STYLE
  },
  customFilter: []
}

export const BASE_MAP = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },

  tooltip: {},
  visualMap: {
    min: 50,
    max: 52,
    text: ['High', 'Low'],
    realtime: false,
    calculable: true,
    inRange: {
      color: ['lightskyblue', 'yellow', 'orangered']
    },
    seriesIndex: 0,
    textStyle: {},
    right: 0
  },
  geo: {
    map: 'MAP',
    roam: false,
    nameMap: {},
    itemStyle: {
      normal: {},
      emphasis: {
        label: {
          show: false
        }
      }
    }
  },
  series: [
    {
      name: '',
      type: 'map',
      geoIndex: 0,
      roam: true,
      data: [],
      itemStyle: {
        normal: {},
        emphasis: {
          label: {
            show: false
          }
        }
      }
    }
  ]
}

export const BASE_SCATTER = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect',
    data: []
  },
  xAxis: {
    data: [],
    boundaryGap: false
  },
  yAxis: {
    type: 'value'
  },
  series: [],
  dataZoom: [
    {
      type: 'slider',
      show: false,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'slider',
      show: false,
      yAxisIndex: [0],
      left: '93%',
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      yAxisIndex: [0],
      start: 0,
      end: 100
    }
  ]
}

export const BASE_TREEMAP = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect'
  },
  series: [
    {
      // name: '',
      type: 'treemap',
      roam: true,
      itemStyle: {
        gapWidth: 2
      },
      breadcrumb: {
        show: false
      },
      data: []
    }
  ]
}

export const BASE_MIX = {
  title: {
    text: '',
    textStyle: {
      fontWeight: 'normal'
    }
  },
  grid: {
    containLabel: true
  },
  tooltip: {},
  legend: {
    show: true,
    type: 'scroll',
    itemWidth: 10,
    itemHeight: 10,
    icon: 'rect',
    data: []
  },
  xAxis: {
    data: []
  },
  yAxis: [
    {
      type: 'value'
    },
    {
      type: 'value'
    }
  ],
  series: [],
  dataZoom: [
    {
      type: 'slider',
      show: false,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'slider',
      show: false,
      yAxisIndex: [0],
      left: '93%',
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      xAxisIndex: [0],
      start: 0,
      end: 100
    },
    {
      type: 'inside',
      disabled: true,
      yAxisIndex: [0],
      start: 0,
      end: 100
    }
  ]
}

export const COLOR_PANEL = [
  '#ff4500',
  '#ff8c00',
  '#ffd700',
  '#90ee90',
  '#00ced1',
  '#1e90ff',
  '#c71585',
  '#999999',
  '#000000',
  '#FFFFFF'
]

export const BASE_ECHARTS_SELECT = {
  itemStyle: {
    shadowBlur: 2
  }
}

export const CHART_FONT_FAMILY = [
  { name: '微软雅黑', value: 'Microsoft YaHei' },
  { name: '宋体', value: 'SimSun' },
  { name: '黑体', value: 'SimHei' },
  { name: '楷体', value: 'KaiTi' }
]

export const CHART_CONT_FAMILY_MAP = {
  'Microsoft YaHei': 'Microsoft YaHei',
  'SimSun': 'SimSun, "Songti SC", STSong',
  'SimHei': 'SimHei, Helvetica',
  'KaiTi': 'KaiTi, "Kaiti SC", STKaiti'
}

export const CHART_FONT_LETTER_SPACE = [
  { name: '0px', value: '0' },
  { name: '1px', value: '1' },
  { name: '2px', value: '2' },
  { name: '3px', value: '3' },
  { name: '4px', value: '4' },
  { name: '5px', value: '5' },
  { name: '6px', value: '6' },
  { name: '7px', value: '7' },
  { name: '8px', value: '8' },
  { name: '9px', value: '9' },
  { name: '10px', value: '10' }
]

export const NOT_SUPPORT_PAGE_DATASET = ['kylin', 'sqlServer', 'es', 'presto', 'ds_doris', 'StarRocks', 'impala']

export const SUPPORT_Y_M = ['y', 'y_M', 'y_M_d']

export const DEFAULT_MAP = {
  mapPitch: 0,
  lineType: 'line',
  lineWidth: 1,
  lineAnimate: true,
  lineAnimateDuration: 4,
  lineAnimateInterval: 0.5,
  lineAnimateTrailLength: 0.1
}
