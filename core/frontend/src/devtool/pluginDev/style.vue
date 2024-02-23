<template>
  <component
    :is="mainIndex"
    :obj="obj"
    @plugin-call-back="pluginCallBack"
  />
</template>

<script>
import { mainStyle } from '@/devtool/view/mainTool'
import bus from '@/utils/bus'
export default {
  name: 'MainToolStyle',
  components: {
    mainStyle

  },
  props: {
    obj: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      mainIndex: 'mainStyle'
    }
  },
  methods: {
    pluginCallBack(param) {
      const { eventName, eventParam } = param
      bus.$emit(eventName, eventParam)
    },
  }
}
</script>
