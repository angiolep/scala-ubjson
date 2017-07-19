package example


object ScalaExample extends App {
  import ubj._

  // Value Types
  // -------------------------

  // In pure functional-programming style
  // with constrained polymorphism "à la Haskell"

  encode(Noop())      // [0x4E]
  encode(null)        // [0x5A]
  encode(true)        // [0x54]
  encode(false)       // [0x46]
  encode('a')         // [0x43, 0x61]
  encode(163)         // [0x55, 0xA3]
  encode(-93)         // [0x69, 0xA3]
  encode("hello")     // [0x53, 0x55, 0x05, 0x68, 0x65, 0x6C, 0x6C, 0x6F]


  // Or, in pure object-oriented style

  Noop().toUbj
  true.toUbj
  false.toUbj
  'a'.toUbj
  163.toUbj
  (-93).toUbj
  "hello".toUbj


  // Container Types
  // -------------------------
  // TODO encode(List(null, "paolo"))
}
