package io.dataease.plugins.server;

import io.dataease.auth.entity.SysUserEntity;
import io.dataease.auth.entity.TokenInfo;
import io.dataease.auth.service.AuthUserService;
import io.dataease.auth.util.JWTUtils;
import io.dataease.commons.constants.SysLogConstants;
import io.dataease.commons.exception.DEException;
import io.dataease.commons.utils.BeanUtils;
import io.dataease.commons.utils.DeLogUtils;
import io.dataease.commons.utils.LogUtil;
import io.dataease.commons.utils.ServletUtils;
import io.dataease.exception.DataEaseException;
import io.dataease.i18n.Translator;
import io.dataease.plugins.common.base.domain.SysUserAssist;
import io.dataease.plugins.config.SpringContextUtil;

import io.dataease.plugins.xpack.display.dto.response.SysSettingDto;
import io.dataease.plugins.xpack.lark.dto.entity.LarkAppUserEntity;
import io.dataease.plugins.xpack.lark.dto.entity.LarkQrResult;
import io.dataease.plugins.xpack.lark.dto.entity.LarkUserInfo;
import io.dataease.plugins.xpack.lark.dto.response.LarkAppUserResult;
import io.dataease.plugins.xpack.lark.dto.response.LarkInfo;
import io.dataease.plugins.xpack.lark.service.LarkXpackService;
import io.dataease.service.sys.SysUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@ApiIgnore
@RequestMapping("/plugin/lark")
@Controller
public class XLarkServer {


    @Resource
    private AuthUserService authUserService;
    @Resource
    private SysUserService sysUserService;

    @ResponseBody
    @GetMapping("/appId")
    public String getAppId() {
        LarkXpackService larkXpackService = SpringContextUtil.getBean(LarkXpackService.class);
        return larkXpackService.appId();
    }

    @ResponseBody
    @GetMapping("/info")
    public LarkInfo getLarkInfo() {
        LarkXpackService larkXpackService = SpringContextUtil.getBean(LarkXpackService.class);
        return larkXpackService.info();
    }

    @ResponseBody
    @RequiresPermissions("sysparam:read")
    @PostMapping("/save")
    public void save(@RequestBody List<SysSettingDto> settings) {
        LarkXpackService larkXpackService = SpringContextUtil.getBean(LarkXpackService.class);
        larkXpackService.save(settings);
    }

    @ResponseBody
    @PostMapping("/testConn")
    public void testConn(@RequestBody LarkInfo larkInfo) {
        LarkXpackService larkXpackService = SpringContextUtil.getBean(LarkXpackService.class);
        try {
            larkXpackService.testConn(larkInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @PostMapping("/getQrParam")
    public LarkQrResult getQrParam() {
        LarkXpackService larkXpackService = SpringContextUtil.getBean(LarkXpackService.class);
        return larkXpackService.getQrParam();
    }

    @GetMapping("/callBackWithoutLogin")
    public ModelAndView callBackWithoutLogin(@RequestParam("code") String code, @RequestParam("mobile") String mobile) {
        boolean isMobile = StringUtils.equals("1", mobile);
        return privateCallBack(code, null, true, isMobile);
    }

    private ModelAndView privateCallBack(String code, String state, Boolean withoutLogin, Boolean isMobile) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        HttpServletResponse response = ServletUtils.response();
        LarkXpackService larkXpackService = null;
        try {
            Map<String, LarkXpackService> beansOfType = SpringContextUtil.getApplicationContext().getBeansOfType((LarkXpackService.class));
            if (beansOfType.keySet().size() == 0) {
                DEException.throwException("缺少飞书插件");
            }
            larkXpackService = SpringContextUtil.getBean(LarkXpackService.class);
            Boolean isOpen = larkXpackService.isOpen();
            if (!isOpen) {
                DEException.throwException("未开启飞书");
            }
            LarkUserInfo larkUserInfo = null;
            if (withoutLogin) {
                LarkAppUserResult larkAppUserResult = larkXpackService.userInfoWithoutLogin(code);
                LarkAppUserEntity userResultData = larkAppUserResult.getData();
                larkUserInfo = BeanUtils.copyBean(new LarkUserInfo(), userResultData);
            } else {
                larkUserInfo = larkXpackService.userInfo(code, state, false);
            }
            String username = larkUserInfo.getUser_id();
            SysUserEntity sysUserEntity = authUserService.getUserByLarkId(username);
            if (null == sysUserEntity) {
                if (authUserService.checkScanCreateLimit())
                    DEException.throwException(Translator.get("I18N_PROHIBIT_SCANNING_TO_CREATE_USER"));
                String email = StringUtils.isNotBlank(larkUserInfo.getEmail()) ? larkUserInfo.getEmail() : (username + "@lark.work");
                sysUserService.validateExistUser(username, larkUserInfo.getName(), email);
                sysUserService.saveLarkCUser(larkUserInfo, email);
                sysUserEntity = authUserService.getUserByLarkId(username);
            } else if (sysUserEntity.getEnabled() == 0) {
                DataEaseException.throwException(Translator.get("i18n_user_is_disable"));
            }
            TokenInfo tokenInfo = TokenInfo.builder().userId(sysUserEntity.getUserId()).username(sysUserEntity.getUsername()).build();
            String realPwd = sysUserEntity.getPassword();
            String token = JWTUtils.sign(tokenInfo, realPwd, !isMobile);
            ServletUtils.setToken(token);

            DeLogUtils.save(SysLogConstants.OPERATE_TYPE.LOGIN, SysLogConstants.SOURCE_TYPE.USER, sysUserEntity.getUserId(), null, null, null);

            Cookie cookie_token = new Cookie("Authorization", token);
            cookie_token.setPath("/");

            response.addCookie(cookie_token);
            if (withoutLogin) {
                Cookie platformCookie = new Cookie("inOtherPlatform", "true");
                platformCookie.setPath("/");
                response.addCookie(platformCookie);
            }
        } catch (Exception e) {

            String msg = e.getMessage();
            if (null != e.getCause()) {
                msg = e.getCause().getMessage();
            }
            msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
            LogUtil.error(e);
            Cookie cookie_error = new Cookie("LarkError", msg);
            cookie_error.setPath("/");
            response.addCookie(cookie_error);
            return modelAndView;
        }
        return modelAndView;
    }

    @GetMapping("/callBack")
    public ModelAndView callBack(@RequestParam("code") String code, @RequestParam("state") String state) {
        return privateCallBack(code, state, false, false);
    }

    private void bindError(HttpServletResponse response, String url, String errorMsg) {
        Cookie cookie_error = new Cookie("LarkError", errorMsg);
        cookie_error.setPath("/");
        response.addCookie(cookie_error);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LogUtil.error(e.getMessage(), e);
            DEException.throwException(e);
        }
    }

    @GetMapping("/bind")
    public void bind(@RequestParam("code") String code, @RequestParam("state") String state) {

        HttpServletResponse response = ServletUtils.response();
        String url = "/#person-info/index";

        LarkXpackService larkXpackService = null;
        try {
            SysUserEntity userEntity = authUserService.getUserById(Long.parseLong(state));
            if (ObjectUtils.isEmpty(userEntity)) {
                bindError(response, url, "绑定用户不存在");
                return;
            }
            SysUserAssist sysUserAssist = sysUserService.assistInfo(Long.parseLong(state));
            if (ObjectUtils.isNotEmpty(sysUserAssist) && StringUtils.isNotBlank(sysUserAssist.getLarkId())) {
                bindError(response, url, "目标用户已绑定其他飞书账号");
                return;
            }

            Boolean isOpen = authUserService.supportLark();
            if (!isOpen) {
                DEException.throwException("未开启飞书");
            }
            larkXpackService = SpringContextUtil.getBean(LarkXpackService.class);
            LarkUserInfo larkUserInfo = larkXpackService.userInfo(code, state, true);
            String userId = larkUserInfo.getUser_id();


            SysUserEntity sysUserEntity = authUserService.getUserByLarkId(userId);
            if (null != sysUserEntity) {
                bindError(response, url, "当前飞书账号已绑定其他DE用户");
                return;
            }

            if (ObjectUtils.isEmpty(sysUserAssist)) {
                sysUserAssist = new SysUserAssist();
                sysUserAssist.setUserId(Long.parseLong(state));
            }
            sysUserAssist.setLarkId(userId);
            sysUserService.saveAssist(sysUserAssist.getUserId(), sysUserAssist.getWecomId(), sysUserAssist.getDingtalkId(), sysUserAssist.getLarkId(), sysUserAssist.getLarksuiteId());
            response.sendRedirect(url);
        } catch (Exception e) {

            String msg = e.getMessage();
            if (null != e.getCause()) {
                msg = e.getCause().getMessage();
            }
            msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
            LogUtil.error(e);
            bindError(response, url, msg);
        }
    }
}
