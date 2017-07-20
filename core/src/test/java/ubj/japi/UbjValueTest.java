package ubj.japi;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UbjValueTest {

  @Test
  public void testNoop() throws Exception {
    assertThat(
        new UbjNoop().bytes(),
        equalTo(load("noop")));
  }

  @Test
  public void testNull() throws Exception {
    assertThat(
        new UbjNull().bytes(),
        equalTo(load("null")));
  }

  @Test
  public void testTrue_Bool() throws Exception {
    assertThat(
        new UbjBoolean(true).bytes(),
        equalTo(load("true")));
  }

  @Test
  public void testFalse_Bool() throws Exception {
    assertThat(
        new UbjBoolean(false).bytes(),
        equalTo(load("false")));
  }

  @Test
  public void testChar_a() throws Exception {
    assertThat(
        new UbjChar('a').bytes(),
        equalTo(load("a")));
  }

  @Test
  public void testUint8_163() throws Exception {
    assertThat(
        new UbjUint8(163).bytes(),
        equalTo(load("163")));
  }

  private byte[] load(String filename) throws Exception {
    byte[] bytes = Files.readAllBytes(Paths.get("core/src/test/resources", filename + ".ubj"));
    return bytes;
  }
}
