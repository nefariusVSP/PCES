name := "PCES" //a program for creating expert systems

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq (
  //"org.apache.spark" %% "spark-core" % "1.2.0" ,
  "com.novocode" % "junit-interface" % "0.8",
  "net.databinder" %%"unfiltered-filter" % "0.8.4",
  "net.databinder" %% "unfiltered-jetty" % "0.8.4"

)
