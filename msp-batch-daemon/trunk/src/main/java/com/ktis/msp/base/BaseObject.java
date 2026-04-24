package com.ktis.msp.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class BaseObject {

	/**
	 * Return Common JSON Object
	 * 
	 * @return
	 */

	@Autowired
	protected MessageSource messageSource;

	/*protected MessageBean getMessage() {
		return new MessageBean();
	}
*/
	/**
	 * Forward object
	 * 
	 * @param model
	 * @param message
	 */
/*	protected void SendMessage(Model model, MessageBean message) {
		
		if (!message.containsKey(MessageBean.MSG_RETURN_CODE)) {
			message.setReturnCode(MessageCode.FAIL);
			message.setReturnMessage("반환코드가 없습니다.");
		}
		model.addAttribute("MESSAGE", message);
	}*/

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private String logPrefix;

	public String generateLogMsg(String message) {
		return String.format("%s%s", logPrefix, message);
	}

	public void setLogPrefix(String logPrefix) {
		this.logPrefix = logPrefix;
	}

	public String getLogPrefix() {
		return logPrefix;
	}

	public String getSqlCodeFromMsg(String pMsg) {
		Matcher matcher = Pattern.compile("(ORA-.....)").matcher(pMsg);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}

	/**
	 * <PRE>
	 * 1. MethodName: dummyFinally
	 * 2. ClassName	: BaseController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 26. 오전 11:39:24
	 * </PRE>
	 * 
	 * @return void
	 */
/*	public String dummyCatch() {
		return "";
	}*/

	/**
	 * <PRE>
	 * 1. MethodName: dummyFinally
	 * 2. ClassName	: BaseController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 26. 오전 11:39:24
	 * </PRE>
	 * 
	 * @return void
	 */
/*	public String dummyFinally() {
		return "";

	}*/
/*
	public void writeCsv(List<?> resultList, PrintWriter printWriter) {
		for (int inx = 0; inx < resultList.size(); inx++) {
			Map row = (Map) resultList.get(inx);

			Iterator<Map.Entry<String, String>> entries = row.entrySet().iterator();

			while (entries.hasNext()) {
				Map.Entry<String, String> entry = entries.next();
				printWriter.write("\"" + row.get(entry.getKey()) + "\",");
			}

			printWriter.write("\n");
		}

	}*/

}
