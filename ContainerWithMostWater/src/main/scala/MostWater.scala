/**
  * @author sandip@plumewifi.com
  */
object MostWater {
    case class AreaMark(leftX: Int, leftY: Int, rightX: Int, rightY: Int, area: Int)

    def maxArea(height: Array[Int]): Int = {
      val len = height.length
      var maxArea = AreaMark(leftX = 0, leftY = 0, rightX = 0, rightY = 0, area = 0)
      for (point1 <- (0 until len)) {
        for (point2 <- (point1 + 1 until len)) {
          val tankHeight = if (height(point1) > height(point2)) height(point2) else height(point1)
          val area = (point2 - point1) * tankHeight
          if (area > maxArea.area) {
            maxArea = AreaMark(leftX = point1, leftY = height(point1), rightX = point2, rightY = height(point2), area = area)
          }
        }
      }
      maxArea.area
    }
}
