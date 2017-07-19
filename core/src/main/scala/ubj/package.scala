
package object ubj {

  def encode[A](x: A)(implicit u: Ubj[A]): Array[Byte] = u.encode(x)


  trait Ubj[A] {
    def encode(x: A): Array[Byte]
  }


  implicit val UbjNoop = new Ubj[Noop] {
   def encode(x: Noop): Array[Byte] =  utf8('N')
  }
  class Noop private() {
    def toUbj = encode(this)
  }
  object Noop { def apply() = new Noop()}


  implicit def UbjNull = new Ubj[Null] {
   def encode(z: Null): Array[Byte] =  utf8('Z')
  }


  implicit val UbjBoolean = new Ubj[Boolean] {
   def encode(bool: Boolean): Array[Byte] =  if (bool) utf8('T') else utf8('F')
  }
  implicit class UbjBoolean(bool: Boolean) {
    def toUbj = encode(bool)
  }

  implicit def UbjChar = new Ubj[Char] {
   def encode(ch: Char): Array[Byte] =  {
      if (ch >= 0 && ch <= 255) utf8('C') ++ uint8(ch)
      else throw new IllegalArgumentException
    }
  }
  implicit class UbjChar(ch: Char) {
    def toUbj = encode(ch)
  }

  implicit def UbjInt = new Ubj[Int] {
    import scala.math.{pow => ^}
   def encode(num: Int): Array[Byte] =
      if      (num >=   0       && num <= (^(2, 8) - 1)) utf8('U') ++ uint8(num)
      else if (num >= -(^(2,7)) && num <= (^(2, 7) - 1)) utf8('i') ++ int8(num)
      // TODO int16, int32, int64
      else ???
  }
  implicit class UbjInt(x: Int) {
    def toUbj = encode(x)
  }

  implicit def UbjString = new Ubj[String] {
   def encode(str: String): Array[Byte] = {
      val bytes = utf8(str)
      utf8('S') ++ UbjInt.encode(bytes.length) ++ bytes
    }
  }
  implicit class UbjString(str: String) {
    def toUbj = encode(str)
  }


  private def utf8(x: Char): Array[Byte] = utf8(new String(Array(x)))

  private def utf8(x: String): Array[Byte] = x.getBytes("UTF-8")

  private def uint8(x: Int): Array[Byte] = Array(x.toByte)

  private def int8(x: Int): Array[Byte] = uint8(x + 256)
}
