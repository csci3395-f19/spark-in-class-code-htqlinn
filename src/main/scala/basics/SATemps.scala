package basics

case class TempRow(day:Int, doy:Int, month:Int, year:Int, precip:Double, tave:Double, tmax:Double, tmin:Double)

object SATemps {
    
  def parseLine(line:String): TempRow = {
    val p = line.split(",")
    TempRow(p(0).toInt, p(1).toInt, p(2).toInt, p(4).toInt, p(5).toDouble, p(6).toDouble, p(7).toDouble, p(8).toDouble)
	}

	def main(args:Array[String]):Unit = { 
    val source = scala.io.Source.fromFile("/users/mlewis/CSCI3395-F19/InClassBD/data/SanAntonioTemps.csv")
    val lines = source.getLines()
    val data = lines.drop(2).map(parseLine).toArray
    data.take(5).foreach(println)

    val hotDay1 = data.maxBy(_.tmax)
    val hotDay2 = data.reduce((d1,d2) => if(d1.tmax > d1.tmax) d1 else d2)

    println("The hottest day is: " + hotDay1)
    println("The hottest day is: " + hotDay2)

    val maxPrecip = data.maxBy(_.precip)
    println("The day with most precipitation is: " + maxPrecip)

    val precipFraction = (data.filter(_.precip > 1).length / data.length) * 100
    println("The fraction of days with over 1 inch of precipitation is: " + precipFraction)
  
    val rainyDays = data.filter(_.precip >= 1.0)
    val rainyTemp = rainyDays.foldLeft(0.0)(_+_.tmax) / rainyDays.length

    //alternatively
    val (rainySum, rainyCount) = data.foldLeft((0.0, 0)){case ((sum, cnt), day) =>
      if(day.precip >= 1)(sum + day.precip, cnt + 1)
    }
    println(rainySum / rainyCount)

    val months = data.groupBy(_.month)
    val avgHigh = months.mapValues((rows) => rows.map(_.tmax).sum /rows.length)

    val 
  
  }
}