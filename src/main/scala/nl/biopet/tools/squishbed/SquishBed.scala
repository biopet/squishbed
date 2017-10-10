package nl.biopet.tools.squishbed

import nl.biopet.utils.ngs.intervals.BedRecordList
import nl.biopet.utils.tool.ToolCommand

object SquishBed extends ToolCommand {
  def main(args: Array[String]): Unit = {
    val parser = new ArgsParser(toolName)
    val cmdArgs =
      parser.parse(args, Args()).getOrElse(throw new IllegalArgumentException)

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
}
