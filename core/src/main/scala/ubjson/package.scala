
package object ubjson {

  def encode[A](x: A)(implicit ubjson: Ubjson[A]): Array[Byte] = ubjson.pack(x)

  trait Ubjson[A] {
    def pack(x: A): Array[Byte]
  }

  class Noop private()
  object Noop { def apply() = new Noop()}

  implicit def ubjsonNoop = new Ubjson[Noop] {
    override def pack(x: Noop): Array[Byte] =  utf8('N')
  }

  implicit def ubjsonNull = new Ubjson[Null] {
    override def pack(x: Null): Array[Byte] =  utf8('Z')
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
      val bytes = utf8(x)
      utf8('S') ++ encode(bytes.length) ++ bytes
    }
  }


  private def utf8(x: Char): Array[Byte] = utf8(new String(Array(x)))

  private def utf8(x: String): Array[Byte] = x.getBytes("UTF-8")

  private def uint8(x: Int): Array[Byte] = Array(x.toByte)

  private def int8(x: Int): Array[Byte] = uint8(x + 256)
}
