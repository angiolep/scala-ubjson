package ubj.japi

import ubj._

trait UbjValue {
  def bytes: Array[Byte]
}

class UbjNoop() extends UbjValue {
  def bytes: Array[Byte] = encode(Noop())
}

class UbjNull() extends UbjValue {
  def bytes: Array[Byte] = encode(null)
}

class UbjBoolean(bool: Boolean) extends UbjValue {
  def bytes: Array[Byte] = encode(bool)
}

class UbjChar(ch: Char) extends UbjValue {
  def bytes: Array[Byte] = encode(ch)
}

class UbjString(str: String) extends UbjValue {
  def this(ch: Char) = this(new String(Array(ch)))
  def bytes: Array[Byte] = encode(str)
}

class UbjUint8(num: Int) extends UbjValue {
  def bytes: Array[Byte] = encode(num)
}
