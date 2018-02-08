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

import nl.biopet.utils.ngs.intervals.BedRecordList
import nl.biopet.utils.tool.ToolCommand
import nl.biopet.utils.Documentation.htmlTable

object SquishBed extends ToolCommand[Args] {
  def emptyArgs: Args = Args()
  def argsParser = new ArgsParser(this)
  def main(args: Array[String]): Unit = {
    val cmdArgs = cmdArrayToArgs(args)

    if (!cmdArgs.input.exists)
      throw new IllegalStateException(
        "Input file not found, file: " + cmdArgs.input)

    logger.info("Start")

    val records = BedRecordList.fromFile(cmdArgs.input)
    val length = records.length
    val refLength = records.combineOverlap.length
    logger.info(s"Total bases: $length")
    logger.info(s"Total bases on reference: $refLength")
    logger.info("Start squishing")
    val squishBed =
      records.squishBed(cmdArgs.strandSensitive, cmdArgs.nameSensitive).sorted
    logger.info("Done squishing")
    val squishLength = squishBed.length
    val squishRefLength = squishBed.combineOverlap.length
    logger.info(s"Total bases left: $squishLength")
    logger.info(s"Total bases left on reference: $squishRefLength")
    logger.info(
      s"Total bases removed from ref: ${refLength - squishRefLength}")
    squishBed.writeToFile(cmdArgs.output)

    logger.info("Done")
  }

  def descriptionText: String =
    """
      |
      |Squishbed makes a BED file smaller by removing bases that are in overlapping records.
      |Squishbed will report the number of bases before and after squishing.
    """.stripMargin

  def manualText: String =
    """
      |Squishbed requires a BED file as input. When squishing it can take
      |the strand column into account with `--strandSensitive` and the name column with
      |`--nameSensitive`.
    """.stripMargin

  def exampleText: String = {
    val exampleTableHeaders = List("chrom", "chromstart", "chromend")
    val exampleTableBefore = htmlTable(
      exampleTableHeaders,
      List(
        List("ChrQ", "0", "10"),
        List("ChrQ", "5", "15"),
        List("ChrQ", "10", "20"),
        List("ChrQ", "25", "35"),
        List("ChrQ", "50", "80"),
        List("ChrQ", "60", "70")
      )
    )
    val exampleTableAfter = htmlTable(exampleTableHeaders,
                                      List(
                                        List("ChrQ", "0", "5"),
                                        List("ChrQ", "15", "20"),
                                        List("ChrQ", "25", "35"),
                                        List("ChrQ", "50", "60"),
                                        List("ChrQ", "70", "80")
                                      ))
    s"""
       |The following example will create a squished BED file where the
       |strand and name column have been taken into account:
       |${example("-i",
                  "myBED.bed",
                  "-o",
                  "mySquishedBED.bed",
                  "--strandSensitive",
                  "--nameSensitive")}
       |
       |An example when running SquishBed
       |
       |Before:
       |
       |$exampleTableBefore
       |
       |After:
       |
       |$exampleTableAfter
     """.stripMargin
  }
}
