package com.allmundo.callcenter.tests;

import com.almundo.callcenter.CallCenter;
import com.almundo.callcenter.llamadas.Llamada;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

/**
 * Created by Marcos on 12/1/2017.
 */
public class CallcenterTest extends TestCase {
	List<Llamada> llamadasBatchTest;
	private static final Logger LOGGER = Logger.getLogger( CallcenterTest.class.getName() );

	public CallcenterTest(String name) {
		super( name );
		this.llamadasBatchTest = new ArrayList<>();
	}

	@Before
	public void beforeEachTest() throws Exception {
		llamadasBatchTest = new ArrayList<>();
	}

	@Test
	public void testOneCall() throws Exception {
		int concurrentCalls = 1;
		int threads = 1;
		int op = 1;
		int sup = 0;
		int dir = 0;
		CallCenter callCenter = new CallCenter(threads, op, sup, dir);
		IntStream.range(0, concurrentCalls)
						.forEach(i -> this.llamadasBatchTest.add(new Llamada("llamada onecall " + String.valueOf(i))));
		callCenter.handleCalls(this.llamadasBatchTest);
		sleep(2000);
		Assert.assertEquals(this.llamadasBatchTest.get(0).getEmpleadoAtendio(), "Operador 0");
		Assert.assertTrue(this.llamadasBatchTest.get(0).getFailedAttemps() == 0);
	}

	@Test
	public void testTwoCallsOneSlot() throws Exception {
		int concurrentCalls = 2;
		int threads = 2;
		int op = 1;
		int sup = 0;
		int dir = 0;
		CallCenter callCenter = new CallCenter(threads, op, sup, dir);
		IntStream.range(0, concurrentCalls)
						.forEach(i -> this.llamadasBatchTest.add(new Llamada("llamada twoCallsOneSlot " + String.valueOf(i))));
		callCenter.handleCalls(this.llamadasBatchTest);
		sleep(4000);
		Assert.assertEquals(this.llamadasBatchTest.get(0).getEmpleadoAtendio(), "Operador 0");
		Assert.assertTrue(this.llamadasBatchTest.get(0).getFailedAttemps() == 0);
		Assert.assertEquals(this.llamadasBatchTest.get(1).getEmpleadoAtendio(), "Operador 0");
		Assert.assertTrue(this.llamadasBatchTest.get(1).getFailedAttemps() == 1);
	}

	@Test
	public void testDefault() throws Exception {
		int concurrentCalls = 10;
		CallCenter callCenter = new CallCenter();
		IntStream.range(0, concurrentCalls)
						.forEach(i -> this.llamadasBatchTest.add(new Llamada("llamada testDefault " + String.valueOf(i))));
		callCenter.handleCalls(this.llamadasBatchTest);
		sleep(2000);
		Assert.assertEquals(this.llamadasBatchTest.get(0).getEmpleadoAtendio(), "Operador 0");
		Assert.assertEquals(this.llamadasBatchTest.get(1).getEmpleadoAtendio(), "Operador 1");
		Assert.assertEquals(this.llamadasBatchTest.get(2).getEmpleadoAtendio(), "Operador 2");
		Assert.assertEquals(this.llamadasBatchTest.get(3).getEmpleadoAtendio(), "Operador 3");
		Assert.assertEquals(this.llamadasBatchTest.get(4).getEmpleadoAtendio(), "Operador 4");
		Assert.assertEquals(this.llamadasBatchTest.get(5).getEmpleadoAtendio(), "Supervisor 0");
		Assert.assertEquals(this.llamadasBatchTest.get(6).getEmpleadoAtendio(), "Supervisor 1");
		Assert.assertEquals(this.llamadasBatchTest.get(7).getEmpleadoAtendio(), "Supervisor 2");
		Assert.assertEquals(this.llamadasBatchTest.get(8).getEmpleadoAtendio(), "Director 0");
		Assert.assertEquals(this.llamadasBatchTest.get(9).getEmpleadoAtendio(), "Director 1");
	}

	@Test
	public void testPendingCalls() throws Exception {
		int concurrentCalls = 10;
		int threads = 6;
		int op = 3;
		int sup = 2;
		int dir = 1;
		CallCenter callCenter = new CallCenter(threads, op, sup, dir);
		IntStream.range(0, concurrentCalls)
						.forEach(i -> this.llamadasBatchTest.add(new Llamada("llamada pendingCalls " + String.valueOf(i))));
		callCenter.handleCalls(this.llamadasBatchTest);
		sleep(4000);
		Assert.assertEquals(this.llamadasBatchTest.get(0).getEmpleadoAtendio(), "Operador 0");
		Assert.assertTrue(this.llamadasBatchTest.get(1).getFailedAttemps() == 0);
		Assert.assertEquals(this.llamadasBatchTest.get(1).getEmpleadoAtendio(), "Operador 1");
		Assert.assertTrue(this.llamadasBatchTest.get(1).getFailedAttemps() == 0);
		Assert.assertEquals(this.llamadasBatchTest.get(2).getEmpleadoAtendio(), "Operador 2");
		Assert.assertTrue(this.llamadasBatchTest.get(2).getFailedAttemps() == 0);
		Assert.assertEquals(this.llamadasBatchTest.get(3).getEmpleadoAtendio(), "Supervisor 0");
		Assert.assertTrue(this.llamadasBatchTest.get(3).getFailedAttemps() == 0);
		Assert.assertEquals(this.llamadasBatchTest.get(4).getEmpleadoAtendio(), "Supervisor 1");
		Assert.assertTrue(this.llamadasBatchTest.get(4).getFailedAttemps() == 0);
		Assert.assertEquals(this.llamadasBatchTest.get(5).getEmpleadoAtendio(), "Director 0");
		Assert.assertTrue(this.llamadasBatchTest.get(6).getFailedAttemps() > 0);
		Assert.assertTrue(this.llamadasBatchTest.get(6).getEmpleadoAtendio() != null);
		Assert.assertTrue(this.llamadasBatchTest.get(7).getFailedAttemps() > 0);
		Assert.assertTrue(this.llamadasBatchTest.get(7).getEmpleadoAtendio() != null);
		Assert.assertTrue(this.llamadasBatchTest.get(8).getFailedAttemps() > 0);
		Assert.assertTrue(this.llamadasBatchTest.get(8).getEmpleadoAtendio() != null);
		Assert.assertTrue(this.llamadasBatchTest.get(9).getFailedAttemps() > 0);
		Assert.assertTrue(this.llamadasBatchTest.get(9).getEmpleadoAtendio() != null);
	}

	@Test
	public void testPendingCalls2() throws Exception {
		int concurrentCalls = 6;
		int threads = 3;
		int op = 1;
		int sup = 1;
		int dir = 1;
		CallCenter callCenter = new CallCenter(threads, op, sup, dir);
		IntStream.range(0, concurrentCalls)
						.forEach(i -> this.llamadasBatchTest.add(new Llamada("llamada pendingCalls " + String.valueOf(i))));
		callCenter.handleCalls(this.llamadasBatchTest);
		sleep(4000);
		Assert.assertEquals(this.llamadasBatchTest.get(0).getEmpleadoAtendio(), "Operador 0");
		Assert.assertTrue(this.llamadasBatchTest.get(0).getFailedAttemps() == 0);
		Assert.assertEquals(this.llamadasBatchTest.get(1).getEmpleadoAtendio(), "Supervisor 0");
		Assert.assertTrue(this.llamadasBatchTest.get(1).getFailedAttemps() == 0);
		Assert.assertEquals(this.llamadasBatchTest.get(2).getEmpleadoAtendio(), "Director 0");
		Assert.assertTrue(this.llamadasBatchTest.get(2).getFailedAttemps() == 0);
		Assert.assertTrue(this.llamadasBatchTest.get(3).getFailedAttemps() > 0);
		Assert.assertTrue(this.llamadasBatchTest.get(4).getFailedAttemps() > 0);
		Assert.assertTrue(this.llamadasBatchTest.get(5).getFailedAttemps() > 0);
		Assert.assertTrue(this.llamadasBatchTest.get(3).getEmpleadoAtendio() != null);
		Assert.assertTrue(this.llamadasBatchTest.get(4).getEmpleadoAtendio() != null);
		Assert.assertTrue(this.llamadasBatchTest.get(5).getEmpleadoAtendio() != null);
	}
}