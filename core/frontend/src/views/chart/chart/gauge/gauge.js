import { componentStyle } from '../common/common'
import { hexColorToRGBA } from '@/views/chart/chart/util'
import { BASE_ECHARTS_SELECT, DEFAULT_THRESHOLD } from '@/views/chart/chart/chart'
import { getScaleValue } from '@/components/canvas/utils/style'

export function baseGaugeOption(chart_option, chart, scale = 1) {
  // 处理shape attr
  let customAttr = {}
  if (chart.customAttr) {
    customAttr = JSON.parse(chart.customAttr)
    if (customAttr.color) {
      chart_option.color = customAttr.color.colors
    }
    // tooltip
    if (customAttr.tooltip) {
      const tooltip = JSON.parse(JSON.stringify(customAttr.tooltip))
      const reg = new RegExp('\n', 'g')
      tooltip.formatter = tooltip.formatter.replace(reg, '<br/>')
      chart_option.tooltip = tooltip
    }
  }
  // 处理data
  if (chart.data) {
    chart_option.title.text = chart.title
    if (chart.data.series.length > 0) {
      chart_option.series[0].name = chart.data.series[0].name
      // size
      if (customAttr.size) {
        chart_option.series[0].min = customAttr.size.gaugeMin
        chart_option.series[0].max = customAttr.size.gaugeMax
        chart_option.series[0].startAngle = customAttr.size.gaugeStartAngle
        chart_option.series[0].endAngle = customAttr.size.gaugeEndAngle
      }
      // detail
      if (customAttr.label) {
        const label = JSON.parse(JSON.stringify(customAttr.label))
        chart_option.series[0].detail.show = label.show
        chart_option.series[0].detail.fontSize = label.fontSize
        chart_option.series[0].detail.color = label.color
        chart_option.series[0].detail.formatter = label.gaugeFormatter
      }
      chart_option.series[0].type = 'gauge'
      // color
      chart_option.series[0].itemStyle = {
        color: hexColorToRGBA(customAttr.color.colors[0], customAttr.color.alpha)
      }
      chart_option.series[0].selectedMode = true
      chart_option.series[0].select = BASE_ECHARTS_SELECT
      // data只取第一个
      const y = {
        // name: chart.data.x[0],
        name: chart.data.series[0].name,
        value: chart.data.series[0].data[0]
      }
      chart_option.series[0].data.push(y)

      chart_option.series[0].axisTick = {
        splitNumber: getScaleValue(5, scale), // 刻度间隔数
        length: getScaleValue(10, scale), // 子刻度线长度
        lineStyle: {
          width: getScaleValue(2, scale) // 子刻度线宽度
        }
      }
      chart_option.series[0].splitLine = {
        length: getScaleValue(18, scale), // 刻度线长度
        lineStyle: {
          width: getScaleValue(2, scale) // 刻度线宽度
        }
      }
      let showAxisLine = customAttr.size.gaugeAxisLine
      if (showAxisLine === null || showAxisLine === undefined) {
        showAxisLine = true
      }
      chart_option.series[0].axisLabel = {
        show: showAxisLine,
        distance: getScaleValue(20, scale), // 刻度值文字里刻度线距离
        fontSize: getScaleValue(20, scale)// 刻度值字体大小
      }

      // threshold
      if (chart.senior) {
        const range = []
        let index = 0
        let flag = false
        const senior = JSON.parse(chart.senior)
        const threshold = JSON.parse(JSON.stringify(senior.threshold ? senior.threshold : DEFAULT_THRESHOLD))
        if (threshold.gaugeThreshold && threshold.gaugeThreshold !== '') {
          const arr = threshold.gaugeThreshold.split(',')
          const per = parseFloat(chart.data.series[0].data[0]) / parseFloat(chart_option.series[0].max)
          for (let i = 0; i < arr.length; i++) {
            const ele = arr[i]
            const p = parseFloat(ele) / 100
            range.push([p, hexColorToRGBA(customAttr.color.colors[i % customAttr.color.colors.length], customAttr.color.alpha)])
            if (!flag && per <= p) {
              flag = true
              index = i
            }
          }
          if (!flag) {
            index = arr.length
          }

          range.push([1, hexColorToRGBA(customAttr.color.colors[arr.length % customAttr.color.colors.length], customAttr.color.alpha)])
          chart_option.series[0].axisLine = {
            lineStyle: {
              color: range
            }
          }

          chart_option.series[0].itemStyle = {
            color: hexColorToRGBA(customAttr.color.colors[index], customAttr.color.alpha)
          }
          chart_option.series[0].progress = {
            show: false
          }
          chart_option.series[0].axisTick = {
            splitNumber: getScaleValue(5, scale), // 刻度间隔数
            length: getScaleValue(10, scale), // 子刻度线长度
            lineStyle: {
              color: 'auto',
              width: getScaleValue(2, scale) // 子刻度线宽度
            }
          }
          chart_option.series[0].splitLine = {
            length: getScaleValue(18, scale), // 刻度线长度
            lineStyle: {
              color: 'auto',
              width: getScaleValue(2, scale) // 刻度线宽度
            }
          }
          chart_option.series[0].axisLabel = {
            color: 'auto',
            distance: getScaleValue(20, scale), // 刻度值文字里刻度线距离
            fontSize: getScaleValue(20, scale)// 刻度值字体大小
          }
        }
      }
    }
  }
  componentStyle(chart_option, chart)
  return chart_option
}
