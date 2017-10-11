package nl.biopet.tools.squishbed

import java.io.File

import nl.biopet.utils.tool.AbstractOptParser

class ArgsParser(cmdName: String) extends AbstractOptParser[Args](cmdName) {
  opt[File]('i', "input")
    .required()
    .valueName("<file>")
    .action((x, c) => c.copy(input = x))
  opt[File]('o', "output")
    .required()
    .unbounded()
    .valueName("<file>")
    .action((x, c) => c.copy(output = x))
  opt[Unit]('s', "strandSensitive")
    .unbounded()
    .action((_, c) => c.copy(strandSensitive = true))
  opt[Unit]('n', "nameSensitive")
    .unbounded()
    .action((_, c) => c.copy(nameSensitive = true))
}
