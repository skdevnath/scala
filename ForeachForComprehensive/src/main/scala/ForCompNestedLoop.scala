case class Vap(val vapType: String, val radio: String)

class ForCompNestedLoop {
  val vapTypes = List("ssid_5.0", "ssid_2.4")
  val radios = List("2.4","5.0")
  def getVap(vapType: String, radio: String) : Vap = {
    return Vap(vapType, radio)
  }
  def isVapConfigSupported(vap: Vap) : Boolean = {
    /* lets only allow config on 2.4 radio */
    if (vap.radio == "2.4") true else false
  }

  def testNestedForCompLoop : Unit = {
    val vaps = for {
      vapType <- vapTypes
      radio <- radios
      vap = getVap(vapType, radio)
      if isVapConfigSupported(vap)
    } yield (vapType, radio, vap)
  }

}



/*
  override def vapEnabledRadios(nodeId: String, vapType: VapType): Set[Radio] = {
    (for {
      nodeConfig <- topology.nodeConfigs.get(nodeId).iterator
      wifis <- nodeConfig.wifiConfig.iterator
      wifi <- wifis
      if wifi.vaps forall (_.contains(vapType))
    } yield wifi.freqBand.toRadio).toSet
  }


  one more e.g
      def homeBridgeSubnet: Option[Subnet] = {
      iterator find (_.isHomeBridge) flatMap (_.subnetOption)

      collectFirst  {
        case entry if entry.isHomeBridge => entry.subnetOpti
      }
      val allSubnets = for {
        entry <- iterator if (entry.isHomeBridge)
      } yield (entry.subnetOption)
      allSubnets.next()
    }
 */
