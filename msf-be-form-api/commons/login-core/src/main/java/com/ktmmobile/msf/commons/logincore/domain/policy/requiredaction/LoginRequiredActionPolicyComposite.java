package com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyLogUtils;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicySelector;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyType;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;

@Slf4j
@Component
public class LoginRequiredActionPolicyComposite {

    private final List<SelectedRequiredActionPolicy> policies;

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

    public List<LoginRequiredAction> resolve(LoginCompletionContext<?> context) {
        List<LoginRequiredAction> actions = new ArrayList<>();
        for (SelectedRequiredActionPolicy selected: policies) {
            Optional<LoginRequiredAction> action = resolve(selected, context);
            if (action.isPresent()) {
                actions.add(action.get());
            }
        }
        return List.copyOf(actions);
    }

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
