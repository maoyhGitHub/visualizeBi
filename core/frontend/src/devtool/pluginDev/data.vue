<template>
  <component
    :is="mainIndex"
    :obj="obj"
    :move-id="moveId"
    @plugin-call-back="pluginCallBack"
  />
</template>

<script>
import { mainData } from '@/devtool/view/mainTool'
import bus from '@/utils/bus'

export default {
  components: { mainData },
  props: {
    obj: {
      type: Object,
      default: () => {}
    },
    moveId: {
      type: String
    }
  },
  data() {
    return {
      mainIndex: 'mainData'
    }
  },
  methods: {
    pluginCallBack(param) {
      const { eventName, eventParam } = param
      bus.$emit(eventName, eventParam)
    },
    showRename(item) {
      this.$emit('plugin-call-back', {
        eventName: 'show-rename',
        eventParam: item
      })
    }
  }
}
</script>
