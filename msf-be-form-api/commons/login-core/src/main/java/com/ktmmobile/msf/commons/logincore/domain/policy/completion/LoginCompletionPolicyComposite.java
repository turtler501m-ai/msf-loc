package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyLogUtils;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicySelector;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyType;

@Slf4j
@Component
public class LoginCompletionPolicyComposite {

    private final List<SelectedCompletionPolicy> policies;

    public LoginCompletionPolicyComposite(Map<String, LoginCompletionPolicy> policies, LoginPolicySelector policySelector) {
        List<String> missingPolicies = policySelector.missing(LoginPolicyType.COMPLETION, policies);
        if (!missingPolicies.isEmpty()) {
            log.warn("Missing LoginCompletionPolicy beans configured in login-core.policy.completion: {}", missingPolicies);
        }
        List<String> selectedNames = policySelector.selectedNames(LoginPolicyType.COMPLETION, policies);
        this.policies = selectedNames.stream()
            .map(beanName -> new SelectedCompletionPolicy(beanName, policies.get(beanName)))
            .toList();
        logSelectedPolicies(LoginPolicyLogUtils.names(policies, selectedNames));
    }

    public void verify(LoginCompletionContext<?> context) {
        for (SelectedCompletionPolicy selected: policies) {
            verify(selected, context);
        }
    }

    private void verify(SelectedCompletionPolicy selected, LoginCompletionContext<?> context) {
        boolean supported = selected.policy().supports(context);
        log.info(
            "Login completion policy evaluated. policy={}, userId={}, supported={}",
            selected.beanName(),
            context.user().userId(),
            supported
        );
        if (!supported) {
            return;
        }
        try {
            selected.policy().verify(context);
            log.info(
                "Login completion policy passed. policy={}, userId={}",
                selected.beanName(),
                context.user().userId()
            );
        } catch (RuntimeException ex) {
            log.info(
                "Login completion policy failed. policy={}, userId={}, error={}",
                selected.beanName(),
                context.user().userId(),
                ex.getMessage()
            );
            throw ex;
        }
    }

    private void logSelectedPolicies(String policyNames) {
        if (LoginPolicyLogUtils.NONE.equals(policyNames)) {
            log.warn("LoginCompletionPolicy: {}", policyNames);
            return;
        }
        log.info("LoginCompletionPolicy: {}", policyNames);
    }

    private record SelectedCompletionPolicy(
        String beanName,
        LoginCompletionPolicy policy
    ) {
    }
}
