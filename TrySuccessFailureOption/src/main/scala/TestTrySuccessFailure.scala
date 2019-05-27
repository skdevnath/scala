import com.sandip.common._

object TestTrySuccessFailure {
  def main(args: Array[String]): Unit = {
    println("In main")
    val user = User(id = 2, firstName = "Johanna", lastName = "Doe", age = 30, gender = None)
    val genderStr = user.gender match {
      case Some(gender) => gender
      case None => "not specified"
    }
    println("Gender: " + genderStr)

    // Test Subnet
    val myIp = "192.168.22.0"
    val myMask = "255.255.255.112"
    println(f"${myIp}/${myMask}")

    val ip = InetAddress(myIp)
    println(f"My ip: ${ip}")
    val mask = InetAddress(myMask)
    println(f"My mask: ${mask}")
    val mySubnet = Subnet(ip.get, mask.get)
    println(f"My Subnet: ${mySubnet}")

    for {
      ip <- InetAddress.parse(myIp)
      mask <- InetAddress(myMask)
      mySubnet = Subnet(ip, mask)
    } {
      println(f"${mySubnet}")
    }

    val subnet = for {
      ip <- InetAddress.parse(myIp).toOption // Try
      mask <- InetAddress(myMask)
      trySubnet = Subnet(ip, mask)  // Try
      mySubnet <- trySubnet.toOption
    } yield (mySubnet)
    println(f"With Try:${subnet}")
  }
}
