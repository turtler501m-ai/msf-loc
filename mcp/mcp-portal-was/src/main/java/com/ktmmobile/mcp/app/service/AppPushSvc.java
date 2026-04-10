/**
 *
 */
package com.ktmmobile.mcp.app.service;

import com.ktmmobile.mcp.app.dto.PushSendDto;

/**
 * @author ANT_FX700_02
 *
 */
public interface AppPushSvc {

	void immediatelyPushSend(PushSendDto pushSendDto);

}
