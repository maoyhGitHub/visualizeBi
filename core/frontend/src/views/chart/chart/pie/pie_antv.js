import {
  getLabel,
  getLegend,
  getPadding,
  getTheme,
  getTooltip
} from '@/views/chart/chart/common/common_antv'

import { Pie, Rose } from '@antv/g2plot'
import { antVCustomColor } from '@/views/chart/chart/util'

export function basePieOptionAntV(plot, container, chart, action) {
  // theme
  const theme = getTheme(chart)
  // attr
  const label = getLabel(chart)
  const tooltip = getTooltip(chart)
  // style
  const legend = getLegend(chart)
  // data
  const data = chart.data.data
  // options
  const options = {
    theme: theme,
    data: data,
    angleField: 'value',
    colorField: 'field',
    appendPadding: getPadding(chart),
    label: label,
    tooltip: tooltip,
    legend: legend,
    pieStyle: {
      lineWidth: 0
    },
    statistic: {
      title: false,
      content: {
        style: {
          whiteSpace: 'pre-wrap',
          overflow: 'hidden',
          textOverflow: 'ellipsis'
        },
        content: ''
      }
    },
    interactions: [
      {
        type: 'legend-active', cfg: {
          start: [{ trigger: 'legend-item:mouseenter', action: ['element-active:reset'] }],
          end: [{ trigger: 'legend-item:mouseleave', action: ['element-active:reset'] }]
        }
      },
      {
        type: 'legend-filter', cfg: {
          start: [{ trigger: 'legend-item:click', action: ['list-unchecked:toggle', 'data-filter:filter', 'element-active:reset', 'element-highlight:reset'] }]
        }
      },
      {
        type: 'tooltip', cfg: {
          start: [{ trigger: 'interval:mousemove', action: 'tooltip:show' }],
          end: [{ trigger: 'interval:mouseleave', action: 'tooltip:hide' }]
        }
      },
      {
        type: 'active-region', cfg: {
          start: [{ trigger: 'interval:mousemove', action: 'active-region:show' }],
          end: [{ trigger: 'interval:mouseleave', action: 'active-region:hide' }]
        }
      }
    ]
  }
  // size
  let customAttr = {}
  if (chart.customAttr) {
    customAttr = JSON.parse(chart.customAttr)
    if (customAttr.size) {
      const s = JSON.parse(JSON.stringify(customAttr.size))
      options.radius = parseFloat(parseInt(s.pieOuterRadius) / 100)
      options.innerRadius = parseFloat(parseInt(s.pieInnerRadius) / 100)
    }
  }
  // custom color
  options.color = antVCustomColor(chart)

  // 开始渲染
  if (plot) {
    plot.destroy()
  }
  plot = new Pie(container, options)

  plot.off('interval:click')
  plot.on('interval:click', action)

  return plot
}

export function basePieRoseOptionAntV(plot, container, chart, action) {
  // theme
  const theme = getTheme(chart)
  // attr
  const label = getLabel(chart)
  const tooltip = getTooltip(chart)
  // style
  const legend = getLegend(chart)
  // data
  const data = chart.data.data
  // options
  const options = {
    theme: theme,
    data: data,
    xField: 'field',
    yField: 'value',
    seriesField: 'field',
    appendPadding: getPadding(chart),
    label: label,
    tooltip: tooltip,
    legend: legend,
    statistic: {
      title: false,
      content: {
        style: {
          whiteSpace: 'pre-wrap',
          overflow: 'hidden',
          textOverflow: 'ellipsis'
        },
        content: ''
      }
    },
    interactions: [
      {
        type: 'legend-active', cfg: {
          start: [{ trigger: 'legend-item:mouseenter', action: ['element-active:reset'] }],
          end: [{ trigger: 'legend-item:mouseleave', action: ['element-active:reset'] }]
        }
      },
      {
        type: 'legend-filter', cfg: {
          start: [{ trigger: 'legend-item:click', action: ['list-unchecked:toggle', 'data-filter:filter', 'element-active:reset', 'element-highlight:reset'] }]
        }
      },
      {
        type: 'tooltip', cfg: {
          start: [{ trigger: 'interval:mousemove', action: 'tooltip:show' }],
          end: [{ trigger: 'interval:mouseleave', action: 'tooltip:hide' }]
        }
      }
    ]
  }
  // size
  let customAttr = {}
  if (chart.customAttr) {
    customAttr = JSON.parse(chart.customAttr)
    if (customAttr.size) {
      const s = JSON.parse(JSON.stringify(customAttr.size))
      options.radius = parseFloat(parseInt(s.pieOuterRadius) / 100)
      options.innerRadius = parseFloat(parseInt(s.pieInnerRadius) / 100)
    }
  }
  // custom color
  options.color = antVCustomColor(chart)

  // 开始渲染
  if (plot) {
    plot.destroy()
  }
  plot = new Rose(container, options)

  plot.off('interval:click')
  plot.on('interval:click', action)

  return plot
}
