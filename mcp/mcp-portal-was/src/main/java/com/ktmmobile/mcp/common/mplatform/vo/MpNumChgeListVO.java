package com.ktmmobile.mcp.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.common.util.XmlParse;

public class MpNumChgeListVO extends CommonXmlVO{
	private List<Map<String, String>> list;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		List<Element> itemList = XmlParse.getChildElementList(this.root, "outDto");

		list = new ArrayList<Map<String, String>>();
		for(Element item : itemList){
			Map<String, String> vo = new HashMap<String, String>();

			String ctn = XmlParse.getChildValue(item, "ctn");
			String[] nums = StringUtil.getMobileNum(ctn);
    		StringBuffer sb = new StringBuffer();
    		sb.append(nums[0]).append("-").append(nums[1]).append("-").append(nums[2]);
			vo.put("ctn", sb.toString());
			vo.put("orignCtn", XmlParse.getChildValue(item, "ctn"));
			vo.put("sctn", XmlParse.getChildValue(item, "sctn"));
			vo.put("marketGubun", XmlParse.getChildValue(item, "marketGubun"));

			list.add(vo);
		}
	}

	/**
	 * @return the list
	 */
	public List<Map<String, String>> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}


}
