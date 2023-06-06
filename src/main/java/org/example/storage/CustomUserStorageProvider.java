package org.example.storage;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.LegacyUserCredentialManager;
import org.keycloak.models.*;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class CustomUserStorageProvider implements UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator,
        UserQueryProvider {

    private Logger logger = Logger.getLogger(CustomUserStorageProvider.class);

    private KeycloakSession keycloakSession;
    private ComponentModel componentModel;

    private Map<String,String> users;

    public CustomUserStorageProvider(KeycloakSession keycloakSession, ComponentModel componentModel, Map<String, String> users) {
        this.componentModel=componentModel;
        this.keycloakSession = keycloakSession;
        this.users = users;
    }

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(RealmModel realmModel, String id) {
        logger.infof("getUserById %s",id);
        StorageId storageId = new StorageId(id);
        logger.infof("providerID %s",storageId.getProviderId());
        String username = storageId.getExternalId();
        return getUserByUsername(realmModel,username);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realmModel, String s) {
        logger.infof("getUserByUsername %s",s);
        return users.get(s).isEmpty() ? null : createUserModel(realmModel,s);
    }

    @Override
    public UserModel getUserByEmail(RealmModel realmModel, String s) {
        return getUserByUsername(realmModel,s);
    }

    private UserModel createUserModel(RealmModel realmModel, String username){
        return new AbstractUserAdapter(keycloakSession,realmModel,componentModel){

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public SubjectCredentialManager credentialManager() {
                return new LegacyUserCredentialManager(keycloakSession,realmModel,this);
            }
        };
    }

    @Override
    public boolean supportsCredentialType(String s) {
        return s.equals(PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realmModel, UserModel userModel, String s) {
        return supportsCredentialType(s) && users.get(userModel.getUsername()).isEmpty();
    }

    @Override
    public boolean isValid(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {

        UserCredentialModel cred = (UserCredentialModel) credentialInput;
        logger.infof("Input Credential: %s; Actual Credential: %s",cred.getValue(),users.get(userModel.getUsername()));
        return cred.getValue().equals(users.get(userModel.getUsername()));
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realmModel, String s, Integer integer, Integer integer1) {
        List<UserModel> userModelList = new LinkedList<>();
        users.forEach((username, pass) -> {
            if(username.contains(s)){
                userModelList.add(createUserModel(realmModel,username));
            }
        });

        return userModelList.stream();
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realmModel, Map<String, String> map, Integer integer, Integer integer1) {
        return null;
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realmModel, GroupModel groupModel, Integer integer, Integer integer1) {
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realmModel, String s, String s1) {
        return null;
    }
}
