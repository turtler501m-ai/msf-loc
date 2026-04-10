package com.ktmmobile.msf.commons.common.utils.math;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtils {

    /**
     * ON DUPLICATE KEY UPDATE 시,
     * INSERT와 UPDATE가 동시에 발생하므로 하나의 레코드 당 2건의 affected row가 발생한다.
     * 이 메서드는 실제로 업데이트된 레코드 수를 계산하여 반환한다.
     * (실제로 단순히 나누기 2만 수행하여 반환한다.)
     */
    public static int getRealUpdateCountOnDuplicateKeyUpdate(int affectedRows) {
        return (affectedRows + 1) / 2; //== (int) Math.ceil((double) affectedRows / 2)
    }
}
