<template>
  <de-layout-content
    v-loading="$store.getters.loadingMap[$store.getters.currentPath]"
  >
    <div class="sys-setting">
      <el-tabs
        v-model="activeName"
        class="de-tabs"
      >
        <el-tab-pane
          lazy
          label="视图组件"
          name="views"
        />

        <el-tab-pane
          lazy
          label="过滤组件"
          name="filter"
        />

        <el-tab-pane
          lazy
          label="其他组件"
          name="others"
        />
      </el-tabs>
      <div
        class="tabs-container"

      >
        <div class="min-w600">
          <Group
                       v-if="activeName === 'views'"
                       :is-plugin-loaded="isPluginLoaded"/>
          <FilterGroup v-if="activeName === 'filter'" />
          <OthersGroup v-if="activeName === 'others'" />
        </div>
      </div>
    </div>
  </de-layout-content>
</template>
<script>
import Group from './Group'
import FilterGroup from './filter'
import OthersGroup from './others'
import DeLayoutContent from '@/components/business/DeLayoutContent'
import { pluginLoaded } from '@/api/user'
import { engineMode } from '@/api/system/engine'
export default {
  components: {
    Group,
    FilterGroup,
    OthersGroup,
    DeLayoutContent
  },
  data() {
    return {
      activeName: 'views',
      isPluginLoaded: false,
      engineMode: 'local'
    }
  },
  beforeCreate() {
    pluginLoaded().then((res) => {
      this.isPluginLoaded = res.success && res.data
    })
    engineMode().then((res) => {
      this.engineMode = res.data
    })
  }
}
</script>
<style lang="scss" scoped>
.sys-setting {
  height: 100%;
  background-color: var(--MainBG, #f5f6f7);

  .tabs-container {
    height: calc(100% - 48px);
    background: var(--ContentBG, #ffffff);
    overflow-x: auto;

    .min-w600 {
      min-width: 600px;
      height: 100%;
      & > :nth-child(1) {
        box-sizing: border-box;
        padding-bottom: 20px;
      }
    }
  }

  .is-center {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 0 20px 20px 20px;
  }

  .pad-center {
    padding: 24px;
  }
}
</style>
