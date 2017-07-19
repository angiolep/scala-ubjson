package example


object ScalaExample extends App {
  import ubjson._

  // Value Types
  // -------------------------
  encode(Noop())      // [4E]
  encode(null)        // [5A]
  encode(true)        // [54]
  encode(false)       // [46]
  encode('a')         // [4361]
  encode(163)         // [55A3]
  encode(-93)         // [69A3]
  encode("hello")     // [53550568 656C6C6F]


  // Container Types
  // -------------------------
  // TODO encode(List(null, "paolo"))
}
