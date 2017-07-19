package ubj

import org.scalatest.{FlatSpec, MustMatchers}

// TODO http://dmitry-ra.github.io/ubjson-test-suite/blocks-parser.html
// TODO https://groups.google.com/forum/?fromgroups#!topic/universal-binary-json/gLYhujRcML8

class UbjsonSpec extends FlatSpec with MustMatchers {

  "The ubjson.encode method" should "encode jvm Noop to ubjson noop" in {
    encode(Noop()) mustBe expected("noop") // [0x4E]
  }


  it should "encode jvm null to ubjson null" in {
    encode(null) mustBe expected("null") // [0x5E]
  }


  it should "encode jvm(true: Boolean) to ubjson(true: boolean)" in {
    encode(true) mustBe expected("true") // [0x54]
  }


  it should "encode jvm(false: Boolean) to ubjson(false: boolean)" in {
    encode(false) mustBe expected("false") // [0x46]
  }


  it should "encode jvm('a': Char) to ubjson('a': char)" in {
    encode('a') mustBe expected("a") // [0x43, 0x61]
  }


  it should "encode jvm(163: Int) to ubjson(163: uint8) " in {
    // UNSIGNED: the MSB counts as 128
    //
    // hex     :                A                 3
    // bits    :    1   0   1   0     0   0   1   1
    // weights : -128  64  32  16     8   4   2   1    =>   163

    encode(163) mustBe expected("163") // [0x55, 0xA3]
  }


  it should "encode jvm(-93: Int) to ubjson(-93: int8)" in {
    // TWO's COMPLEMENT: the MSB counts as -128
    //
    // hex     :                A                 3
    // bits    :    1   0   1   0     0   0   1   1
    // weights : -128  64  32  16     8   4   2   1    =>   -93

    encode(-93) mustBe expected("-93") // [0x69, 0xA3]
  }


  it should """encode jvm("hello": String) to ubjson("hello": string)""" in {
    //     S     U     5     h     e     l     l     0
    // [0x53, 0x55, 0x05, 0x68, 0x65, 0x6C, 0x6C, 0x6F]
    encode("hello") mustBe expected("hello")
  }


  it should """encode jvm("привет": String) to ubjson("привет": string)""" in {
    //     S     U    12           п           р           и           в           е           т
    // [0x53, 0x55, 0x0C, 0xD0, 0xBF, 0xD1, 0x80, 0xD0, 0xB8, 0xD0, 0xB2, 0xD0, 0xB5, 0xD1, 0x82]
    encode("привет") mustBe expected("russian")
  }


  it should """encode jvm("مرحبا": String) to ubjson("مرحبا": string)""" in {
    //     S     U    10           ا            ب           ح           ر           م
    // [0x53, 0x55, 0x0A, 0xD9, 0x85, 0xD8, 0xB1, 0xD8, 0xAD, 0xD8, 0xA8, 0xD8, 0xA7]
    encode("مرحبا") mustBe expected("arabic")
  }



  private def expected(filename: String): Array[Byte] = {
    import java.nio.file.{Files, Paths}
    val expected = Files.readAllBytes(Paths.get(s"core/src/test/resources/$filename.ubj"))
    expected
  }
}
