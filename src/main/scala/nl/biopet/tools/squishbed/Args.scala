package nl.biopet.tools.squishbed

import java.io.File

case class Args(input: File = null,
                output: File = null,
                strandSensitive: Boolean = false,
                nameSensitive: Boolean = false)
