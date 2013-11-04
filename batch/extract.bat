REM This is a batch file to extract the war.
REM The directory you are in will be the root directory of the app.
REM
REM   cd\
REM   c:
REM   mkdir [directoryWhereWarShouldBeExtractedAndRun]
REM   cd [directoryWhereWarShouldBeExtractedAndRun]
REM   jar -xvf [yourWARfilePath]
REM
cd\
c:
mkdir runnable-wars
cd runnable-wars
jar -xvf %USERPROFILE%/workspace/CatalystDrive/target/CatalystDrive.war