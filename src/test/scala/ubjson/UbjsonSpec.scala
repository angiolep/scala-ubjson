package ubjson

import org.scalatest.{FlatSpec, MustMatchers}

class UbjsonSpec extends FlatSpec with MustMatchers {

  "The ubjson.encode method" should "encode Scala (true: Boolean) to Ubjson (true: boolean)" in {
    encode(true) mustBe ubj("true") // [0x54]
  }


  it should "encode Scala (false: Boolean) to Ubjson (false: boolean)" in {
    encode(false) mustBe ubj("false") // [0x46]
  }


  it should "encode Scala ('Z': Char) to Ubjson ('Z': char)" in {
    encode('Z') mustBe ubj("Z") // [0x43, 0x5A]
  }


  it should "encode Scala (163: Int) to Ubjson (163: uint8) " in {
    // UNSIGNED: the MSB counts as 128
    //
    // hex     :                A                 3
    // bits    :    1   0   1   0     0   0   1   1
    // weights : -128  64  32  16     8   4   2   1    =>   163

    encode(163) mustBe ubj("163") // [0x55, 0xA3]
  }


  it should "encode Scala (-93: Int) to Ubjson (-93: int8)" in {
    // TWO's COMPLEMENT: the MSB counts as -128
    //
    // hex     :                A                 3
    // bits    :    1   0   1   0     0   0   1   1
    // weights : -128  64  32  16     8   4   2   1    =>   -93

    encode(-93) mustBe ubj("-93") // [0x69, 0xA3]
  }


  it should """encode Scala ("hello": String) to Ubjson ("hello": string)""" in {
    //     S     U     5     h     e     l     l     0
    // [0x53, 0x55, 0x05, 0x68, 0x65, 0x6C, 0x6C, 0x6F]
    encode("hello") mustBe ubj("hello")
  }


  private def ubj(filename: String): Array[Byte] = {
    import java.nio.file.{Files, Paths}
    val expected = Files.readAllBytes(Paths.get(s"src/test/resources/$filename.ubj"))
    expected
  }
}
