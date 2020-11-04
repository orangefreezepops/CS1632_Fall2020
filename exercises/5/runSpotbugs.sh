javac -d bin -cp src:jpf-core/build/jpf-annotations.jar:jpf-core/build/jpf.jar src/DrunkCarnivalShooter.java
javac -d bin -cp src:jpf-core/build/jpf-annotations.jar:jpf-core/build/jpf.jar src/DrunkCarnivalShooterImpl.java

java -jar spotbugs-4.0.0-beta4/lib/spotbugs.jar -textui -low -effort:max -longBugCodes -exclude spotbugs-4.0.0-beta4/my_exclude_filter.xml bin/*.class
