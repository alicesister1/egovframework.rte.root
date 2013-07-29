package egovframework.rte.psl.data.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.data.jpa.domain.User;
import egovframework.rte.psl.data.jpa.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/context-*.xml")
@Transactional
public class SimpleTest {

	@Autowired
	UserRepository repository;
	
	User user;

	@Before
	public void setUp() {

		user = new User();
		user.setUsername("foobar");
		user.setFirstname("firstname");
		user.setLastname("lastname");
	}

	@Test
	public void testInsert() {

		user = repository.save(user);

		assertEquals(user, repository.findOne(user.getId()));
	}

	@Test
	public void testFindByLastname() throws Exception {

		user = repository.save(user);

		List<User> users = repository.findByLastname("lastname");

		assertNotNull(users);
		assertTrue(users.contains(user));
	}

	@Test
	public void testFindByName() throws Exception {

		user = repository.save(user);

		List<User> users = repository.findByFirstnameOrLastname("lastname");

		assertTrue(users.contains(user));
	}
}
