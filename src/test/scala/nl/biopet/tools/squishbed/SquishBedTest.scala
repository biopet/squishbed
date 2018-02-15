/*
 * Copyright (c) 2014 Biopet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.biopet.tools.squishbed

import java.io.File

import nl.biopet.utils.test.tools.ToolTest
import nl.biopet.utils.ngs.intervals.{BedRecord, BedRecordList}
import org.testng.annotations.Test

class SquishBedTest extends ToolTest[Args] {
  def toolCommand: SquishBed.type = SquishBed
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

    SquishBed.main(
      Array("-i", intputFile.getAbsolutePath, "-o", outputFile.getAbsolutePath))

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
      SquishBed.main(
        Array("-i",
              intputFile.getAbsolutePath,
              "-o",
              outputFile.getAbsolutePath))
    }.getMessage shouldBe s"Input file not found, file: ${intputFile.getAbsolutePath}"
  }

  @Test
  def noArgs(): Unit = {
    intercept[IllegalArgumentException] {
      SquishBed.main(Array())
    }
  }
}
