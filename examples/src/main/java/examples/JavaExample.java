package examples;

import ubj.japi.*;

public class JavaExample {
  public static void main(String[] args) {

    // Value Types
    // -------------------------
    new UbjNoop().bytes();
    new UbjNull().bytes();
    new UbjBoolean(true).bytes();
    new UbjUint8(163).bytes();
    new UbjUint8(-93).bytes();
    new UbjString('c').bytes();
    new UbjString("hello").bytes();

    // Container Types
    // -------------------------
    // TODO new UbjArray(asList(null, 1.2, "paolo")).encode();
  }
}
