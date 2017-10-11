#  BIOPET tool suite
This tool is part of BIOPET tool suite that is developed at LUMC by [the SASC team](http://sasc.lumc.nl/). 
Each tool in the BIOPET tool suite is meant to offer a standalone function that can be used to perform a
dedicate data analysis task or added as part of [BIOPET pipelines](http://biopet-docs.readthedocs.io/en/latest/).

#  About this tool
Squishbed makes a BED file smaller by removing bases that are in overlapping records.

Before:

chrom | chromstart | chromend
---|---|---
chrQ | 0 | 10
chrQ | 5 | 15
chrQ | 10 | 20
chrQ | 25 | 35
chrQ | 50 | 80
chrQ | 60 | 70

After:

chrom | chromstart | chromend
---|---|---
chrQ | 0 | 5
chrQ | 15 | 20
chrQ | 25 | 35
chrQ | 50 | 60
chrQ | 70 | 80

Squishbed will report the number of bases before and after squishing.

#  Installation
This tool requires Java 8 to be installed on your device. Download Java 8 
[here](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) 
or install via your distribution's package manager.

Download the latest version of squishbed [here](https://github.com/biopet/squishbed/releases). 
To generate the usage run:
```bash
java -jar squishbed-version.jar --help
```

#  Manual
The following example will create a squished BED file where 
the strand and name column have been taken into account:

```bash
java --jar squishbed-version.jar \
-i myBED.bed \
-o mySquishedBED.bed \
--strandSensitive \
--nameSensitive
```


#  Contact


<p>
  <!-- Obscure e-mail address for spammers -->
For any question related to this tool, please use the github issue tracker or contact 
  <a href='http://sasc.lumc.nl/'>the SASC team</a> directly at: <a href='&#109;&#97;&#105;&#108;&#116;&#111;&#58;
 &#115;&#97;&#115;&#99;&#64;&#108;&#117;&#109;&#99;&#46;&#110;&#108;'>
  &#115;&#97;&#115;&#99;&#64;&#108;&#117;&#109;&#99;&#46;&#110;&#108;</a>.
</p>
