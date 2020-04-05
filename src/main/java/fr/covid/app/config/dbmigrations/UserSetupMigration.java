package fr.covid.app.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import fr.covid.app.domain.Authority;
import fr.covid.app.domain.User;
import fr.covid.app.security.AuthoritiesConstants;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Instant;

/**
 * Creates the initial database setup.
 */
@ChangeLog(order = "002")
public class UserSetupMigration {

    @ChangeSet(order = "01", author = "amineS", id = "01-addNewAuthorities")
    public void addNewAuthorities(MongoTemplate mongoTemplate) {
        Authority headOfServiceAuthority = new Authority();
        headOfServiceAuthority.setName(AuthoritiesConstants.HEAD_OF_SERVICE);

        Authority hospitalAdminAuthority = new Authority();
        hospitalAdminAuthority.setName(AuthoritiesConstants.HOSPITAL_ADMIN);
        Authority visiteurAuthority = new Authority();
        visiteurAuthority.setName(AuthoritiesConstants.VISITEUR);
        mongoTemplate.save(hospitalAdminAuthority);
        mongoTemplate.save(headOfServiceAuthority);
        mongoTemplate.save(visiteurAuthority);
    }

    @ChangeSet(order = "02", author = "amineS", id = "02-addNewUsers")
    public void addNewUsers(MongoTemplate mongoTemplate) {
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);

        Authority headOfServiceAuthority = new Authority();
        headOfServiceAuthority.setName(AuthoritiesConstants.HEAD_OF_SERVICE);

        Authority hospitalAdminAuthority = new Authority();
        hospitalAdminAuthority.setName(AuthoritiesConstants.HOSPITAL_ADMIN);
        Authority visiteurAuthority = new Authority();
        visiteurAuthority.setName(AuthoritiesConstants.VISITEUR);

        User headOfServiceUser = new User();
        headOfServiceUser.setId("user-4");
        headOfServiceUser.setLogin("head");
        headOfServiceUser.setPassword("$2y$12$bgl4kJ9P6bj4ctIRDBKlIORuVO3MPjwKZa445C86MHdLO6maukPg2");
        headOfServiceUser.setFirstName("head");
        headOfServiceUser.setLastName("head");
        headOfServiceUser.setEmail("covid.tunisia+head@gmail.com");
        headOfServiceUser.setActivated(true);
        headOfServiceUser.setLangKey("en");
        headOfServiceUser.setCreatedBy("system");
        headOfServiceUser.setCreatedDate(Instant.now());
        headOfServiceUser.getAuthorities().add(headOfServiceAuthority);
        headOfServiceUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(headOfServiceUser);

        User hospitalAdminUser = new User();
        hospitalAdminUser.setId("user-5");
        hospitalAdminUser.setLogin("hospitalAdmin");
        hospitalAdminUser.setPassword("$2y$12$BdXOpgizpDlpmSk7qcjz6.zRaJv5rJJUklMzf0g3UmeiPdzOtlWgm");
        hospitalAdminUser.setFirstName("hospitalAdmin");
        hospitalAdminUser.setLastName("hospitalAdmin");
        hospitalAdminUser.setEmail("covid.tunisia+hospitalAdmin@gmail.com");
        hospitalAdminUser.setActivated(true);
        hospitalAdminUser.setLangKey("en");
        hospitalAdminUser.setCreatedBy("system");
        hospitalAdminUser.setCreatedDate(Instant.now());
        hospitalAdminUser.getAuthorities().add(hospitalAdminAuthority);
        hospitalAdminUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(hospitalAdminUser);

        User visiteurUser = new User();
        visiteurUser.setId("user-6");
        visiteurUser.setLogin("visiteur");
        visiteurUser.setPassword("$2y$12$4lYUPopZ8hhyaIGDMNOJd.mKkGIEOvEMGLKAocfHWbtQyIPjnZ/y6");
        visiteurUser.setFirstName("visiteur");
        visiteurUser.setLastName("visiteur");
        visiteurUser.setEmail("covid.tunisia+visiteur@gmail.com");
        visiteurUser.setActivated(true);
        visiteurUser.setLangKey("en");
        visiteurUser.setCreatedBy("system");
        visiteurUser.setCreatedDate(Instant.now());
        visiteurUser.getAuthorities().add(visiteurAuthority);
        visiteurUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(visiteurUser);
    }
}
