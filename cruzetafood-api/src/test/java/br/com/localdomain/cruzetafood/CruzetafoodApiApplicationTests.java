package br.com.localdomain.cruzetafood;



import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(value = { SpringExtension.class })
@SpringBootTest(classes = CruzetafoodApiApplicationTests.class)
public class CruzetafoodApiApplicationTests {

	@Test
	public void contextLoads() {
		assertTrue(false);
	}

}
