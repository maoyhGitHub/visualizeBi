<template>
  <div>

    <div
      v-show="contentShow"
      class="login-background"
    >

      <div class="login-container">
        <el-row
          v-loading="loading"
          type="flex"
        >
          <el-col
            :span="12"
          >
            <div class="contentleft">
              <h3>数据可视化平台</h3>
            </div>

          </el-col>
          <el-col :span="12">
            <el-form
              v-show="!codeShow"
              ref="loginForm"
              :model="loginForm"
              :rules="loginRules"
              size="default"
              class="formbox"
            >
              <h2>登录</h2>
              <h4>请使用您本人的账号密码登录</h4>
              <div class="login-form">
                <el-form-item v-if="radioTypes.length > 1">
                  <el-radio-group
                    v-if="radioTypes.length > 1"
                    v-model="loginForm.loginType"
                    @change="changeLoginType"
                  >
                    <el-radio
                      :label="0"
                      size="mini"
                    >{{ $t('login.default_login') }}</el-radio>
                    <el-radio
                      v-if="loginTypes.includes(1)"
                      :label="1"
                      size="mini"
                    >LDAP</el-radio>
                    <el-radio
                      v-if="loginTypes.includes(2)"
                      :label="2"
                      size="mini"
                    >OIDC</el-radio>
                    <el-radio
                      v-if="loginTypes.includes(7)"
                      :label="7"
                      size="mini"
                    >Lark</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item prop="username" >
                  <el-input
                    v-model="loginForm.username"
                    placeholder="用户名"
                    autofocus
                    :disabled="loginTypes.includes(2) && loginForm.loginType === 2"
                  />
                </el-form-item>
                <el-form-item prop="password" style="margin-top: 10%">
                  <el-input
                    v-model="loginForm.password"
                    :placeholder="$t('login.password')"
                    show-password
                    maxlength="30"
                    show-word-limit
                    autocomplete="new-password"
                    :disabled="loginTypes.includes(2) && loginForm.loginType === 2"
                    @keypress.enter.native="handleLogin"
                  />
                </el-form-item>
              </div>
              <div class="login-btn" style="margin-top: 20%">
                <el-button
                  type="primary"
                  class="submit"
                  size="default"
                  :disabled="loginTypes.includes(2) && loginForm.loginType === 2"
                  @click.native.prevent="handleLogin"
                >
                  {{ $t('commons.login') }}
                </el-button>
                <div
                  v-if="uiInfo && uiInfo['ui.demo.tips'] && uiInfo['ui.demo.tips'].paramValue"
                  class="demo-tips"
                >
                  {{ uiInfo['ui.demo.tips'].paramValue }}
                </div>
              </div>
              <div class="login-msg">
                {{ msg }}
              </div>
            </el-form>
          </el-col>
        </el-row>

      </div>
      <plugin-com
        v-if="loginTypes.includes(2) && loginForm.loginType === 2"
        ref="SSOComponent"
        component-name="SSOComponent"
      />

      <plugin-com
        v-if="loginTypes.includes(7) && loginForm.loginType === 7"
        ref="LarksuiteQr"
        component-name="LarksuiteQr"
      />

    </div>
    <div
      v-if="showFoot"
      class="dynamic-login-foot"
      v-html="footContent"
    />
  </div>
</template>

<script>

import { encrypt } from '@/utils/rsaEncrypt'
import { ldapStatus, oidcStatus, getPublicKey, pluginLoaded, defaultLoginType, wecomStatus, dingtalkStatus, larkStatus, larksuiteStatus, casStatus, casLoginPage } from '@/api/user'
import { getSysUI } from '@/utils/auth'
import { changeFavicon } from '@/utils/index'
import { initTheme } from '@/utils/ThemeUtil'
import PluginCom from '@/views/system/plugin/PluginCom'
import Cookies from 'js-cookie'
export default {
  name: 'Login',
  components: { PluginCom },
  data() {
    return {
      loginForm: {
        loginType: 0,
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', message: this.$t('commons.input_id') }],
        password: [{ required: true, trigger: 'blur', message: this.$t('commons.input_pwd') }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      uiInfo: null,
      loginImageUrl: null,
      loginLogoUrl: null,
      axiosFinished: false,
      loginTypes: [0],
      isPluginLoaded: false,
      contentShow: false,
      clearLocalStorage: [
        'panel-main-tree',
        'panel-default-tree',
        'chart-tree',
        'dataset-tree'
      ],
      defaultType: 0,
      showFoot: false,
      footContent: '',
      codeShow: false,
      imgAppShow: true,
      codeIndex: 4
    }
  },
  computed: {
    msg() {
      return this.$store.state.user.loginMsg
    },
    qrTypes() {
      return this.loginTypes && this.loginTypes.filter(item => item > 3 && item < 7) || []
    },
    radioTypes() {
      return this.loginTypes && this.loginTypes.filter(item => item < 4 || item > 6) || []
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  beforeCreate() {
    pluginLoaded().then(res => {
      this.isPluginLoaded = res.success && res.data
      this.isPluginLoaded && initTheme()
      this.contentShow = true
    }).catch(() => {
      this.contentShow = true
    })

    casStatus().then(res => {
      if (res.success && res.data) {
        this.loginTypes.push(3)
      }
    })

    ldapStatus().then(res => {
      if (res.success && res.data) {
        this.loginTypes.push(1)
      }
      this.setDefaultType()
    })

    oidcStatus().then(res => {
      if (res.success && res.data) {
        this.loginTypes.push(2)
      }
      this.setDefaultType()
    })

    wecomStatus().then(res => {
      if (res.success && res.data) {
        this.loginTypes.push(4)
        const arr = this.loginTypes.filter(item => item > 3)
        this.codeIndex = arr && arr.length && Math.min(...arr) || this.codeIndex
      }
      this.setDefaultType()
    })

    dingtalkStatus().then(res => {
      if (res.success && res.data) {
        this.loginTypes.push(5)
        const arr = this.loginTypes.filter(item => item > 3)
        this.codeIndex = arr && arr.length && Math.min(...arr) || this.codeIndex
      }
      this.setDefaultType()
    })

    larkStatus().then(res => {
      if (res.success && res.data) {
        this.loginTypes.push(6)
        const arr = this.loginTypes.filter(item => item > 3)
        this.codeIndex = arr && arr.length && Math.min(...arr) || this.codeIndex
      }
      this.setDefaultType()
    })

    larksuiteStatus().then(res => {
      if (res.success && res.data) {
        this.loginTypes.push(7)
      }
      this.setDefaultType()
    })

    getPublicKey().then(res => {
      if (res.success && res.data) {
        // 保存公钥
        localStorage.setItem('publicKey', res.data)
      }
    })
    defaultLoginType().then(res => {
      if (res && res.success) {
        this.defaultType = res.data
      }
      if (this.loginTypes.includes(3) && this.defaultType === 3) {
        casLoginPage().then(res => {
          window.location.href = res.data
        })
      }
      this.setDefaultType()
    })
  },

  mounted() {
    // this.loading = false
  },

  created() {
    this.$store.dispatch('user/getUI').then((res) => {
      this.axiosFinished = true
      this.showLoginImage(res)
    }).catch(err => {
      console.error(err)
    })
    let msg = Cookies.get('OidcError')
    if (msg) {
      msg = msg.replace('+', '')
      this.$error(msg)
    }
    this.clearOidcMsg()

    if (Cookies.get('WecomError')) {
      this.$error(Cookies.get('WecomError'))
      this.switchCodeIndex(4)
    }
    this.clearWecomMsg()

    if (Cookies.get('DingtalkError')) {
      this.$error(Cookies.get('DingtalkError'))
      this.switchCodeIndex(5)
    }
    this.clearDingtalkMsg()

    if (Cookies.get('LarkError')) {
      this.$error(Cookies.get('LarkError'))
      this.switchCodeIndex(6)
    }
    this.clearLarkMsg()

    if (Cookies.get('LarksuiteError')) {
      this.$error(Cookies.get('LarksuiteError'))
    }
    this.clearLarksuiteMsg()
  },

  methods: {
    switchCodeIndex(codeIndex) {
      this.codeIndex = codeIndex
    },
    showQr() {
      this.codeShow = !this.codeShow
    },
    setDefaultType() {
      if (this.loginTypes.includes(this.defaultType)) {
        this.loginForm.loginType = this.defaultType
        this.$nextTick(() => {
          this.changeLoginType(this.loginForm.loginType)
        })
      }
    },
    clearOidcMsg() {
      Cookies.remove('OidcError')
      Cookies.remove('IdToken')
    },
    clearWecomMsg() {
      Cookies.remove('WecomError')
    },
    clearDingtalkMsg() {
      Cookies.remove('DingtalkError')
    },
    clearLarkMsg() {
      Cookies.remove('LarkError')
    },
    clearLarksuiteMsg() {
      Cookies.remove('LarksuiteError')
    },
    showLoginImage(uiInfo) {
      this.uiInfo = getSysUI()
      if (!this.uiInfo || Object.keys(this.uiInfo).length === 0) {
        this.uiInfo = uiInfo
      }
      if (this.uiInfo['ui.loginImage'] && this.uiInfo['ui.loginImage'].paramValue) {
        this.loginImageUrl = '/system/ui/image/' + this.uiInfo['ui.loginImage'].paramValue
      }
      if (this.uiInfo['ui.loginLogo'] && this.uiInfo['ui.loginLogo'].paramValue) {
        this.loginLogoUrl = '/system/ui/image/' + this.uiInfo['ui.loginLogo'].paramValue
      }

      if (this.uiInfo['ui.favicon'] && this.uiInfo['ui.favicon'].paramValue) {
        const faviconUrl = '/system/ui/image/' + this.uiInfo['ui.favicon'].paramValue
        changeFavicon(faviconUrl)
      }
      if (this.uiInfo['ui.showFoot'] && this.uiInfo['ui.showFoot'].paramValue) {
        this.showFoot = this.uiInfo['ui.showFoot'].paramValue === true || this.uiInfo['ui.showFoot'].paramValue === 'true'
        if (this.showFoot) {
          const content = this.uiInfo['ui.footContent'] && this.uiInfo['ui.footContent'].paramValue
          this.footContent = content
        }
      }
    },
    initCache() {
      this.clearLocalStorage.forEach(item => {
        localStorage.removeItem(item)
      })
    },

    handleLogin() {
      this.initCache()
      this.clearOidcMsg()
      this.clearWecomMsg()
      this.clearDingtalkMsg()
      this.clearLarkMsg()
      this.clearLarksuiteMsg()
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = false
          const user = {
            username: encrypt(this.loginForm.username),
            password: encrypt(this.loginForm.password),
            loginType: this.loginForm.loginType
          }
          this.$store.dispatch('user/login', user).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          return false
        }
      })
    },
    changeLoginType(val) {
      if (val !== 2 && val !== 7) return
      this.clearOidcMsg()
      this.clearLarksuiteMsg()
      this.$nextTick(() => {

      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../../styles/variables";

@mixin login-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-background {
  background: url(../../assets/bg.jpg) no-repeat;
  //background-color: var(--MainBG, $--background-color-base);
  height: 100vh;
  @include login-center;
}

.login-container {
  position: relative;
  left: 0;
  width: 96%;
  height: 92%;
  min-height: 600px;
  border-radius: 24px;
  background-size: 100% 100% !important;
  background: url(../../assets/bjlogin.png) no-repeat;
  //background-color: var(--ContentBG, #FFFFFF);
  @media only screen and (max-width: 1280px) {
    width: 900px;
    height: 380px;
  }
  .lock {
    position: absolute;
    right: 0px;
    top: 0px;
    width: 10.2%;
  }
  .contentleft {
    top: 5%;
    position: relative;
    left: 4%;
    width: 60%;
    text-align: left;
  }
  .contentleft h3 {
    font-size: 40px;
    color: #fff;
    line-height: 58px;
    vertical-align: middle;
  }

  .contentleft h6 {
    font-size: 28px;
    color: #fff;
    font-weight: normal;
    text-align: left;
    line-height: 50px;
  }
   .formbox {
    width: 27%;
    height: 80%;
    text-align: left;
    right: 5%;
    position: absolute;
    clear: both;
    margin-top: 5%;
  }

  .formbox h2,.formbox h4 {
    color: #fff;
    padding: 0px 40px;
    font-weight: normal;
  }

  .formbox h2 {
    font-size: 54px;
    line-height: 100px;
    vertical-align: middle;
  }

  .formbox h4 {
    font-size: 24px;
    color: #fff;
    opacity: 0.5 ;
  }

  .login-logo {
    margin-top: 50px;
    text-align: center;
    @media only screen and (max-width: 1280px) {
      margin-top: 20px;
    }
    img{
      /*width: 240px;*/
      width: auto;
      max-height: 60px;
      @media only screen and (max-width: 1280px) {
        /*width: 200px;*/
        width: auto;
        max-height: 50px;
      }
    }
  }

  .login-title {
    margin-top: 50px;
    font-size: 32px;
    letter-spacing: 0;
    text-align: center;
    color: #999999;

    @media only screen and (max-width: 1280px) {
      margin-top: 20px;
    }
  }

  .login-border {
    height: 2px;
    margin: 20px auto 20px;
    position: relative;
    width: 80px;
    background: $--color-primary;
    @media only screen and (max-width: 1280px) {
      margin: 20px auto 20px;
    }
  }

  .login-welcome {
    margin-top: 20px;
    font-size: 14px;
    color: $--color-primary;
    letter-spacing: 0;
    line-height: 18px;
    text-align: center;
    @media only screen and (max-width: 1280px) {
      margin-top: 20px;
    }
  }

  .demo-tips {
    margin-top: 20px;
    font-size: 18px;
    color: $--color-danger;
    letter-spacing: 0;
    line-height: 18px;
    text-align: center;
    @media only screen and (max-width: 1280px) {
      margin-top: 20px;
    }
  }

  .login-form {
    margin-top: 80px;
    padding: 0 40px;

    @media only screen and (max-width: 1280px) {
      margin-top: 40px;
    }

    & ::v-deep .el-input__inner {
      border-radius: 20px;
      border: 1px solid transparent;
      background: $colorBg;
      background-color: #FFFFFF;
    }
    & :focus {
      border: 1px solid $--color-primary;
    }
  }

  .login-btn {
    margin-top: 22px;
    padding: 0 40px;
    @media only screen and (max-width: 1280px) {
      margin-top: 20px;
    }

    .submit {
      width: 100%;
      border-radius: 20px;
    }
  }

  .login-msg {
    margin-top: 10px;
    padding: 0 40px;
    color: $--color-danger;
    text-align: center;
  }

  .login-image {
    //background: url(../../assets/login-desc.png) no-repeat;
    background-size: cover;
    width: 100%;
    height: 520px;
    @media only screen and (max-width: 1280px) {
      height: 380px;
    }
  }
  .login-image-de {
    background-size: cover;
    width: 100%;
    height: 520px;
    @media only screen and (max-width: 1280px) {
      height: 380px;
    }
  }
}
.dynamic-login-foot {
  visibility: visible;
  width: 100%;
  position: fixed;
  z-index: 302;
  bottom: 0;
  left: 0;
  height: auto;
  padding-top: 1px;
  zoom: 1;
  margin: 0;
}

.trans {
  position: absolute;
  left: calc(50% - 64px);
  top: 0;
  width: 64px;
  height: 64px;
  background-image: url(../../assets/qrcode.png) ;
  cursor:pointer;
}
.trans-pc {
  margin-left: calc(100% - 64px);
  top: 0;
  width: 64px;
  height: 64px;
  background: var(--primary,#3370ff) url(../../assets/xianshiqi-2.png) no-repeat top right/40px;
  cursor:pointer;
}
.imgApp {
  width: 64px;
  height: 64px;
  background: linear-gradient(225deg,transparent 45px, #fff 0);
}
.code{
  width: 100%;
  height: 100%; //将登录框挤出显示区域
  text-align: center;
  img {
    width: 150px;
    height: 150px;
    padding: 10px;
  }
  .code-contaniner {
    height: 410px;
  }
}
.login-third-item {
  display: inline-block;
  width: 32px;
  height: 32px;
  border-radius: 50% 50%;
  background: #ccc;
  cursor: pointer;
  margin: 0 10px;
}
.login-third-wecom {
  background: url(../../assets/wecom.png) no-repeat 50%/cover;
}
.login-third-dingtalk {
  background: url(../../assets/dingding01.png) no-repeat 50%/cover;
}
.login-third-lark {
  background: url(../../assets/lark.png) no-repeat 50%/cover;
}

</style>
