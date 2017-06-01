package io.logz.apollo.rest;

import com.google.inject.Injector;
import io.logz.apollo.auth.PasswordManager;
import io.logz.apollo.auth.User;
import io.logz.apollo.common.HttpStatus;
import io.logz.apollo.configuration.ApolloConfiguration;
import io.logz.apollo.dao.UserDao;
import org.rapidoid.integrate.GuiceBeans;
import org.rapidoid.integrate.Integrate;
import org.rapidoid.security.Role;
import org.rapidoid.setup.App;
import org.rapidoid.setup.My;
import org.rapidoid.setup.On;
import org.rapidoid.u.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import static java.util.Objects.requireNonNull;

/**
 * Created by roiravhon on 11/22/16.
 */
public class RestServer {

    private static final Logger logger = LoggerFactory.getLogger(RestServer.class);

    private final ApolloConfiguration configuration;
    private final Injector injector;
    private final UserDao userDao;

    @Inject
    public RestServer(ApolloConfiguration configuration, Injector injector, UserDao userDao) {
        this.configuration = requireNonNull(configuration);
        this.injector = requireNonNull(injector);
        this.userDao = requireNonNull(userDao);
    }

    @PostConstruct
    public void start() {
        registerLoginProvider();
        registerRolesProvider();

        String[] args = new String[] {
                "secret=" + configuration.getSecret(),
                "on.address=" + configuration.getApiListen(),
                "on.port=" + configuration.getApiPort()
        };

        // Initialize the REST API server
        On.changes().ignore();
        GuiceBeans beans = Integrate.guice(injector);
        App.run(args).auth();
        App.register(beans);
    }

    @PreDestroy
    public void stop() {
        // Future cleanups..
    }

    private void registerLoginProvider() {
        My.loginProvider((req, username, password) -> {
            User requestedUser = userDao.getUser(username);
            if (requestedUser == null) {
                req.response().code(HttpStatus.UNAUTHORIZED);
                return false;
            }

            if (PasswordManager.checkPassword(password, requestedUser.getHashedPassword())) {
                return true;
            } else {
                req.response().code(HttpStatus.UNAUTHORIZED);
                return false;
            }
        });
    }

    private void registerRolesProvider() {
        My.rolesProvider((req, username) -> {
            try {
                if (userDao.getUser(username).isAdmin()) {
                    return U.set(Role.ADMINISTRATOR);
                }
                return U.set(Role.ANYBODY);

            } catch (Exception e) {
                logger.error("Got exception while getting user roles! setting to ANYBODY", e);
                return U.set(Role.ANYBODY);
            }
        });
    }

}
