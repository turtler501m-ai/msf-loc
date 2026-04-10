package com.ktmmobile.mcp.mstore.dto;

import java.io.Serializable;
import java.util.List;

public class MstoreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String searchType;          //조회 타입
    private String empen;               //사번 (회원 CI값)
    private List<MstoreApiDto> users;   //탈퇴연동 대상

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getEmpen() {
        return empen;
    }

    public void setEmpen(String empen) {
        this.empen = empen;
    }

    public List<MstoreApiDto> getUsers() {
        return users;
    }

    public void setUsers(List<MstoreApiDto> users) {
        this.users = users;
    }
}
