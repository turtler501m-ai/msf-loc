package com.ktmmobile.mcp.mcash.controller;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.mcash.dto.McashUserDto;
import com.ktmmobile.mcp.mcash.mapper.McashMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@RestController
public class McashController {

    private static final Logger logger = LoggerFactory.getLogger(McashController.class);
    
    @Autowired
    McashMapper mcashMapper;

    /**
     * M캐시 가입 시 회선 리스트 조회
     * @param map
     * @return List<McashUserDto>
     */
    @RequestMapping(value = "/mcash/getMcashAvailableCntrList", method = {RequestMethod.POST, RequestMethod.GET})
    public List<McashUserDto> getMcashAvailableCntrList(@RequestBody String userid) {
        List<McashUserDto> mcashAvailableCntrList;

        try {
            // Database 에서 조회함.
            mcashAvailableCntrList = mcashMapper.getMcashAvailableCntrList(userid);
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return mcashAvailableCntrList;
    }

    /**
     * M캐시 가입정보 조회
     * @param userid
     * @return McashUserDto
     */
    @RequestMapping(value = "/mcash/getMcashJoinInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public McashUserDto getMcashJoinInfo(@RequestBody String userid) {
        McashUserDto mcashJoinInfo;

        try {
            // Database 에서 조회함.
            mcashJoinInfo = mcashMapper.getMcashJoinInfo(userid);
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mcashJoinInfo;
    }

    /**
     * M캐시 신규가입시 회원 정보 조회
     * @param svcCntrNo
     * @return McashUserDto
     */
    @RequestMapping(value = "/mcash/getUserInfoBySvcCntrNo", method = RequestMethod.POST)
    public McashUserDto getUserInfoBySvcCntrNo(@RequestBody String svcCntrNo) {
        McashUserDto mcashUserDto;

        try {
            mcashUserDto = mcashMapper.getUserInfoBySvcCntrNo(svcCntrNo);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mcashUserDto;
    }

}