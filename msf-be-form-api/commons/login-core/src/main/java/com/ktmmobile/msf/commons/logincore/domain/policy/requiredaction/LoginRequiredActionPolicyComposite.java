package com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyLogUtils;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicySelector;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyType;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;

/**
 * 로그인 후속 조치 정책을 YAML whitelist 순서대로 평가하는 Composite
 */
@Slf4j
@Component
public class LoginRequiredActionPolicyComposite {

    private final List<SelectedRequiredActionPolicy> policies;

    /**
     * 로그인 후속 조치 정책 Composite 생성
     *
     * @param policies 후속 조치 정책 Bean Map
     * @param policySelector 로그인 정책 선택기
     */
    public LoginRequiredActionPolicyComposite(Map<String, LoginRequiredActionPolicy> policies, LoginPolicySelector policySelector) {
        List<String> missingPolicies = policySelector.missing(LoginPolicyType.REQUIRED_ACTION, policies);
        if (!missingPolicies.isEmpty()) {
            log.warn("Missing LoginRequiredActionPolicy beans configured in login-core.policy.required-action: {}", missingPolicies);
        }
        List<String> selectedNames = policySelector.selectedNames(LoginPolicyType.REQUIRED_ACTION, policies);
        this.policies = selectedNames.stream()
            .map(beanName -> new SelectedRequiredActionPolicy(beanName, policies.get(beanName)))
            .toList();
        logSelectedPolicies(LoginPolicyLogUtils.names(policies, selectedNames));
    }

    /**
     * 로그인 후속 조치 목록 결정
     *
     * @param context 로그인 완료 컨텍스트
     * @return 후속 조치 목록
     */
    public List<LoginRequiredAction> resolve(LoginCompletionContext<?> context) {
        List<LoginRequiredAction> actions = new ArrayList<>();
        for (SelectedRequiredActionPolicy selected: policies) {
            Optional<LoginRequiredAction> action = resolve(selected, context);
            // 여러 조치가 동시에 필요한 경우 YAML 순서대로 누적
            action.ifPresent(actions::add);
        }
        return List.copyOf(actions);
    }

    /**
     * 단일 후속 조치 정책 결정
     *
     * @param selected 선택 정책
     * @param context 로그인 완료 컨텍스트
     * @return 후속 조치
     */
    private Optional<LoginRequiredAction> resolve(SelectedRequiredActionPolicy selected, LoginCompletionContext<?> context) {
        boolean supported = selected.policy().supports(context);
        log.info(
            "Login required action policy evaluated. policy={}, userId={}, supported={}",
            selected.beanName(),
            context.user().userId(),
            supported
        );
        if (!supported) {
            return Optional.empty();
        }
        Optional<LoginRequiredAction> action = selected.policy().resolve(context);
        if (action.isPresent()) {
            LoginRequiredAction resolved = action.get();
            log.info(
                "Login required action policy resolved. policy={}, userId={}, action={}, tokenIssuable={}",
                selected.beanName(),
                context.user().userId(),
                resolved.code(),
                resolved.tokenIssuable()
            );
        } else {
            log.info(
                "Login required action policy resolved. policy={}, userId={}, action=NONE",
                selected.beanName(),
                context.user().userId()
            );
        }
        return action;
    }

    /**
     * 선택된 후속 조치 정책 로그 출력
     *
     * @param policyNames 정책 이름 목록 문자열
     */
    private void logSelectedPolicies(String policyNames) {
        if (LoginPolicyLogUtils.NONE.equals(policyNames)) {
            log.warn("LoginRequiredActionPolicy: {}", policyNames);
            return;
        }
        log.info("LoginRequiredActionPolicy: {}", policyNames);
    }

    private record SelectedRequiredActionPolicy(
        String beanName,
        LoginRequiredActionPolicy policy
    ) {
    }
}
