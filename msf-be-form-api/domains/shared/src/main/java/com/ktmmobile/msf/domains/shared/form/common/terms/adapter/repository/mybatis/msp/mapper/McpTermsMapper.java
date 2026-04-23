package com.ktmmobile.msf.domains.shared.form.common.terms.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsContent;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsItem;

@Mapper
public interface McpTermsMapper {

    List<TermsContent> selectListTermsContent(List<TermsItem> itemList);
}
