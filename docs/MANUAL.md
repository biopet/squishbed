# Manual

The following example will create a squished BED file where 
the strand and name column have been taken into account:

```bash
java --jar squishbed-version.jar \
-i myBED.bed \
-o mySquishedBED.bed \
--strandSensitive \
--nameSensitive
```

An example when running SquishBed

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

