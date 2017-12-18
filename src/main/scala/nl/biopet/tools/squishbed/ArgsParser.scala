package nl.biopet.tools.squishbed

import java.io.File

import nl.biopet.utils.tool.{AbstractOptParser, ToolCommand}

class ArgsParser(toolCommand: ToolCommand[Args])
    extends AbstractOptParser[Args](toolCommand) {
  opt[File]('i', "input") required () valueName ("<file>") action { ((x, c) => c.copy(input = x))
  } text "Input BED file"
  opt[File]('o', "output")
    .required()
    .unbounded()
    .valueName("<file>")
    .action((x, c) => c.copy(output = x))
    .text("Output BED file")
  opt[Unit]('s', "strandSensitive")
    .unbounded()
    .action((_, c) => c.copy(strandSensitive = true))
    .text("Take strand column into account")
  opt[Unit]('n', "nameSensitive")
    .unbounded()
    .action((_, c) => c.copy(nameSensitive = true))
    .text("Take name column into account")
}
