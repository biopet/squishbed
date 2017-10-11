package nl.biopet.tools.squishbed

import java.io.File

import nl.biopet.test.BiopetTest
import nl.biopet.utils.ngs.intervals.{BedRecord, BedRecordList}
import org.testng.annotations.Test

class SquishBedTest extends BiopetTest {
  @Test
  def testMain(): Unit = {
    val intputFile = File.createTempFile("regions.", ".bed")
    val outputFile = File.createTempFile("regions.", ".bed")
    intputFile.deleteOnExit()
    outputFile.deleteOnExit()

    val overlapList = BedRecordList.fromList(
      List(
        BedRecord("chrQ", 0, 10),
        BedRecord("chrQ", 5, 15),
        BedRecord("chrQ", 10, 20),
        BedRecord("chrQ", 25, 35),
        BedRecord("chrQ", 50, 80),
        BedRecord("chrQ", 60, 70)
      ))
    overlapList.writeToFile(intputFile)

    SquishBed.main(Array("-i", intputFile.getAbsolutePath, "-o", outputFile.getAbsolutePath))

    val output = BedRecordList.fromFile(outputFile)

    output shouldBe BedRecordList.fromList(
      List(
        BedRecord("chrQ", 0, 5),
        BedRecord("chrQ", 15, 20),
        BedRecord("chrQ", 25, 35),
        BedRecord("chrQ", 50, 60),
        BedRecord("chrQ", 70, 80)
      ))
  }

  @Test
  def testInputFileNotExist(): Unit = {
    val intputFile = File.createTempFile("regions.", ".bed")
    val outputFile = File.createTempFile("regions.", ".bed")
    intputFile.delete()
    outputFile.delete()
    intercept[IllegalStateException] {
      SquishBed.main(Array("-i", intputFile.getAbsolutePath, "-o", outputFile.getAbsolutePath))
    }.getMessage shouldBe s"Input file not found, file: ${intputFile.getAbsolutePath}"
  }

  @Test
  def noArgs(): Unit = {
    intercept[IllegalArgumentException] {
      SquishBed.main(Array())
    }
  }
}
