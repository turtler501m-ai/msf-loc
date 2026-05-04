package com.ktmmobile.msf.commons.logincore.domain.policy.failure;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyLogUtils;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicySelector;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyType;

@Slf4j
@Component
public class LoginFailurePolicyComposite {

    private final List<SelectedFailurePolicy> policies;

    public LoginFailurePolicyComposite(Map<String, LoginFailurePolicy> policies, LoginPolicySelector policySelector) {
        List<String> missingPolicies = policySelector.missing(LoginPolicyType.FAILURE, policies);
        if (!missingPolicies.isEmpty()) {
            log.warn("Missing LoginFailurePolicy beans configured in login-core.policy.failure: {}", missingPolicies);
        }
        List<String> selectedNames = policySelector.selectedNames(LoginPolicyType.FAILURE, policies);
        this.policies = selectedNames.stream()
            .map(beanName -> new SelectedFailurePolicy(beanName, policies.get(beanName)))
            .toList();
        logSelectedPolicies(LoginPolicyLogUtils.names(policies, selectedNames));
    }

    public boolean shouldLock(LoginFailureContext<?> context) {
        boolean shouldLock = false;
        for (SelectedFailurePolicy selected: policies) {
            shouldLock = evaluate(selected, context) || shouldLock;
        }
        return shouldLock;
    }

    private boolean evaluate(SelectedFailurePolicy selected, LoginFailureContext<?> context) {
        boolean supported = selected.policy().supports(context);
        log.info(
            "Login failure policy evaluated. policy={}, userId={}, supported={}",
            selected.beanName(),
            context.user().userId(),
            supported
        );
        if (!supported) {
            return false;
        }
        boolean shouldLock = selected.policy().shouldLock(context);
        log.info(
            "Login failure policy resolved. policy={}, userId={}, shouldLock={}",
            selected.beanName(),
            context.user().userId(),
            shouldLock
        );
        return shouldLock;
    }

    private void logSelectedPolicies(String policyNames) {
        if (LoginPolicyLogUtils.NONE.equals(policyNames)) {
            log.warn("LoginFailurePolicy: {}", policyNames);
            return;
        }
        log.info("LoginFailurePolicy: {}", policyNames);
    }

    private record SelectedFailurePolicy(
        String beanName,
        LoginFailurePolicy policy
    ) {
    }
}
