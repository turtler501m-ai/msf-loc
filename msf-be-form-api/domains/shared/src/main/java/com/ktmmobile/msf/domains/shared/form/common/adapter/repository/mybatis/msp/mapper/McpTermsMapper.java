package com.ktmmobile.msf.domains.shared.form.common.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsContent;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsItem;

@Mapper
public interface McpTermsMapper {

    List<TermsContent> selectListTermsContent(List<TermsItem> itemList);
}
