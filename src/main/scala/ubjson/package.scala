
package object ubjson {

  private def utf8(x: Char): Array[Byte] = {
    utf8(new String(Array(x)))
  }

  private def utf8(x: String): Array[Byte] = {
    x.getBytes("UTF-8")
  }

  private def uint8(x: Int): Array[Byte] = {
    // (00000000 00000000 00000000 10100011).toByte => 10100011
    Array(x.toByte)
  }

  private def int8(x: Int): Array[Byte] = {
    uint8(x + 256)
    // -93 + 256 = 163
    // (00000000 00000000 00000000 10100011).toByte => 10100011
    // (11111111 11111111 11111111 01011100)
    // (00000000 00000000 00000000 00000001)
    // (11111111 11111111 11111111 01011101)
  }

  trait Ubjson[A] {
    def pack(x: A): Array[Byte]
  }

  implicit def ubjsonBoolean = new Ubjson[Boolean] {
    override def pack(x: Boolean): Array[Byte] =  if (x) utf8('T') else utf8('F')
  }

  implicit def ubjsonChar = new Ubjson[Char] {
    override def pack(x: Char): Array[Byte] =  {
      if (x >= 0 && x <= 255) utf8('C') ++ uint8(x)
      else throw new IllegalArgumentException
    }
  }

  implicit def ubjsonInt = new Ubjson[Int] {
    import scala.math.{pow => ^}
    override def pack(x: Int): Array[Byte] =
      if      (x >=   0       && x <= (^(2, 8) - 1)) utf8('U') ++ uint8(x)
      else if (x >= -(^(2,7)) && x <= (^(2, 7) - 1)) utf8('i') ++ int8(x)
      else ???
  }

  implicit def ubjsonString = new Ubjson[String] {
    override def pack(x: String): Array[Byte] = {
      val string = utf8(x)
      utf8('S') ++ encode(string.length) ++ string
    }
  }


  def encode[A](x: A)(implicit ubjson: Ubjson[A]): Array[Byte] = ubjson.pack(x)


}
