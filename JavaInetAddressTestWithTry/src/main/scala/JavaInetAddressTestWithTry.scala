/**
  * @author sandip@plumewifi.com
  */
package myJavaInetAddressTest


object JavaInetAddressTestWithTry {
  def main(args: Array[String]): Unit = {
    val ipv4 = "12.12.12.12"
    println("converting " + ConvertToOvsdbStr.convert(Some(ipv4)))
    val url = "googleservices.com"
    println("converting " + ConvertToOvsdbStr.convert(Some(url)))
    val ipv6Addr1 = "2001:0db8:85a3:0000:0000:8a2e:0370:7334"
    println("converting " + ConvertToOvsdbStr.convert(Some(ipv6Addr1)))
    val ipv6Addr2 = "ff06::c3"
    println("converting " + ConvertToOvsdbStr.convert(Some(ipv6Addr2)))
    val ipv6Addr3 = "0:0:0:0:0:ffff:192.1.56.10" // this is failing, getting identified as ipv4
    println("converting " + ConvertToOvsdbStr.convert(Some(ipv6Addr3)))
    val ipv6Addr4 = "::ffff:192.1.56.10" // this is failing, getting identified as ipv4
    println("converting " + ConvertToOvsdbStr.convert(Some(ipv6Addr4)))
  }
}
