package org.example.auth;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.authenticators.browser.AbstractUsernameFormAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;


public class CustomAuthenticationProvider extends  AbstractUsernameFormAuthenticator{
    @Override
    public void authenticate(AuthenticationFlowContext context) {
        if(context.getUser().getFirstAttribute("authenticated").equals("true")){
            context.success();
        }else {
            context.challenge(context.form().setAttribute("realm", context.getRealm()).createLoginTotp());
        }
    }


    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return !userModel.getFirstAttribute("secretNumber").isEmpty();
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void action(AuthenticationFlowContext context) {
        //super.action(context);
        MultivaluedMap<String,String> input= context.getHttpRequest().getDecodedFormParameters();
        String inputSecret = input.getFirst("otp");
        String secret = context.getUser().getFirstAttribute("secretNumber");
        if(inputSecret.equals(secret)){
            context.getUser().setSingleAttribute("authenticated","true");
            context.success();
        }else {
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,context.form().setAttribute("realm",context.getRealm()).createLoginTotp());
        }
    }
}
